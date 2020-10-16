package com.identitymanagement.authentication.service;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.identitymanagement.authentication.exception.InvalidToken;
import com.identitymanagement.authentication.logging.LoggingConstants;
import com.identitymanagement.authentication.validation.UserValidation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenMint {
	
	
	@Autowired
	UserValidation userValidation;
	
	@Value("${jwt.issuer}")
	String issuer;
	
	@Value("${jwt.validity}")
	long tokenValidity;
	
	@Value("${jwt.key}")
	String base64EncodedKey;
	
	Logger logger = LogManager.getLogger(TokenMint.class);
	
	public String generateToken(String userName) {
		
		HashMap<String, Object> tokenPayload = new HashMap<String, Object>();
		
		tokenPayload = getPayload(userName);
		
		String jwtToken = Jwts.builder().setClaims(tokenPayload)
				.signWith(SignatureAlgorithm.HS512, base64EncodedKey)
				.compact();
					
		logger.info(LoggingConstants.TOKEN_MINTED, userName);
		return jwtToken;
	}
	
	public String validateToken(String jwsToken) {
		try {
			
			logger.info(LoggingConstants.TOKEN_VALIDATION_REQUESTED);
			Claims tokenClaims = getClaimsFromToken(jwsToken);
			
			long tokenLife = Long.parseLong(tokenClaims.get("valid_through").toString());
			long tokenBirth = Long.parseLong(tokenClaims.get("issued_at").toString());
			if(!tokenClaims.get("issuer").equals(issuer))
				throw new InvalidToken("Token is Tampered");
			if(tokenLife < System.currentTimeMillis())
				throw new InvalidToken("Token Expired");
			if(! (tokenBirth < System.currentTimeMillis()))
				throw new InvalidToken("Token is Tampered");
			validateUser(tokenClaims.get("issued_to").toString());
			
			logger.info(LoggingConstants.TOKEN_VALIDATION_SUCCESS);
			
			return tokenClaims.get("issued_to").toString();
		}catch (Exception e) {
			logger.warn(LoggingConstants.INVALID_TOKEN,"Invalid Token",jwsToken);
			throw new InvalidToken(e.getMessage());
		}
	}
	
	private boolean validateUser(String userName) {
		
		if(userName.equals("") || userName == null)
			throw new RuntimeException("Invalid User");
		
		userValidation.isUserExisting(userName);
		return true;
	}
	
	private Claims getClaimsFromToken(String jwtSignedToken) {
		
		Claims claims = Jwts.parser()
				.setSigningKey(base64EncodedKey)
				.parseClaimsJws(jwtSignedToken).getBody();
		
		
		return claims;
	}
	
	private HashMap<String, Object> getPayload(String userName){
		HashMap<String, Object> tokenPayload = new HashMap<String, Object>();
		long currentUnixEpoch = System.currentTimeMillis();
		
		tokenPayload.put("issuer", issuer);
		tokenPayload.put("issued_to", userName);
		tokenPayload.put("issued_at", currentUnixEpoch);
		tokenPayload.put("valid_through", currentUnixEpoch+tokenValidity);
		
		
		return tokenPayload;
	}
}
