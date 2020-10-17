package com.identitymanagement.authentication.mint;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApikeyExtractor {

	@Value("${api.key.secret}")
	private String apiMintSecret;
	
	public String getDecrypted(String encryptedString) {
		String decrypted = "";
		try {
			decrypted = apiAesDecrypt(encryptedString);
		} catch (Exception e) {
			//logger.error(LoggingConstants.FAILED_ENCRYPTION, "AES", encryptedString, e.getMessage(),e.getStackTrace());
		}	
		
		return decrypted;
	} 
	
	private String apiAesDecrypt(String encryptedString) throws Exception{
		
		SecretKeySpec secretKeySpec = new SecretKeySpec(apiMintSecret.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		
		byte[] original = cipher.doFinal(Base64.decodeBase64URLSafe(encryptedString));
		String decryptedString = new String(original);
		
		return decryptedString;
	}
}
