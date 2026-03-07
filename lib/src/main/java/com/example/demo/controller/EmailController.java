package com.example.demo.controller;

import com.example.demo.model.UserData;
import com.example.demo.repository.UserDataRepository;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller 
public class EmailController {

    @Autowired
    private UserDataRepository repository;

    @Autowired
    private EmailService emailService;

   
    @GetMapping("/view")
    @ResponseBody 
    public List<UserData> getAllData() {
        return repository.findAll();
    }

   
    @PostMapping("/add")
    @ResponseBody
    public void addUser(@RequestBody UserData user) {
        // Spring Boot automatically maps the JSON body to your UserData object
        repository.save(user);
    }

    @GetMapping("/send")
    public String sendReport(
            @RequestParam("email") String email, // Added explicit name for Java 21 safety
            @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient client) {
        try {
            var data = repository.findAll();
            String htmlTable = emailService.buildHtmlTable(data);
            emailService.sendEmailViaRest(client, email, "Database Report", htmlTable);
            
            return "redirect:/?sent=true";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/?error=true";
        }
    }

    @GetMapping("/delete-all")
    @ResponseBody
    public String deleteAll() {
        repository.truncateTable(); 
        return "Database Cleared & IDs Reset to 1";
    }
}