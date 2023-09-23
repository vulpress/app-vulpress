package hu.aestallon.vulpress.app.mail;

import hu.aestallon.vulpress.app.event.EmailSent;
import hu.aestallon.vulpress.app.event.RegistrationAccepted;
import org.springframework.context.event.EventListener;

public interface EmailService {

  @EventListener
  EmailSent sendVerificationMail(RegistrationAccepted registrationAccepted);

}
