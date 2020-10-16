package com.identitymanagement.authentication.logging;

public class LoggingConstants {
	
	public static final String REQUEST_LOGGER = "[Request Recieved]: {}: {}";
	public static final String FAILED_VALIDATION = "[Validation Failed]: Recieved {} = {}";
	public static final String RESPONSE_BODY = "[Response] to {}:{} is {} and content is {}";
	
	public static final String REST_REQUEST_ATTEMPT = "[Rest Request] Attempting to reach {} to {}";
	public static final String REST_REQUEST_STATUS_REPORT= "[Rest Request Report] {} responded with {}";
	

	public static final String HANDLED_EXCEPTION = "[EXCEPTION HANDLED] Responded with: {}, The stack trace is: {}";
	
	public static final String FAILED_ENCRYPTION = "[FAILED ENCRYPTION]: {} encryption failed for {}, The error message is: {}, and stack trace is: {}";
	public static final String FAILED_DECRYPTION = "[FAILED DECRYPTION]: {} decryption failed for {}, The error message is: {}, and stack trace is: {}";
	public static final String ABORTING_OPERATION = "[ABORTING OPERATION] Aborting to perform {} for {} due to {}";
	
	public static final String BAD_VIEW_RESPONSE = "[HTML RESPONSE]: Responded with {} for {}, reason: {}";
	
	public static final String MAIL_SENT_SUCCESS = "[MAILING SUCCESSFUL]: Mail sent successfully to {} from {} for {}";
	public static final String MAIL_SENT_FAILED = "[MAILING FAILED]: Failed to Mail at {} for {}, reason: {}";

	public static final String INVALID_TOKEN = "[INVALID TOKEN]: Received {}: {}";
	public static final String TOKEN_MINTED = "[TOKEN MINTED]: Minted token successfully for {}";
	public static final String TOKEN_VALIDATION_REQUESTED = "[TOKEN VALIDATION]: Token Validation Requested";
	public static final String TOKEN_VALIDATION_SUCCESS = "[TOKEN VALIDATED]: Token Validation Successful";
	
	public static final String RESOURCE_CREATION = "[RESOURCE CREATION]: Creating new {}: {}";
	public static final String RESOURCE_CREATION_SUCCESSFUL = "[RESOURCE CREATED]: Created new {}: {}, successfully";

	
}
