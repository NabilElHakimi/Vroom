package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Validation;
import me.elhakimi.vroom.repository.ValidationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@AllArgsConstructor
@Service
public class ValidationServiceImpl {

    private final ValidationRepository validationRepository;
    private final JavaMailSender JavaMailSender;

    public void save(AppUser user) {
        Validation validation = new Validation();
        validation.setUser(user);
        Instant creation = Instant.now();
        validation.setCreatedAt(creation);
        Instant expiration = creation.plusSeconds(60 * 10);
        validation.setExpiresAt(expiration);
        Random random = new Random();
        int  randomInt = random.nextInt(999999);
        String code = String.format("%06d", randomInt);
        validation.setCode(code);
        this.validationRepository.save(validation);
        sendActivationEmail(validation);
    }

    public void  sendActivationEmail(Validation validation) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(validation.getUser().getEmail());
        mailMessage.setSubject("Vroom Account Activation");
        mailMessage.setText("Your activation code is : " + validation.getCode());
        JavaMailSender.send(mailMessage);
    }

    public Validation findByCode(String code) {
        return this.validationRepository.findByCodeAndActivationAtNull(code);
    }

    public void expired(Validation validation) {
        validation.setActivationAt(Instant.now());
        this.validationRepository.save(validation);
    }
}
