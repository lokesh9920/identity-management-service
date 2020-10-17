package com.identitymanagement.authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter @AllArgsConstructor
public class InvalidCredentials extends RuntimeException{

	private String message;
}
