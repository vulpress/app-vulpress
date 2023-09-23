package hu.aestallon.giannitsa.app.mail;

import hu.aestallon.giannitsa.app.auth.User;
import hu.aestallon.giannitsa.app.event.EmailSent;
import hu.aestallon.giannitsa.app.event.RegistrationAccepted;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.UUID;

@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  
  private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

  private final JavaMailSender javaMailSender;

  @Value("${vulpress.domain:http://localhost:8080}")
  private String domain;
  @Value("${vulpress.mail.from:noreply@aestallon.com}")
  private String from;
  @Value("${vulpress.mail.registration.subject:Vulpress - Registration Verification}")
  private String registrationSubject;

  @Override
  public EmailSent sendVerificationMail(RegistrationAccepted registrationAccepted) {
    final User user = registrationAccepted.user();
    log.debug("Sending e-mail to user: {}", user);

    final UUID uuid = user.registrationToken();
    final String text = "Vulpress: <a href=\"%s/reg/%s\">Activate account</a>"
        .formatted(domain, uuid);
    log.debug("Email body: [ {} ]", text);

    javaMailSender.send(msg -> {
      MimeMessageHelper helper = new MimeMessageHelper(msg, false, StandardCharsets.UTF_8.name());
      helper.setFrom(from);
      helper.setTo(user.email());
      helper.setText(text, true);
      helper.setSubject(registrationSubject);
    });

    return new EmailSent(user, Clock.systemDefaultZone());
  }

}
