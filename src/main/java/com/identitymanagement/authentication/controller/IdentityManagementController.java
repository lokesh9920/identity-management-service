package com.identitymanagement.authentication.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identitymanagement.authentication.entity.User;
import com.identitymanagement.authentication.logging.LoggingConstants;
import com.identitymanagement.authentication.mint.TokenMint;
import com.identitymanagement.authentication.model.UserDetails;
import com.identitymanagement.authentication.model.UserMetaData;
import com.identitymanagement.authentication.model.UserSecrets;
import com.identitymanagement.authentication.service.UserService;
import com.identitymanagement.authentication.validation.UserValidation;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class IdentityManagementController {

	
	@Autowired
	private UserValidation userValidation;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	TokenMint tokenService;
	
	
	Logger logger = LogManager.getLogger(IdentityManagementController.class);
	
	@PostMapping("/register")
	public ResponseEntity<UserMetaData> registerUser(@Valid @RequestBody UserDetails userDetails){
		logger.info(LoggingConstants.REQUEST_LOGGER,"POST","/register");
		
		userValidation.isUserGoodToRegister(userDetails);
		User user = userService.createUser(userDetails);
		UserMetaData userMetaData = new UserMetaData();
		userMetaData.extractMetaInfo(user);
		
		String uniqueJWTToken = tokenService.generateToken(userDetails.getUserName());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, uniqueJWTToken);
		
		return new ResponseEntity<UserMetaData>(userMetaData,httpHeaders,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> loginUser(@Valid @RequestBody UserSecrets user){
		logger.info(LoggingConstants.REQUEST_LOGGER,"POST","/login");
		
		userValidation.isUserGoodToLogin(user);
		
		String uniqueJWTToken = tokenService.generateToken(user.getUserName());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.AUTHORIZATION, uniqueJWTToken);
		
		
		return new ResponseEntity<Object>(httpHeaders,HttpStatus.OK);
	}
	

	
}
