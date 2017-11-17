package ru.korbit.ceadmin.mail;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.EmailDao;
import ru.korbit.cecommon.domain.Email;

import java.time.LocalDateTime;

@Component
@Slf4j
public class MailSenderHolder {

    private final MailSender sender;

    @Autowired
    public MailSenderHolder(MailSender sender) {
        this.sender = sender;
    }

    public boolean sendEmail(Email email) {
        email.setLastAttempt(LocalDateTime.now());
        email.increaseAttempts();

        val simpleEmail = new SimpleMailMessage();
        simpleEmail.setSubject(email.getSubject());
        simpleEmail.setTo(email.getRecipient());
        simpleEmail.setText(email.getBody());
        try {
            sender.send(simpleEmail);
            return true;
        } catch (Exception e) {
            log.error("Error sending email", e);
            return false;
        }
    }
}
