package com.identitymanagement.authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class UnprocessableRequest extends RuntimeException{
	
	private String message;

}
