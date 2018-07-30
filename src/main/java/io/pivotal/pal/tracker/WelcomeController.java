package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {


    public WelcomeController( @Value("${WELCOME_MESSAGE}") String messsge){
        this.message=messsge;
    }

    private String message ;

    @GetMapping("/")
    public String sayHello() {
        return message;
    }
}
