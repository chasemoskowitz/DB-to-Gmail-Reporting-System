package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserData; 
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;

import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    // Helper Method to build the HTML Table from Postgres Data
    public String buildHtmlTable(List<UserData> users) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h2 style='color: #2c3e50; font-family: sans-serif;'>PostgreSQL 18 Database Report</h2>");
        sb.append("<table style='width:100%; border-collapse: collapse; font-family: sans-serif; font-size: 14px;'>");
        sb.append("<tr style='background-color: #f8f9fa;'>")
          .append("<th style='border: 1px solid #ddd; padding: 12px; text-align: left;'>Name</th>")
          .append("<th style='border: 1px solid #ddd; padding: 12px; text-align: left;'>Email</th>")
          .append("<th style='border: 1px solid #ddd; padding: 12px; text-align: left;'>Notes</th>")
          .append("</tr>");

        for (UserData user : users) {
            sb.append("<tr>")
              .append("<td style='border: 1px solid #ddd; padding: 10px;'>").append(user.getName()).append("</td>")
              .append("<td style='border: 1px solid #ddd; padding: 10px;'>").append(user.getEmail()).append("</td>")
              .append("<td style='border: 1px solid #ddd; padding: 10px;'>").append(user.getDescription()).append("</td>")
              .append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    public void sendEmailViaRest(OAuth2AuthorizedClient client, String to, String subject, String bodyText) throws Exception {
        String tokenValue = client.getAccessToken().getTokenValue();
        AccessToken accessToken = new AccessToken(tokenValue, null);
        GoogleCredentials credentials = GoogleCredentials.create(accessToken);

        Gmail service = new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("DBtoGmailWebsite")
                .build();

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        
        email.setFrom(new InternetAddress("me"));
        email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        
        // CRITICAL CHANGE: Use setContent for HTML instead of setText
        email.setContent(bodyText, "text/html; charset=utf-8");

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        
        // Use URL-safe Base64 encoding as required by Gmail API
        String encodedEmail = Base64.getUrlEncoder().encodeToString(rawMessageBytes);

        Message message = new Message();
        message.setRaw(encodedEmail);

        service.users().messages().send("me", message).execute();
    }
}