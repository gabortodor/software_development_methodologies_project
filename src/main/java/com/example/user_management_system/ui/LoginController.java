package com.example.user_management_system.ui;

import com.example.user_management_system.registration.RegistrationController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/home")
    public String viewLoginPage() {
        return "login";
        }

    @GetMapping("/profile_home")
    public String viewProfile() {return "profile_home";}

    @GetMapping("/reset**")
    public String viewReset() {return "reset";}

}


