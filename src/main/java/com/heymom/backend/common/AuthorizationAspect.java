package com.heymom.backend.common;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;

import com.heymom.backend.service.UserService;

@Aspect
public class AuthorizationAspect {
	@Autowired
	private UserService userService;

	@Before(value = "@annotation(com.heymom.backend.common.LoginRequired)")
	public void check(JoinPoint joinPoint) throws Exception {
		String userToken = getUserToken(joinPoint);
		userService.getCurrentUserByToken(userToken);
		String authority = getAuthority(joinPoint);
		userService.checkUserAuthority(authority);
	}

	private String getAuthority(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		return signature.getMethod().getAnnotation(LoginRequired.class)
				.authority();
	}

	private String getUserToken(JoinPoint joinPoint) throws Exception {
		Object[] parameterValues = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Annotation[][] annotations = joinPoint
				.getTarget()
				.getClass()
				.getMethod(signature.getMethod().getName(),
						signature.getMethod().getParameterTypes())
				.getParameterAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			for (Annotation parameterAnnotation : annotations[i]) {
				if (parameterAnnotation.annotationType().equals(
						RequestHeader.class)
						|| parameterAnnotation.annotationType().equals(
								CookieValue.class)) {
					if ("token".equals(parameterAnnotation.annotationType()
							.getMethod("value").invoke(parameterAnnotation)
							.toString())) {
						return (String) parameterValues[i];
					}
				}
			}
		}
		return null;
	}
}
