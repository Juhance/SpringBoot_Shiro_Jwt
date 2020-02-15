package com.cqust.exception;

public class cachException extends Exception{
	
	private static final long serialVersionUID = -712029516708476133L;
	
	public static String msg=null;

    
	public cachException(String msg) {
		this.msg = msg;
	}
}
