package com.identitymanagement.authentication.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.identitymanagement.authentication.exception.LinkExpiredException;
import com.identitymanagement.authentication.exception.ResourceNotFoundException;
import com.identitymanagement.authentication.logging.LoggingConstants;
import com.identitymanagement.authentication.service.UserService;

@Controller
@RequestMapping("/users/account-management")
@CrossOrigin
public class AccountManagementViewsController {
	
	@Autowired
	UserService userService;
	
	Logger logger = LogManager.getLogger(AccountManagementViewsController.class);
	
	@GetMapping("/{uniqueIdentifier}")
	public String resetPasswordPage(@PathVariable(value = "uniqueIdentifier") String uniqueIdentifier, HttpServletRequest httpRequest,Model model) {
		
		logger.info(LoggingConstants.REQUEST_LOGGER,"GET","/users/account-management");

		try{
			userService.validateLinkLifeAndParseUserId(uniqueIdentifier); 
		}
		catch (LinkExpiredException exception) {
			logger.info(LoggingConstants.BAD_VIEW_RESPONSE,"badpage",uniqueIdentifier,"Link Expired");
			model.addAttribute("whyBad", "Uh oh! you are late, Our server killed this link before you come");
			return "badpage";
		}
		catch (ResourceNotFoundException e) {
			logger.info(LoggingConstants.BAD_VIEW_RESPONSE,"badpage",uniqueIdentifier,"Tampered Link");
			model.addAttribute("whyBad", "Uh oh! It's on you now, Seems like you opened the wrong link");
			return "badpage";
		}
		
		// link is good and verified
		
		logger.info(LoggingConstants.BAD_VIEW_RESPONSE,"resetpassword page",uniqueIdentifier,"Valid Link");

		model.addAttribute("uqid", uniqueIdentifier);
		return "resetpassword";
	}
	
}