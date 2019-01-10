package com.restservice.restservicejavateam.Controller;

import com.restservice.restservicejavateam.Email.EmailService;
import com.restservice.restservicejavateam.domain.Mail.Mail;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")

@RequestMapping("/EmailController")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }


    @ApiOperation(value = "SendMailFactory To and subject and text ",
            notes = "Send mail without return type")
    @PostMapping("/sendmail/{to:.+}/{subject}/{text}")
    public void SendMailFactory(@PathVariable("to") String to, @PathVariable("subject") String subject, @PathVariable("text") String text) {


        try {
            emailService.sendSimpleMessage(new Mail("iniyan455@gmail.com", to, subject, text));


        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
