package com.identitymanagement.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyGenDetails {

	private String customer;
	private long howLong;    // if 1 sec then this value should be 1000 i.e. 1000 ms actually
}
