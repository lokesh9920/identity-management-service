package com.identitymanagement.authentication.mint;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.identitymanagement.authentication.logging.LoggingConstants;

@Service
public class UniqueIdentifierMint {

	@Value("${aes.key}")
	private String aeskey;
	
	@Value("${reset.validity}")
	private long resetLinkValidity;
	
	private static final Logger logger = LogManager.getLogger(UniqueIdentifierMint.class);
	
	
	public String getUniqueIdentifier(String userId) {
		String encrypted = "";
		try {
			String stringToEncrypt = userId + "," + String.valueOf(System.currentTimeMillis()+resetLinkValidity);
			encrypted = aesEncrypt(stringToEncrypt);
			
		} catch (Exception e) {
			logger.error(LoggingConstants.FAILED_ENCRYPTION, "AES", userId, e.getMessage(),e.getStackTrace());
		}	
		
		return encrypted;
	}
	
	public String getDecrypted(String encryptedString) {
		String decrypted = "";
		try {
			decrypted = aesDecrypt(encryptedString);
		} catch (Exception e) {
			logger.error(LoggingConstants.FAILED_ENCRYPTION, "AES", encryptedString, e.getMessage(),e.getStackTrace());
		}	
		
		return decrypted;
	}
	
	private String aesEncrypt(String stringToEncrypt) throws Exception {
		
		SecretKeySpec secretKeySpec = new SecretKeySpec(aeskey.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		
		byte[] encryptedBytes = cipher.doFinal(stringToEncrypt.getBytes());
		return Base64.encodeBase64URLSafeString(encryptedBytes);
	}
	
	private String aesDecrypt(String encryptedString) throws Exception{
		
		SecretKeySpec secretKeySpec = new SecretKeySpec(aeskey.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		
		byte[] original = cipher.doFinal(Base64.decodeBase64URLSafe(encryptedString));
		String decryptedString = new String(original);
		
		return decryptedString;
	}
	
}
