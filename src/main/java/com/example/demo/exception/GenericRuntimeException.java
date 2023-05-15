package com.example.demo.exception;

import com.example.demo.model.ResponseContent;

/**
 * 通用Runtime异常
 * @author bruce
 */
public class GenericRuntimeException extends RuntimeException{

	/** 返回码 */
	private int code = ResponseContent.CODE_ERROR;

	public GenericRuntimeException(){
		super();
	}

	public GenericRuntimeException(String message){
		super(message);
	}

	public GenericRuntimeException(int code, String message){
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
