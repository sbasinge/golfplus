package com.basinc.golfminus.notifier;

import java.util.Properties;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.solder.logging.Logger;

import com.basinc.golfminus.account.MembershipAccepted;
import com.basinc.golfminus.account.MembershipRejected;
import com.basinc.golfminus.domain.MembershipRequest;
import com.basinc.golfminus.domain.Score;
import com.basinc.golfminus.domain.TeeTime;
import com.basinc.golfminus.domain.TeeTimeParticipant;
import com.basinc.golfminus.domain.User;
import com.basinc.golfminus.security.Registered;
import com.basinc.golfminus.view.club.HandicapCalculated;

public class EmailNotifier implements MessageNotifier {
	@Inject
	Logger log;

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_PORT = "465";
	private static final String emailFromAddress = "cgmc@gmail.com";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private static final String ACCOUNT_ID = "kdkljdskljdfkl@gmail.com";
	private static final String ACCOUNT_PWD = "password";

	public void registrationCompleted(@Observes @Registered MembershipRequest membershipRequest) {
		log.warn("Membership completed notification.");
	}

	public void membershipRejected(@Observes @MembershipRejected MembershipRequest membershipRequest) {
		log.warn("Membership rejected notification.");
	}

	public void membershipAccepted(@Observes @MembershipAccepted MembershipRequest membershipRequest) {
		log.warn("Membership accecpted notification.");
	}

	public void handicapCalculated(@Observes @HandicapCalculated User user) {
		log.warn("Handicap calculated notification.");
		String subject = "You're handicap has been updated....";
		String text = "You're current handicap is a " + user.getHandicap();
		String[] sendTo = { user.getEmail() };
		
		if (user.getHandicapCalculateNotificationType().isNotifyByEmail() || 
			user.getHandicapCalculateNotificationType().isNotifyByText()) {
//			try {
//				sendEmailMessage(sendTo, subject, text, emailFromAddress);
//			} catch (MessagingException e) {
//				log.error("Error sending email.",e);
//			}
		}
	}


	public void sendEmailMessage(String recipients[], String subject, String message, String from) throws MessagingException {
		boolean debug = true;

		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ACCOUNT_ID, ACCOUNT_PWD);
			}
		});

		session.setDebug(debug);

		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		Transport.send(msg);
	}

	public void scoreUpdated(Score score) {
		// TODO Auto-generated method stub
		
	}

	public void teetimeAdded(TeeTime teetime) {
		// TODO Auto-generated method stub
		
	}

	public void teetimeUpdated(TeeTimeParticipant participant) {
		// TODO Auto-generated method stub
		
	}

	public void teetimeDeleted(TeeTime teetime) {
		// TODO Auto-generated method stub
		
	}
}
