package com.gaborkallos.thefeedbacker.service;

import com.gaborkallos.thefeedbacker.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private String MESSAGE_FROM = "petprojectx@gmail.com";

    @Qualifier("javaMailService")
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRegistrationMessage(Admin admin, String password) {
        SimpleMailMessage message;

        try {
            message = new SimpleMailMessage();
            message.setFrom(MESSAGE_FROM);

            message.setTo(admin.getEmail());

            message.setSubject("Password for the Project X");

            message.setText("Dear " + admin.getUsername() + "! \n \n" +
                    "Welcome in our project X website. \n" +
                    "Your username is: " + admin.getUsername() + "\n"+
                    "and your password: " + password + " . \n" +
                    "To login visit http://localhost:4200/login \n \n" +
                    "Regards, \n" +
                    "System Administrators \n" +
                    "The PROJECT X LTD");

            javaMailSender.send(message);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
