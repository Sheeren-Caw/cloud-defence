package com.cawstudios.clouddefence;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller
public class TestController {
    @Get("/message")
    public String displayMessage() {
        return "Successfully Deployed to Kubernetes !!";
    }
}
