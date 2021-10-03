package com.example.user_management_system.registration;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;




@RestController
@RequestMapping
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    private static String currentException;

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex){
        ModelAndView modelAndView=new ModelAndView("redirect:/home");
        currentException=ex.getMessage();
        return modelAndView;
    }


    public static String getCurrentException(){
        return currentException;
    }

    public static void setCurrentExceptionToNull(){
        currentException=null;
    }

    @PostMapping(path = "/home", params = "signUp")
    public ModelAndView postRegistration(@ModelAttribute Request request) {
        if (!isMatchingPassword(request.getPassword(), request.getPassword_confirm())) {
            throw new IllegalStateException("Passwords not matching");
        }
        if (registrationService.registration(request))
            return new ModelAndView("redirect:/home" );
        throw new IllegalStateException("Unsuccessful registration.");
    }

    @GetMapping(path = "/confirm")
    public ModelAndView getConfirm(@RequestParam(name = "token") String token) {
        if (registrationService.enableAccount(token))
            return new ModelAndView("redirect:/home" );
        throw new IllegalStateException("Cannot confirm account.");
    }

    @PostMapping(path = "/home", params = "resetPassword")
    public ModelAndView postResetEmail( @RequestParam String email) {
        if(registrationService.sendPasswordResetEmail(email)){
            return new ModelAndView("redirect:/home" );
        }
        else {
            throw new IllegalStateException("Cannot send email.");
        }
    }

    @PostMapping(path = "/reset**")
    public ModelAndView postResetPassword(@RequestParam(name = "token") String token,@RequestParam (name = "password") String password,
                                    @RequestParam (name = "password_confirm") String password_confirm) {
        if(!isMatchingPassword(password,password_confirm)){
            throw new IllegalStateException("Passwords not matching");
        }
        if(registrationService.changePassword(password, token))
            return new ModelAndView("redirect:/home" );

        throw new IllegalStateException("Cannot change password.");

    }

    public boolean isMatchingPassword(String password, String password_confirm) {
        return password.equals(password_confirm);
    }


}
