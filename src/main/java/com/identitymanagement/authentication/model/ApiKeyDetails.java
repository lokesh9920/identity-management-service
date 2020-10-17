package com.identitymanagement.authentication.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ApiKeyDetails {

	private String customer;
	private Date validTill;
}
