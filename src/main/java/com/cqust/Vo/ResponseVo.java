package com.cqust.Vo;

import java.io.Serializable;

import com.cqust.enitiy.User;

public class ResponseVo implements Serializable {

	/**
	 * ����ID
	 */
	private static final long serialVersionUID = -4771625271929822747L;
	//����	
	private Integer statusCode;//״̬��  200����
	private String errorMessage;//������ʾ
	private Object data;//����ҳ���data����
	private User user;//����ҳ�������Ϣ
	
	public ResponseVo(Integer code, String errMsg, Object data2, Object object) {
		// TODO Auto-generated constructor stub
		this.statusCode = code;
		this.errorMessage = errMsg;
		this.data = data2;
		this.user = (User) object;
	}
	//����
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
	//����
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

    //״̬
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
