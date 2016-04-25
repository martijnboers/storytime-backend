package Mail;
//File Name SendEmail.java

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Mailer {
	public void sentPasswordMail(String email, String token) {
		// Recipient's email ID needs to be mentioned.
		String to = email;

		// Sender's email ID needs to be mentioned
		String from = "noreply@story.social";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.port", 587);
		properties.put("mail.smtp.host", "box.plebian.nl");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.user", "noreply@story.social");
		properties.put("mail.password", "TR@d@$x$G2!W#fa");

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("noreply@story.social", "TR@d@$x$G2!W#fa");
			}
		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Nieuw wachtwoord - Story.social");

			// Now set the actual message
			message.setText(
					"Beste lezer, via deze link kunt u het wachtwoord resetten: https://story.social/forgotpassword/"
							+ token);

			// Send message

			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
