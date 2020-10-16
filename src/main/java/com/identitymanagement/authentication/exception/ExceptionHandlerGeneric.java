package com.identitymanagement.authentication.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.identitymanagement.authentication.logging.LoggingConstants;

@ControllerAdvice
public class ExceptionHandlerGeneric {
	Logger logger = LogManager.getLogger(ExceptionHandlerGeneric.class);
	
	@ExceptionHandler(value = {NumberFormatException.class,MissingServletRequestParameterException.class})
	public ResponseEntity<GenericException> handleBadRequests(Exception parentException){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.BAD_REQUEST,parentException.getStackTrace());
		GenericException genericException = new GenericException("Malformed or Missing Parameters");
		return new ResponseEntity<GenericException>(genericException, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(value = RegistrationFailed.class)
	public ResponseEntity<GenericException> handleFailureRegistrations(RuntimeException parentException){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.CONFLICT,parentException.getStackTrace());
		GenericException genericException = new GenericException(parentException.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = {MissingRequestHeaderException.class,MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
	public ResponseEntity<GenericException> handleInvalidRequests(Exception exception){

		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.BAD_REQUEST,exception.getStackTrace());
		GenericException genericException = new GenericException("Invalid Request");
		return new ResponseEntity<GenericException>(genericException, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<GenericException> handleIncorrectMethods(HttpRequestMethodNotSupportedException exception){
		 
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.METHOD_NOT_ALLOWED,exception.getStackTrace());
		GenericException genericException = new GenericException(exception.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(value = UnprocessableRequest.class)
	public ResponseEntity<GenericException> handleUnprocessableRequests(UnprocessableRequest exception){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.UNPROCESSABLE_ENTITY,exception.getStackTrace());
		GenericException genericException = new GenericException(exception.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(value = InvalidToken.class)
	public ResponseEntity<GenericException> handleInvalidTokens(RuntimeException exception){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.UNAUTHORIZED,exception.getStackTrace());
		GenericException genericException = new GenericException(exception.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = LinkExpiredException.class)
	public ResponseEntity<GenericException> handleExpiredLinks(RuntimeException exception){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.GONE,exception.getStackTrace());
		GenericException genericException = new GenericException(exception.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.GONE);
	}
	
	@ExceptionHandler(value = InvalidCredentials.class)
	public ResponseEntity<GenericException> handleInvalidCredentials(RuntimeException exception){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.UNAUTHORIZED,exception.getStackTrace());
		GenericException genericException = new GenericException(exception.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<GenericException> handleNoResource(RuntimeException exception){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.NOT_FOUND,exception.getStackTrace());
		GenericException genericException = new GenericException(exception.getMessage());
		return new ResponseEntity<GenericException>(genericException, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<GenericException> Megahandler(Exception exception){
		
		logger.error(LoggingConstants.HANDLED_EXCEPTION,HttpStatus.INTERNAL_SERVER_ERROR,exception.getStackTrace());
		GenericException genericException = new GenericException("Unexpected Error Occured");
		return new ResponseEntity<GenericException>(genericException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
