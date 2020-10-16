package com.identitymanagement.authentication.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserDetails {
	
	private long id;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@Email
	private String mailId;
	@NotEmpty
	private String userName;
	@NotEmpty
	private String password;
	
}
