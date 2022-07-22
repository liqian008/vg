package com.example.demo.exception;

import com.example.demo.model.ResponseContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author bruce
 */
@Slf4j
@RestControllerAdvice
public class ExceptionResolver {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseContent error(Exception exception){
		log.error("[ExceptionResolver][error]enter, exception:{}", exception);
		log.error("[ExceptionResolver][error]", exception);
		exception.printStackTrace();
		ResponseContent result;
		if(exception!=null && exception instanceof GenericRuntimeException){
			result = ResponseContent.buildErrorResult((GenericRuntimeException) exception);
		}else{
			result = ResponseContent.buildErrorResult(ResponseContent.CODE_ERROR, exception.getMessage());
		}
		return result;
	}




}
