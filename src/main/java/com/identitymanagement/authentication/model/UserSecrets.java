package com.identitymanagement.authentication.model;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @ToString
public class UserSecrets {

	@NotEmpty
	private String userName;
	@NotEmpty
	private String password;
}
