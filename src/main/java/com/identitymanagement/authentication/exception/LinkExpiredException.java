package com.identitymanagement.authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkExpiredException extends RuntimeException{

	private String message;
	
}
