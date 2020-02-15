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
     * 登录失败时的提示消息在request域内的Key
     */
    public static final String TOKEN_LOGIN_EXCEPTION_MESSAGE_KEY = "TokenLoginExceptionMessage";

    /**
     * 捕捉Shiro的授权异常
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
     * 捕捉基于Shiro的所有异常
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
     * 捕捉全局异常
     */
    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseVo handleException(Throwable ex) {
        ex.printStackTrace();
        return ResponseVo.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "操作异常！");
    }
}
