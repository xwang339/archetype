package com.lixin.web.controller;

import com.lixin.utils.constants.Constants;
import com.lixin.dal.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

//@ControllerAdvice
@RestControllerAdvice
public class UnifyExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(UnifyExceptionHandler.class);

	@ExceptionHandler({ RuntimeException.class })
	public Result<String> runtimeExceptionHandle(RuntimeException e) {
		log.error(e.getMessage(),e);
		if (StringUtils.isBlank(e.getMessage())) {
			return Result.error(-1, Constants.RUNTIME_ERROR);
		} else {
			return Result.error(-1, e.getMessage());
		}
	}

	@ExceptionHandler({ DataAccessException.class })
	public Result<String> dataAccessExceptionHandle(DataAccessException e) {
		log.error(e.getMessage(),e);
		if (StringUtils.isBlank(e.getMessage())) {
			return Result.error(-2, Constants.DATAFAILED_ERROR);
		} else {
			return Result.error(-2, e.getMessage());
		}
	}

	@ExceptionHandler({ Exception.class })
	public Result<String> exceptionHandle(Exception e) {
		log.error(e.getMessage(),e);
		if (e instanceof NoHandlerFoundException) {
			return Result.error(-404,"The requested interface is not exist");
		} else if (e instanceof IllegalArgumentException) {
			return Result.error(-4,"The request parameter is incorrect");
		} else if (e instanceof IllegalStateException) {
			return Result.error(-5,e.getMessage());
		} else if (e instanceof BindException) {
			BindException ex = (BindException) e;
			List<ObjectError> allErrors = ex.getAllErrors();
			ObjectError error = allErrors.get(0);
			String defaultMessage = error.getDefaultMessage();
			return Result.error(-6,defaultMessage);
		} else if (e instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
			List<ObjectError> errors = ex.getBindingResult().getAllErrors();
			String message = errors.get(0).getDefaultMessage();
			return Result.error(message);
		} else {
			return Result.error(-9999,Constants.UNKNOW_ERROR);
		}
	}
}
