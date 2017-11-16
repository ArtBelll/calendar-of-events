package ru.korbit.ceadmin.mail;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.EmailDao;
import ru.korbit.cecommon.domain.Email;

@Component
@Slf4j
public class MailSenderHolder {

    private final MailSender sender;

    @Autowired
    public MailSenderHolder(EmailDao emailDao, MailSender sender) {
        this.sender = sender;
    }

    public boolean sendEmail(Email email) {
        switch (email.getType()) {
            case REQUEST: return sendRequestEmail(email);
            default: throw new RuntimeException("Unhandled email type");
        }
    }

    private boolean sendRequestEmail(Email email) {
        val simpleEmail = new SimpleMailMessage();
        simpleEmail.setTo(email.getRecipient());
        simpleEmail.setSubject("Request for admin rights");
        // TODO: change url to url page with list requests for admin rights
        simpleEmail.setText("Check page korbit.ru");
        try {
            sender.send(simpleEmail);
            return true;
        } catch (Exception e) {
            log.error("Error sending email", e);
            return false;
        }
    }
}
