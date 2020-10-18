package com.identitymanagement.authentication.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identitymanagement.authentication.logging.LoggingConstants;
import com.identitymanagement.authentication.mint.TokenMint;
import com.identitymanagement.authentication.model.Password;
import com.identitymanagement.authentication.model.UserName;
import com.identitymanagement.authentication.service.UserService;

@RestController
@RequestMapping("/users/account-management")
@CrossOrigin
public class AccountManagementController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	TokenMint tokenService;
	
	Logger logger = LogManager.getLogger(AccountManagementController.class);
	
	@PutMapping
	public ResponseEntity<Object> requestResetPasswordLink(@Valid @RequestBody UserName user, ServletRequest request){
		
		logger.info(LoggingConstants.REQUEST_LOGGER,"PUT","/users/account-management");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		StringBuffer url = httpRequest.getRequestURL();
		
		userService.verifyUserAndSendResetLink(user.getUserName(),url.toString());
		
		return new ResponseEntity<Object>(HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{uniqueIdentifier}")
	@CrossOrigin
	public ResponseEntity<Object> resetPassword(@Valid @RequestBody Password user, @PathVariable(value = "uniqueIdentifier") String uniqueIdentifier, HttpServletRequest httpRequest){
		
		logger.info(LoggingConstants.REQUEST_LOGGER,"PUT","/users/account-management/{uqid}");

		
		long userId = userService.validateLinkLifeAndParseUserId(uniqueIdentifier);
		userService.updatePassword(userId,user);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
