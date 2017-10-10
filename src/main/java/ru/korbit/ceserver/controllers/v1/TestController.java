package ru.korbit.ceserver.controllers.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Artur Belogur on 10.10.17.
 */
@RestController
public class TestController {

    @GetMapping(value = "/test")
    public String getTesting() {
        return "Hello world";
    }
}
