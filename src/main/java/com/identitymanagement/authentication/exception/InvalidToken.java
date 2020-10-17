package com.identitymanagement.authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter @AllArgsConstructor
public class InvalidToken extends RuntimeException{
	
	private String message;
}
