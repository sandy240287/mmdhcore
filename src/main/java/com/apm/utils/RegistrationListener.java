package com.apm.utils;

import java.util.UUID;

import org.mortbay.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.apm.Mappings;
import com.apm.models.APMUser;
import com.apm.models.VerificationToken;
import com.apm.repos.VerificationTokenRepository;
import com.apm.service.APMUserService;
import com.sendgrid.SendGrid;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	protected static Logger logger = LoggerFactory.getLogger(RegistrationListener.class);

	@Value("${email.sender.username}")
	private String username;

	@Value("${apm.server.address}")
	private String serverAddress;

	@Value("${apm.server.port}")
	private String serverPort;

	@Value("${apm.server.protocol}")
	private String serverProtocol;

	@Value("${spring.sendgrid.username}")
	private String sendgridSenderUsername;
	@Value("${spring.sendgrid.password}")
	private String sendgridApiKey;
	@Value("${apm.sendgrid.sender.email}")
	private String sendgridSenderEmail;

	@Autowired
	private MessageByLocaleService messages;
	//@Autowired
	//private JavaMailSender mailSender;
	
	@Autowired
	private VerificationTokenRepository tokenRepo;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private APMResponse confirmRegistration(OnRegistrationCompleteEvent event) {
		APMUser user = event.getUser();
		String token = UUID.randomUUID().toString();
		createVerificationToken(token, user);

		// send email to new user
		String recipientEmail = user.getUsername();
		String subject = "APM Registration Confirmation";
		// Path to set is:
		// http://hostname:port/v1/users/{userId}/registrationConfirm?token=12345
		String confirmationUrl = StringUtil.replace(
				Mappings.API_BASE_PATH + APMUserService.API_USER_CONFIRM_REGISTRATION, "{userId}",
				user.getUserId() + "") + "?token=" + token;
		String emailMessage = messages.getMessage("message.regSucc");
		// if serverPort is 80, do not append to the final URL
		if (serverPort.equals("80"))
			emailMessage = emailMessage + serverProtocol + "://" + serverAddress + confirmationUrl;
		else
			emailMessage = emailMessage + serverProtocol + "://" + serverAddress + ":" + serverPort + confirmationUrl;

		
		/* commented to use SendGrid instead
		 * however downside of sendgrid is that messages land in span due to Yahoo's policy
		 * 
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(username);
		email.setTo(recipientEmail);
		email.setSubject(subject);
		email.setText(emailMessage);
		mailSender.send(email);
		 */
		
		// sendgrid email
		SendGrid sendgrid = new SendGrid(sendgridApiKey);

		SendGrid.Email sgEmail = new SendGrid.Email();
		sgEmail.setFromName(sendgridSenderUsername);
		sgEmail.addTo(recipientEmail);
		sgEmail.setFrom(sendgridSenderEmail);
		sgEmail.setSubject(subject);
		sgEmail.setHtml(emailMessage);

		SendGrid.Response response = null;
		try {
			response = sendgrid.send(sgEmail);
			logger.info("EMAIL_SUCCESS:"+response.getMessage());
			return new APMResponse("EMAIL_SUCCESS", response.getMessage());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return new APMResponse("EMAIL_ERROR", "An error has occurred while sending email!");
		}
	}

	public void createVerificationToken(String token, APMUser user) {
		VerificationToken myToken = new VerificationToken(token, user);
		tokenRepo.save(myToken);
	}
}