package me.elhakimi.vroom.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@AllArgsConstructor
public class EmailSenderUtil {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendActivationEmail(String to, String subject, String name, String activationCode) {
        try {
            // Prepare the Thymeleaf context
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("activationCode", activationCode);

            // Process the HTML template
            String htmlContent = templateEngine.process("activation-email", context);

            // Create a MimeMessage
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true indicates HTML content

            // Send the email
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send activation email", e);
        }
    }
}