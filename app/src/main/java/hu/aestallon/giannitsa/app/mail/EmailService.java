package hu.aestallon.giannitsa.app.mail;

import hu.aestallon.giannitsa.app.event.EmailSent;
import hu.aestallon.giannitsa.app.event.RegistrationAccepted;
import org.springframework.context.event.EventListener;

public interface EmailService {

  @EventListener
  EmailSent sendVerificationMail(RegistrationAccepted registrationAccepted);

}
