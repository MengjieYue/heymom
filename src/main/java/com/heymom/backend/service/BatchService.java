package com.heymom.backend.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.controller.ErrorHandler;
import com.heymom.backend.dto.APIResult;
import com.heymom.backend.dto.BatchRequest;

@Service
public class BatchService implements ApplicationContextAware {
	class MethodInfo {
		List<String> args;
		Method method;

		MethodInfo(Method method, List<String> args) {
			this.method = method;
			this.args = args;
		}
	}

	class ResourceMethodMap {
		Map<String, MethodInfo> methodMap = new HashMap<String, MethodInfo>();
		Object resource;
	}

	private ApplicationContext applicationContext;

	private boolean initialized = false;

	private Logger logger = Logger.getLogger(ErrorHandler.class);

	private Map<String, ResourceMethodMap> resourceMaps = new HashMap<String, ResourceMethodMap>();

	@Transactional(readOnly = true)
	public APIResult<?> doBatch(List<BatchRequest> requests, String userToken) throws HeymomException {
		logger.debug("Received batch request: \n" + requests.toString());
		if (!initialized) {
			load();
		}
		List<APIResult<?>> resultList = new ArrayList<APIResult<?>>();
		for (BatchRequest request : requests) {
			try {
				APIResult<?> result = invoke(request, userToken);
				if (result.getReturnCode() != 0) {
					break;
				}
				resultList.add(result);
			} catch (HeymomException ex) {
				logger.error("Error occured in step #{" + request.getResource() + request.getMethod() + "}", ex);
				throw ex;
			} catch (Exception ex) {
				logger.error("Error occured in step #{" + request.getResource() + request.getMethod() + "}", ex);
				throw new HeymomException(999999);
			}
		}
		return new APIResult<Object>(resultList.toArray());
	}

	private List<String> getParamNames(CtClass cc, String methodName) throws NotFoundException {
		CtMethod cm = cc.getDeclaredMethod(methodName);
		CodeAttribute codeAttribute = cm.getMethodInfo().getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		if (attr == null) {
			return null;
		}
		List<String> paramNames = new ArrayList<String>();
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		for (int i = 0; i < cm.getParameterTypes().length; i++) {
			paramNames.add(attr.variableName(i + pos));
		}
		return paramNames;
	}

	private APIResult<?> invoke(BatchRequest request, String userToken) {
		ResourceMethodMap resourceMethod = resourceMaps.get(request.getResource());
		if (null == resourceMethod) {
			throw new HeymomException(900001);
		}
		MethodInfo methodInfo = resourceMethod.methodMap.get(request.getMethod());
		if (null == methodInfo) {
			throw new HeymomException(900002);
		}
		List<Object> args = new ArrayList<Object>();

		try {
			for (String argName : methodInfo.args) {
				Object arg;
				if (argName.equalsIgnoreCase("userToken")) {
					arg = userToken;
				} else {
					arg = request.getParams().get(argName);
				}
				args.add(arg);
			}
		} catch (Exception ex) {
			logger.error("Error occured in step #{" + request.getResource() + request.getMethod() + "}", ex);
			throw new HeymomException(999999);
		}
		Object ret = null;
		try {
			ret = methodInfo.method.invoke(resourceMethod.resource, args.toArray());
		} catch (Exception e) {
			logger.error("Error occured in step #{" + request.getResource() + request.getMethod() + "}", e);
			throw new HeymomException(999999);
		}

		return (APIResult<?>) ret;
	}

	private void load() {

		Map<String, Object> resources = applicationContext.getBeansOfType(null);

		for (String resourceName : resources.keySet()) {
			if (!resourceName.endsWith("APIController")) {
				continue;
			}
			Object resourceBean = resources.get(resourceName);
			ResourceMethodMap methodMap = new ResourceMethodMap();
			methodMap.resource = resourceBean;

			Class<?> realClass = AnnotationUtils.findAnnotationDeclaringClass(RequestMapping.class,
					resourceBean.getClass());
			ClassPool pool = ClassPool.getDefault();
			ClassClassPath classPath = new ClassClassPath(this.getClass());
			pool.insertClassPath(classPath);
			try {
				CtClass cc = pool.get(realClass.getName());
				for (Method method : realClass.getDeclaredMethods()) {
					String methodName = method.getName();
					methodMap.methodMap.put(methodName, new MethodInfo(method, getParamNames(cc, methodName)));
					logger.info("Resource loaded for Batch: {" + resourceName + "}.{" + methodName + "}");
				}

			} catch (NotFoundException e) {
				e.printStackTrace();
				throw new HeymomException(999999);
			}

			resourceMaps.put(resourceName, methodMap);
		}
		initialized = true;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

}
