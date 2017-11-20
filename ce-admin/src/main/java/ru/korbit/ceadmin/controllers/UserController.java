package ru.korbit.ceadmin.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korbit.ceadmin.dto.ResponseUser;
import ru.korbit.cecommon.dao.UserDao;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "users")
public class UserController extends SessionController {

    @Autowired
    UserController(UserDao userDao) {
        super(userDao);
    }

    @GetMapping(value = "me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        val user = getSessionUser(request);
        return new ResponseEntity<>(new ResponseUser(user), HttpStatus.OK);
    }
}
