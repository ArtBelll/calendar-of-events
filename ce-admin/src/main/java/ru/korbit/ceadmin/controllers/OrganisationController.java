package ru.korbit.ceadmin.controllers;

import lombok.val;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korbit.ceadmin.dto.ROrganisation;
import ru.korbit.cecommon.dao.UserDao;

import javax.servlet.http.HttpServletRequest;

@RestController
@Transactional
public class OrganisationController extends SessionController {

    @Autowired
    protected OrganisationController(UserDao userDao) {
        super(userDao);
    }

    @GetMapping(value = "admin/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        val user = getSessionUser(request);
        val organisation = new ROrganisation(user.getOrganisation());
        return new ResponseEntity<>(organisation, HttpStatus.OK);
    }
}
