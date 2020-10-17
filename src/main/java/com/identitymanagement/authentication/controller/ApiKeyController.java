package com.identitymanagement.authentication.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.identitymanagement.authentication.exception.ResourceNotFoundException;
import com.identitymanagement.authentication.mint.ApikeyExtractor;
import com.identitymanagement.authentication.model.ApiKeyDetails;

@RestController
public class ApiKeyController {
	
	@Autowired
	ApikeyExtractor apiKeyExtractor;
	
	@GetMapping("/accesskeys")
	public ResponseEntity<Object> getApiKeyDetails(@RequestParam(value = "key",required = true) String apiKey){
		
		String decryptedString = apiKeyExtractor.getDecrypted(apiKey);
		
		int seperatorIndex = decryptedString.indexOf(',');
		
		if(seperatorIndex == -1) throw new ResourceNotFoundException("Invalid Api key");
		String customerName = decryptedString.substring(0, seperatorIndex);
		long linkExpireTime = Long.parseLong(decryptedString.substring(seperatorIndex+1));
		Date expireDate = new Date(linkExpireTime);
		
		ApiKeyDetails apiKeyDetails = new ApiKeyDetails(customerName, expireDate);
		return new ResponseEntity<Object>(apiKeyDetails,HttpStatus.OK);
	}
}
