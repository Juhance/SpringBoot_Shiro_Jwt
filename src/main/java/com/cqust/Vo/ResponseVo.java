package com.cqust.Vo;

import java.io.Serializable;

import com.cqust.enitiy.User;

public class ResponseVo implements Serializable {

	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = -4771625271929822747L;
	//属性	
	private Integer statusCode;//状态码  200正常
	private String errorMessage;//错误提示
	private Object data;//返回页面的data数据
	private User user;//返回页面对象信息
	
	public ResponseVo(Integer code, String errMsg, Object data2, Object object) {
		// TODO Auto-generated constructor stub
		this.statusCode = code;
		this.errorMessage = errMsg;
		this.data = data2;
		this.user = (User) object;
	}
	//方法
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	//方法
	public static ResponseVo result(Integer code, String errMsg, Object data) {
        return new ResponseVo(code, errMsg, data,null);
    }
	public static ResponseVo result(Integer code, String errMsg, User user) {
        return new ResponseVo(code, errMsg, null,user);
    }
    public static ResponseVo resultLogin(Integer code, String errMsg, Object data, User user) {
        return new ResponseVo(code, errMsg, data,user);
    }
    public static ResponseVo error(Integer code, String errMsg) {
        return result(code, errMsg, null);
    }

    public static ResponseVo error(String errMsg) {
        return result(500, errMsg, null);
    }

    public static ResponseVo error() {
        return result(500, null, null);
    }

    //状态
    public static ResponseVo success(Object data) {
        return result(200, null, data);
    }
    public static ResponseVo success(User user) {
        return result(200, null, user);
    }
    public static ResponseVo success(Object data, User user) {
        return resultLogin(200, null, data, user);
    }
    public static ResponseVo success() {
        return result(200, null, null);
    }
	
}
