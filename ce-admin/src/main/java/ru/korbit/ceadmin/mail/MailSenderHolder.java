package ru.korbit.ceadmin.mail;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import ru.korbit.cecommon.dao.EmailDao;
import ru.korbit.cecommon.domain.Email;
import ru.korbit.cecommon.packet.TypeOfMail;

@Component
public class MailSenderHolder {

    private final EmailDao emailDao;

    private final MailSender sender;

    @Autowired
    public MailSenderHolder(EmailDao emailDao, MailSender sender) {
        this.emailDao = emailDao;
        this.sender = sender;
    }

    public void sendEmail(Email email, TypeOfMail type) {
        switch (type) {
            case REQUEST: sendRequestEmail(email); break;
            default: throw new RuntimeException("Unhandled email type");
        }
    }

    private void sendRequestEmail(Email email) {
        val simpleEmail = new SimpleMailMessage();
        simpleEmail.setTo(email.getRecipient());
        simpleEmail.setSubject("Request for admin rights");
        // TODO: change url to url page with list requests for admin rights
        simpleEmail.setText("Check page korbit.ru");
        sender.send(simpleEmail);
    }
}
