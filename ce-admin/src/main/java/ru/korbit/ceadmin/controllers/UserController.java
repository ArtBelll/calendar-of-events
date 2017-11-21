package ru.korbit.ceadmin.controllers;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korbit.ceadmin.dto.RUser;
import ru.korbit.cecommon.dao.UserDao;
import ru.korbit.cecommon.packet.RoleOfUser;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
        return new ResponseEntity<>(new RUser(user), HttpStatus.OK);
    }

    @GetMapping(value = "permission/{role}")
    public ResponseEntity<?> isAccess(HttpServletRequest request, @PathVariable("role") RoleOfUser role) {
        val user = getSessionUser(request);

        val response = new HashMap<String, Boolean>();
        if (user.getRoles().contains(role)) {
            response.put("access", true);
        }
        else {
            response.put("access", false);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
