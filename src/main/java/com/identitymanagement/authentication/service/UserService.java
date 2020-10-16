package com.identitymanagement.authentication.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.identitymanagement.authentication.crypto.MD5;
import com.identitymanagement.authentication.entity.User;
import com.identitymanagement.authentication.exception.LinkExpiredException;
import com.identitymanagement.authentication.exception.ResourceNotFoundException;
import com.identitymanagement.authentication.logging.LoggingConstants;
import com.identitymanagement.authentication.mapper.UserMapper;
import com.identitymanagement.authentication.model.Password;
import com.identitymanagement.authentication.model.UserDetails;

@Service
public class UserService {

	
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	UniqueIdentifierMint uniqueIdentifierMint;
	
	@Autowired
	MailingService mailingService;
	
	@Autowired
	MD5 md5;
	
	private final Logger logger = LogManager.getLogger(UserService.class);
		
	@Async
	public void verifyUserAndSendResetLink(String userName,String currentURI) {
		User user = userMapper.getUserByUserName(userName);
		if(user == null) return;
		
		//if user is existing then send mail with link to reset password
		
		String encryptedId = uniqueIdentifierMint.getUniqueIdentifier(String.valueOf(user.getId()));
		if(encryptedId.equals("")) {
			logger.error(LoggingConstants.ABORTING_OPERATION, "Send Mail with reset link", userName, "Empty AES Encrypted String");
			return;
		}		
		
		mailingService.sendMail(user.getMailId(), currentURI+"/"+encryptedId, user.getFirstName());
		
		
		
	}
	
	public long validateLinkLifeAndParseUserId(String uniqueIdentifier) {
		String decryptedString = uniqueIdentifierMint.getDecrypted(uniqueIdentifier);
		
		int seperatorIndex = decryptedString.indexOf(',');
		
		if(seperatorIndex == -1) throw new ResourceNotFoundException("Link Broken");
		long userId = Long.parseLong(decryptedString.substring(0, seperatorIndex));
		long linkExpireTime = Long.parseLong(decryptedString.substring(seperatorIndex+1));

		if(!(linkExpireTime>System.currentTimeMillis())) throw new LinkExpiredException("The link is expired");
		
		return userId;
	}
	
	public void updatePassword(long userId,Password user) {
		String hashedPassword = md5.generateMd5Hash(user.getPassword());
		user.setPassword(hashedPassword);
		
		int rowsAffected = userMapper.updatePassword(userId, user.getPassword());
		if(rowsAffected == 0) throw new ResourceNotFoundException("Link Broken");
		return;
	}
	
	public User createUser(UserDetails userDetails) {
		String hashedPassword = md5.generateMd5Hash(userDetails.getPassword());
		userDetails.setPassword(hashedPassword);
		
		logger.info(LoggingConstants.RESOURCE_CREATION,"User", userDetails.getUserName());
		userMapper.createUser(userDetails);
		logger.info(LoggingConstants.RESOURCE_CREATION_SUCCESSFUL,"User", userDetails.getUserName());
		return userMapper.getUserByUserName(userDetails.getUserName());
		
	}

}
