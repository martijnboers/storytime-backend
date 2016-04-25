package mail;
//File Name SendEmail.java

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import exceptions.MissingPropertiesFile;
import logging.Level;
import logging.Logger;
import model.DbConfiguration;
import model.EmailConfiguration;

import javax.activation.*;

public class Mailer {
	String result = "";
	InputStream inputStream;

	/**
	 * Fetches email configuration to be used in the connector
	 * 
	 * @return EmailConfiguration 
	 * @throws IOException
	 * @throws MissingPropertiesFile
	 */
	public EmailConfiguration getPropValues() throws IOException, MissingPropertiesFile{
		Logger log = Logger.getInstance();
		EmailConfiguration configuration = null;
		Properties prop = new Properties();
		String propFileName = "init/storytime.properties";
		inputStream = this.getClass().getClassLoader().getResource(propFileName).openStream();

		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			log.out(Level.CRITICAL, "EmailProperties", "property file '" + propFileName + "' not found in the classpath");
			throw new MissingPropertiesFile("property file '" + propFileName + "' not found in the classpath");
		}
		configuration = new EmailConfiguration(prop.getProperty("mail_address"), prop.getProperty("mail_password"), prop.getProperty("mail_host"), prop.getProperty("mail_port"));

		inputStream.close();
		return configuration;
	}
	public void sentPasswordMail(String email, String token) throws IOException, MissingPropertiesFile {
		EmailConfiguration ec = getPropValues();
		// Recipient's email ID needs to be mentioned.
		String to = email;

		// Sender's email ID needs to be mentioned
		String from = ec.getEmail();

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.port", ec.getPort());
		properties.put("mail.smtp.host", ec.getHost());
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		properties.put("mail.user", ec.getEmail());
		properties.put("mail.password", ec.getPassword());

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ec.getEmail(), ec.getPassword());
			}
		});
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			message.setSentDate(new Date());
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Nieuw wachtwoord - Story.social");

			// Now set the actual message
			message.setText(
					"Beste meneer/mevrouw, via deze link kunt u het wachtwoord resetten: https://story.social/#/newpassword/"
							+ token + "/" + email);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
