package me.elhakimi.vroom.utils;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class EmailSenderUtil {

    private final JavaMailSender javaMailSender;

    public  void sendActivationEmail(String to , String subject, String text)
        {
            {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(to);
                mailMessage.setSubject(subject);
                mailMessage.setText(text);
                javaMailSender.send(mailMessage);
            }
        }
}
