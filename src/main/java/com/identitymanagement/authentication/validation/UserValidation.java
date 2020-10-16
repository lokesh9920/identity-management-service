package com.identitymanagement.authentication.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.identitymanagement.authentication.crypto.MD5;
import com.identitymanagement.authentication.exception.InvalidCredentials;
import com.identitymanagement.authentication.exception.RegistrationFailed;
import com.identitymanagement.authentication.exception.UnprocessableRequest;
import com.identitymanagement.authentication.model.UserDetails;
import com.identitymanagement.authentication.model.UserSecrets;
import com.identitymanagement.authentication.validation.mapper.UserOperationsMapper;


@Service
public class UserValidation {

	@Autowired
	private UserOperationsMapper userOperationsMapper;
	
	@Autowired
	MD5 md5;
	public boolean isUserGoodToRegister(UserDetails userDetails){
		isUserNameUnique(userDetails.getUserName());
		isMailIdUnique(userDetails.getMailId());
		return true;
	}
	
	public boolean isUserExisting(String userName) {
		int count = userOperationsMapper.getUserbyUserName(userName);
		if(count != 1) 
			throw new UnprocessableRequest("user not registered yet");
		return true;
		
	}
	public boolean isUserGoodToLogin(UserSecrets user) {
		
		String hashedPassword = md5.generateMd5Hash(user.getPassword());
		user.setPassword(hashedPassword);
		
		int count = userOperationsMapper.validateUser(user);
		if(count!=1)
			throw new InvalidCredentials("Invalid Credentials");
		return true;
		
	}
	
	private void isUserNameUnique(String userName) {
		int count = userOperationsMapper.getUserbyUserName(userName);
		if(count!=0)
			throw new RegistrationFailed("UserName should be unique");
		
	}
	
	private void isMailIdUnique(String mailId) {
		int count = userOperationsMapper.getUserbyMail(mailId);
		if(count!=0)
			throw new RegistrationFailed("Mail id already registered");
	}
	
	
	
}
