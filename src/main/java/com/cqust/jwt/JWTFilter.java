package com.cqust.jwt;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cqust.exception.GlobalExceptionHandler;

public class JWTFilter extends BasicHttpAuthenticationFilter {

    // ��¼��ʶ
    private static String LOGIN_SIGN = "Authorization";

    /**
     * ����û��Ƿ��¼
     * ���header�����Ƿ����Authorization�ֶμ���
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;

        String authorization1 = req.getHeader(LOGIN_SIGN);
        String authorization2 = (String) req.getParameter("token");
        return authorization1 != null || authorization2 != null;

    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader(LOGIN_SIGN);
        if(header == null) {
        	header = req.getParameter("token");
        }
        JWTToken token = new JWTToken(header);

        getSubject(request, response).login(token);
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                System.out.println("��¼Ȩ�޲��㣡");
                request.setAttribute(GlobalExceptionHandler.TOKEN_LOGIN_EXCEPTION_MESSAGE_KEY,"ƾ֤�����ˣ��ף�");
                //request.getRequestDispatcher("/user/toLogin");
                //return false;
            }
        }

        return true;
    }

    /**
     * �Կ����ṩ֧��
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // ����ʱ�����ȷ���һ��option�����������Ǹ�option����ֱ�ӷ�������״̬
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}