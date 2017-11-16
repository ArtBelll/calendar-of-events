package ru.korbit.ceadmin.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korbit.ceadmin.mail.MailSenderHolder;
import ru.korbit.cecommon.dao.EmailDao;
import ru.korbit.cecommon.domain.Email;
import ru.korbit.cecommon.domain.Organisation;
import ru.korbit.cecommon.packet.TypeOfMail;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "auth")
public class EmailAuthController {

    private final MailSenderHolder sender;
    private final EmailDao emailDao;

    @Autowired
    public EmailAuthController(MailSenderHolder sender, EmailDao emailDao) {
        this.sender = sender;
        this.emailDao = emailDao;
    }

    @PostMapping(value = "request")
    public ResponseEntity<?> sendEmailRequest(@RequestBody Organisation organisation) throws MessagingException {
        val email = new Email();
        email.setRecipient(organisation.getEmail());
        email.setType(TypeOfMail.REQUEST);
        if(!sender.sendEmail(email)) {
            emailDao.save(email);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
