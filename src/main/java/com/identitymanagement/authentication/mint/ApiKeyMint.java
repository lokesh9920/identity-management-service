package com.identitymanagement.authentication.mint;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class ApiKeyMint {

	@Value("${api.key.secret}")
	private String apiMintSecret;
	
	public String generateApiKey(String customer, long howLong) {
				
				String encrypted = "";
				try {
					String stringToEncrypt = customer + "," + String.valueOf(System.currentTimeMillis()+howLong);
					encrypted = apiAesEncrypt(stringToEncrypt);
					
				} catch (Exception e) {
					//logger.error(LoggingConstants.FAILED_ENCRYPTION, "AES", customer, e.getMessage(),e.getStackTrace());
				}	
				
				return encrypted;
			}
	
		
		private String apiAesEncrypt(String stringToEncrypt) throws Exception {
			
			SecretKeySpec secretKeySpec = new SecretKeySpec(apiMintSecret.getBytes("UTF-8"), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			
			byte[] encryptedBytes = cipher.doFinal(stringToEncrypt.getBytes());
			return Base64.encodeBase64URLSafeString(encryptedBytes);
		}
		
		
}
