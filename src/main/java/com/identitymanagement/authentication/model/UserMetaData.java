package com.identitymanagement.authentication.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

import com.identitymanagement.authentication.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserMetaData {
	
	private long id;
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
	@Email
	private String mailId;
	@NotEmpty
	private String userName;
	
	public void extractMetaInfo(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.mailId = user.getMailId();
		this.userName = user.getUserName();
	}
}
