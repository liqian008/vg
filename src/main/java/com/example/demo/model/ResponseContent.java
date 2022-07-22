package com.example.demo.model;

import com.example.demo.exception.GenericRuntimeException;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用请求响应对象
 * PS：只定义结构，不定义输出类型（SpringMvc中可根据需要进行配置，使用json、xml或其他方式输出）
 * @author bruce
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="请求响应对象")
public class ResponseContent<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 成功的code码为0 */
	public static final int CODE_SUCCESS = 0;
	/** 其他均为失败，在此先默认给1， 各业务中可自行分配错误码 */
	public static final int CODE_ERROR = 1;

	/** 返回码 */
	private int code = CODE_SUCCESS;
	/** 消息 */
	private String message;

	/** 业务数据 */
	private T data;

	/**
	 * 构造成功的响应
	 * @param data
	 * @return
	 */
	public static <T> ResponseContent<T> buildSuccessResult(T data){
		return buildSuccessResult(data, null);
	}

	/**
	 * 构造成功的响应
	 * @param data
	 * @param message
	 * @return
	 */
	public static <T> ResponseContent<T> buildSuccessResult(T data, String message){
		ResponseContent<T> result = new ResponseContent<>();
		result.setCode(CODE_SUCCESS);
		result.setData(data);
		result.setMessage(message);
		return result;
	}

	/**
	 * 构造失败的响应
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseContent<T> buildErrorResult(int code){
		return buildErrorResult(code, null);
	}

	/**
	 * 构造失败的响应
	 * @param code
	 * @param message
	 * @return
	 */
	public static <T> ResponseContent<T> buildErrorResult(int code, String message){
		return buildErrorResult(code, null,message);
	}

	/**
	 * 构造失败的响应
	 * @param code
	 * @param data
	 * @param message
	 * @return
	 */
	public static <T> ResponseContent<T> buildErrorResult(int code, T data, String message){
		ResponseContent<T> result = new ResponseContent<>();
		result.setCode(code);
		result.setData(data);
		result.setMessage(message);
		return result;
	}

	/**
	 *
	 * @param genericRuntimeException
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseContent<T> buildErrorResult(GenericRuntimeException genericRuntimeException){
		return buildErrorResult(genericRuntimeException.getCode(), genericRuntimeException.getMessage());
	}

	/**
	 * 是否为成功的相应
	 * @param responseContent
	 * @return
	 */
	public static boolean isSuccess(ResponseContent responseContent){
		return responseContent!=null && isSuccess(responseContent.getCode());
	}


	public static boolean isSuccess(int code){
		return code == CODE_SUCCESS;
	}



}
