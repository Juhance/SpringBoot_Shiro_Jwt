package com.cqust.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.cqust.Vo.ResponseVo;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
     * ��¼ʧ��ʱ����ʾ��Ϣ��request���ڵ�Key
     */
    public static final String TOKEN_LOGIN_EXCEPTION_MESSAGE_KEY = "TokenLoginExceptionMessage";

    /**
     * ��׽Shiro����Ȩ�쳣
     */
    @ExceptionHandler(AuthorizationException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo handleAuthz(AuthorizationException e, HttpServletRequest request) {
        String errMsg = (String) request.getAttribute(TOKEN_LOGIN_EXCEPTION_MESSAGE_KEY);
        if (StringUtils.isEmpty(errMsg)) {
            errMsg = e.getMessage();
        }
        return ResponseVo.error(HttpStatus.FORBIDDEN.value(), errMsg);
    }

    /**
     * ��׽����Shiro�������쳣
     */
    @ExceptionHandler(ShiroException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseVo handleShiro(ShiroException e, HttpServletRequest request) {
        String errMsg = (String) request.getAttribute(TOKEN_LOGIN_EXCEPTION_MESSAGE_KEY);
        if (StringUtils.isEmpty(errMsg)) {
            errMsg = e.getMessage();
        }
        return ResponseVo.error(HttpStatus.UNAUTHORIZED.value(), errMsg);
    }

    /**
     * ��׽ȫ���쳣
     */
    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVo handleException(Throwable ex) {
        ex.printStackTrace();
        return ResponseVo.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "�����쳣��");
    }
}
