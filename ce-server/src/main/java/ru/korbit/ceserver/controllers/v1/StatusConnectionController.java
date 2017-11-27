package ru.korbit.ceserver.controllers.v1;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@RestController
public class StatusConnectionController {

    @GetMapping(value = "status")
    public ResponseEntity<?> getStatus() {
        val body = new HashMap<String, String>();
        body.put("message", "OK");
        return new ResponseEntity<HashMap>(body, HttpStatus.OK);
    }
}
