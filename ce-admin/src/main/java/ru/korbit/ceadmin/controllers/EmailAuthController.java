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
import ru.korbit.cecommon.domain.Email;
import ru.korbit.cecommon.domain.Organisation;
import ru.korbit.cecommon.packet.TypeOfMail;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "auth")
public class EmailAuthController {

    private final MailSenderHolder sender;

    @Autowired
    public EmailAuthController(MailSenderHolder sender) {
        this.sender = sender;
    }

    @PostMapping(value = "request")
    public ResponseEntity<?> sendEmailRequest(@RequestBody Organisation organisation) throws MessagingException {
        val email = new Email();
        email.setRecipient(organisation.getEmail());
        sender.sendEmail(email, TypeOfMail.REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
