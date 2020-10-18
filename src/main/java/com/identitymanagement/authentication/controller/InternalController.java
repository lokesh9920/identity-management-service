package com.identitymanagement.authentication.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.identitymanagement.authentication.mint.ApiKeyMint;
import com.identitymanagement.authentication.model.ApiKeyGenDetails;

@RestController
@Profile("dev")
@CrossOrigin
public class InternalController {
	
	@Autowired
	ApiKeyMint apiKeyMint;
	
	@PostMapping("/internal/accesskeys")
	public ResponseEntity<Object> generateApiKey(@RequestBody ApiKeyGenDetails customerRequirement) throws Exception{
	
		String apiKey = apiKeyMint.generateApiKey(customerRequirement.getCustomer(), customerRequirement.getHowLong());
		if(apiKey.equals("")) throw new Exception("Error while AES Encryption");
		HashMap<String, Object> generationDetails  = new HashMap<String, Object>();
		
		generationDetails.put("customer", customerRequirement.getCustomer());
		generationDetails.put("validTill", customerRequirement.getHowLong());
		generationDetails.put("apiKey", apiKey);
		
		return new ResponseEntity<Object>(generationDetails,HttpStatus.CREATED);
	}
}
