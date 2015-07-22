package com.heymom.backend.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.dto.APIResult;

@ControllerAdvice
public class ErrorHandler {
    private Logger logger = Logger.getLogger(ErrorHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public APIResult<HeymomException> errorResponse(Exception exception, HttpServletRequest request, HttpServletResponse response) throws Exception{
        StringWriter stackTraceWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTraceWriter));
        String stackTrace = stackTraceWriter.toString();
        if (exception instanceof HeymomException) {
            HeymomException e = (HeymomException) exception;
            String accept = request.getHeader("Accept");
            String xRequestedWithStr = request.getHeader("X-Requested-With");
            if ((xRequestedWithStr != null && xRequestedWithStr.indexOf("XMLHttpRequest") > -1) || (accept!=null && accept.indexOf("application/json")>-1)) {
                return new APIResult<HeymomException>(e.getErrorCode(), stackTrace);
            }else{
                if (e.getErrorCode() == 100005 || e.getErrorCode() == 100006) {
                        response.sendRedirect("/backend/index/login");
                }else{
                    request.setAttribute("code", e.getErrorCode());
                    request.getRequestDispatcher("/backend/tips").forward(request, response);
                }
                return null;
            }
        } else {
            logger.error("Exception:", exception);
            return new APIResult<HeymomException>(HeymomException.UNKNOWN, stackTrace);
        }
    }
}