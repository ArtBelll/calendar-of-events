package ru.korbit.ceadmin.controllers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.korbit.ceadmin.dto.OrganisationDto;
import ru.korbit.ceadmin.dto.UserDto;
import ru.korbit.ceadmin.mail.MailFactory;
import ru.korbit.ceadmin.mail.MailSenderHolder;
import ru.korbit.cecommon.dao.EmailDao;
import ru.korbit.cecommon.dao.OrganisationDao;
import ru.korbit.cecommon.dao.UserDao;
import ru.korbit.cecommon.domain.Organisation;
import ru.korbit.cecommon.domain.User;
import ru.korbit.cecommon.exeptions.BadRequest;
import ru.korbit.cecommon.exeptions.UserNotExists;
import ru.korbit.cecommon.packet.RoleOfUser;
import ru.korbit.cecommon.packet.StatusOfOrganisation;
import ru.korbit.cecommon.utility.PasswordHelper;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Transactional
public class EmailAuthController extends SessionController {

    private final MailSenderHolder sender;
    private final EmailDao emailDao;
    private final OrganisationDao organisationDao;

    @Autowired
    public EmailAuthController(MailSenderHolder sender,
                               EmailDao emailDao,
                               OrganisationDao organisationDao,
                               UserDao userDao) {
        super(userDao);
        this.sender = sender;
        this.emailDao = emailDao;
        this.organisationDao = organisationDao;
    }

    @PostMapping(value = "auth/admin-request")
    public ResponseEntity<?> sendEmailRequest(@RequestBody Organisation organisation) throws MessagingException {
        if (organisationDao.getByEmail(organisation.getEmail()).isPresent()) {
            log.warn("Email {} is used", organisation.getEmail());
            throw new BadRequest("Email" + organisation.getEmail() + " is used");
        }

        organisation.setStatus(StatusOfOrganisation.REQUEST);
        organisationDao.save(organisation);

        val email = MailFactory.getRequestMail(organisation.getEmail());
        if (!sender.sendEmail(email)) {
            emailDao.save(email);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "superuser/auth/admin-requests")
    public ResponseEntity<?> getAdminsRequest() {
        val organisations = organisationDao.getByStatus(StatusOfOrganisation.REQUEST)
                .map(OrganisationDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(organisations, HttpStatus.OK);
    }

    @GetMapping(value = "superuser/auth/request-accepted/{organisationId}")
    public ResponseEntity<?> acceptAdmin(@PathVariable("organisationId") Long organisationId) {
        val organisation = organisationDao
                .searchByIdAndStatus(organisationId, StatusOfOrganisation.REQUEST)
                .orElseThrow(() -> new BadRequest("Organisation is not exist"));

        val password = PasswordHelper.generate();

        val user = new User(organisation.getEmail(), PasswordHelper.hashPassword(password), organisation);
        user.getRoles().add(RoleOfUser.ADMIN);
        userDao.save(user);
        organisation.setStatus(StatusOfOrganisation.APPROVED);

        val email = MailFactory.getAcceptedMail(organisation.getEmail(), password);
        if (!sender.sendEmail(email)) {
            emailDao.save(email);
        }

        log.info("Request accepted for organisation {}", organisation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "superuser/auth/request-rejected/{organisationId}")
    public ResponseEntity<?> rejectAdmin(@PathVariable("organisationId") Long organisationId,
                                         @RequestBody String cause) {
        val organisation = organisationDao
                .searchByIdAndStatus(organisationId, StatusOfOrganisation.REQUEST)
                .orElseThrow(() -> new BadRequest("Organisation is not exist"));

        organisation.setStatus(StatusOfOrganisation.REJECTED);

        val email = MailFactory.getRejectedMail(organisation.getEmail(), cause);
        if (sender.sendEmail(email)) {
            emailDao.save(email);
        }

        log.info("Request rejected for organisation {}", organisation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "auth/login")
    public ResponseEntity<?> login(HttpServletRequest request,
                                   @RequestBody User requestUser) throws URISyntaxException {
        if (StringUtils.isEmpty(requestUser.getEmail()) || StringUtils.isEmpty(requestUser.getPassword())) {
            throw new BadRequest("Missed one or more request fields");
        }

        val user = userDao.getByEmail(requestUser.getEmail())
                .orElseThrow(() -> new UserNotExists(requestUser.getEmail()));

        val password = requestUser.getPassword();
        if (!PasswordHelper.checkPassword(password, user.getPassword())) {
            log.warn("No password hash in db");
            throw new BadRequest("Invalid password");
        }

        val jwt = setSessionUser(user);
        user.setLogged(LocalDateTime.now());
        return createdResponse(new UserDto(user), jwt, isSslRequest(request));
    }
}
