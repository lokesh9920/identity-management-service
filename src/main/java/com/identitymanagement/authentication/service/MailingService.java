package com.identitymanagement.authentication.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.identitymanagement.authentication.logging.LoggingConstants;

@Service
public class MailingService {

	@Value("${from.mail.id}")
	private String senderMail;
	
	@Value("${from.mail.pass}")
	private String senderMailPassword;
	
	Logger logger = LogManager.getLogger(MailingService.class);
	
	public void sendMail(String toMail, String resetLInk, String userName) {
		
		Properties properties = System.getProperties();

		String host = "smtp.gmail.com";
		properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", senderMail);
        properties.put("mail.smtp.password", senderMailPassword);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        
		Session session = Session.getDefaultInstance(properties);
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderMail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
			
			message.setSubject("Password Reset Link");
			message.setText("\nDear "+ userName +",\n\n\nThe password reset link is : " + resetLInk + "\n\nNOTE: This link is valid for next 10 minutes and is non-sharable,\n\nIgnore this mail if you haven't requested for a password reset.\n");
			
			Transport transport = session.getTransport("smtp");
			transport.connect(host, senderMail, senderMailPassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			logger.info(LoggingConstants.MAIL_SENT_SUCCESS, toMail, senderMail, "Password Reset Link");
			
		}catch (Exception e) {
			logger.error(LoggingConstants.MAIL_SENT_FAILED,toMail, "Password Reset Link", e.getMessage());
		}
		
	}
}
