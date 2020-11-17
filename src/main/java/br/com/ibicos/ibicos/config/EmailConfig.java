package br.com.ibicos.ibicos.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;



@Configuration
public class EmailConfig {
	
	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.port}")
	private Integer port;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String mailSmtpAuth;
	
	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String mailSmtpStarttlsEnable;
	

	@Bean
	public JavaMailSender getJavaEmailSender() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		
		javaMailSenderImpl.setHost(host);
		javaMailSenderImpl.setPort(port);
		
		javaMailSenderImpl.setUsername(username);
		javaMailSenderImpl.setPassword(password);
		
		Properties emailProperties = javaMailSenderImpl.getJavaMailProperties();
		emailProperties.put("mail.transport.protocol", "smtp");
		emailProperties.put("mail.smtp.auth", mailSmtpAuth);
		emailProperties.put("mail.smtp.starttls.enable", mailSmtpStarttlsEnable);
		emailProperties.put("mail.debug", "false");
		
		return javaMailSenderImpl;
	}
	
}
