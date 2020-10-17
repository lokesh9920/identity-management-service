package com.identitymanagement.authentication.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.identitymanagement.authentication.mint.TokenMint;

@RestController
@RequestMapping("/tokens/verify")
public class TokenController {

	@Autowired
	TokenMint tokenService;
	
	@GetMapping
	public ResponseEntity<Object> validateToken(@RequestHeader(name = "auth-token", required = true) String jwtToken){
		
		
		String userName = tokenService.validateToken(jwtToken, false);
		
		HashMap<String, Object> successMap = new HashMap<String, Object>();
		successMap.put("userName", userName);
		
		return new ResponseEntity<Object>(successMap, HttpStatus.OK);
	}
}
