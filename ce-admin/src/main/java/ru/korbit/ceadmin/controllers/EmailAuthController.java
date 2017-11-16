package ru.korbit.ceadmin.controllers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.korbit.ceadmin.mail.MailSenderHolder;
import ru.korbit.cecommon.dao.EmailDao;
import ru.korbit.cecommon.dao.OrganisationDao;
import ru.korbit.cecommon.domain.Email;
import ru.korbit.cecommon.domain.Organisation;
import ru.korbit.cecommon.exeptions.BadRequest;
import ru.korbit.cecommon.packet.StatusOfOrganisation;
import ru.korbit.cecommon.packet.TypeOfMail;

import javax.mail.MessagingException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "auth")
@Slf4j
@Transactional
public class EmailAuthController {

    private final MailSenderHolder sender;
    private final EmailDao emailDao;
    private final OrganisationDao organisationDao;

    @Autowired
    public EmailAuthController(MailSenderHolder sender, EmailDao emailDao, OrganisationDao organisationDao) {
        this.sender = sender;
        this.emailDao = emailDao;
        this.organisationDao = organisationDao;
    }

    @PostMapping(value = "admin-request")
    public ResponseEntity<?> sendEmailRequest(@RequestBody Organisation organisation) throws MessagingException {
        if (organisationDao.getByName(organisation.getName()).isPresent()) {
            log.warn("Organisation {} is already exist", organisation.getName());
            throw new BadRequest("Organisation " + organisation.getName() + " is already exist");
        }

        val email = new Email();
        email.setRecipient(organisation.getEmail());
        email.setType(TypeOfMail.REQUEST);
        if(!sender.sendEmail(email)) {
            emailDao.save(email);
        }

        organisation.setStatus(StatusOfOrganisation.REQUEST);
        organisationDao.save(organisation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "admin-requests")
    public ResponseEntity<?> getAdminsRequest() {
        val organisations = organisationDao.getByStatus(StatusOfOrganisation.REQUEST).collect(Collectors.toList());
        return new ResponseEntity<>(organisations, HttpStatus.OK);
    }
}
