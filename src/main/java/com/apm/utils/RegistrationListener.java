package com.apm.utils;

import java.util.UUID;

import org.mortbay.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.apm.Mappings;
import com.apm.repos.VerificationTokenRepository;
import com.apm.repos.models.APMUser;
import com.apm.repos.models.VerificationToken;
import com.apm.service.APMUserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    
	@Value("${email.sender.username}")
	private String username;
	
	@Value("${apm.server.address}")
	private String serverAddress;
	
	@Value("${apm.server.port}")
	private String serverPort;
	
	@Value("${apm.server.protocol}")
	private String serverProtocol;
	
	@Autowired
    private MessageByLocaleService messages;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
	private VerificationTokenRepository tokenRepo;
    
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        APMUser user = event.getUser();
        String token = UUID.randomUUID().toString();
        createVerificationToken(token, user);
         
        //send email to new user
        String recipientAddress = user.getUsername();
        String subject = "APM Registration Confirmation";
        // Path to set is: http://hostname:port/v1/users/{userId}/registrationConfirm?token=12345
        String confirmationUrl = StringUtil.replace(Mappings.API_BASE_PATH + APMUserService.API_USER_CONFIRM_REGISTRATION, "{userId}", user.getUserId()+"") +"?token="+ token;
        String message = messages.getMessage("message.regSucc");
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(username);
        email.setTo(recipientAddress);
        email.setSubject(subject);
        
        // if serverPort is 80, do not append to the final URL
        if(serverPort.equals("80"))
        	message = message + serverProtocol +"://"+ serverAddress + confirmationUrl;
        else
        	message = message + serverProtocol +"://"+ serverAddress+":"+serverPort + confirmationUrl;

       	email.setText(message);
        mailSender.send(email);
    }
    
    public void createVerificationToken(String token, APMUser user) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepo.save(myToken);
    }
}