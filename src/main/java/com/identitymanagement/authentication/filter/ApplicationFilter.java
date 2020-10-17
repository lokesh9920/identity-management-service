package com.identitymanagement.authentication.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.identitymanagement.authentication.exception.InvalidToken;
import com.identitymanagement.authentication.logging.LoggingConstants;
import com.identitymanagement.authentication.mint.ApikeyExtractor;

//dont add component annotation here.
//for @webfilter to work add @servletcomponentscan in main class only then it will work, also dont work if @component is given here.

@ConditionalOnProperty("filter.api.flag.enable")
@WebFilter(value = {"/tokens/*","/users/register","/users/login","/users/account-management"})
public class ApplicationFilter implements Filter{

	
	@Autowired
	ApikeyExtractor apiKeyExtractor;
	
	Logger logger = LogManager.getLogger(ApplicationFilter.class);
	
	@SuppressWarnings("unused")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResposne = (HttpServletResponse) response;
		
		logger.info(LoggingConstants.HOST_NAME, httpRequest.getRemoteHost());
		
		String apiKey = httpRequest.getHeader("X-idms-Auth");

			try {
				String decryptedString = apiKeyExtractor.getDecrypted(apiKey);
				
				int seperatorIndex = decryptedString.indexOf(',');  // customer,expiretimeinmillis formated api key
				
				if(seperatorIndex == -1) throw new InvalidToken("Invalid Api key");
				//TODO: I can add check if customerName is same or not by taking from env variables
				String customerName = decryptedString.substring(0, seperatorIndex);
				long linkExpireTime = Long.parseLong(decryptedString.substring(seperatorIndex+1));
				
				if(linkExpireTime<System.currentTimeMillis()) throw new InvalidToken("Api key Expired");
				
				// authenticated successfully
				chain.doFilter(request, response);
			}catch (InvalidToken e) {
				httpResposne.addHeader("www-Authenticate", "request at tangella.lokesh@gmail.com for API Key");
				httpResposne.sendError(401, e.getMessage());
			}
	
		}

}
