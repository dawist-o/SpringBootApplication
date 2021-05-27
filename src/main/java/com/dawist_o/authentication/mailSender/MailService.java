package com.dawist_o.authentication.mailSender;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Properties;

@Service
@AllArgsConstructor
@Slf4j
public class MailService implements MailSender {

    private final TemplateEngine templateEngine;

    /***
     * This method can send original emails
     * For this:
     * 1) change username and password for real one
     * 2) change host to smtp.gmail.com for example
     * 3) change port for 25/465/587/2525 if doesnt work
     * */
    @Override
    @Async
    public void send(String to, String link) {
        final String username = "8f90ea30dffd2f";
        final String password = "8af8a0b11b36cd";

        // Paste host address from the SMTP settings tab in your Mailtrap Inbox
        String host = "smtp.mailtrap.io";
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        //it’s optional in Mailtrap
        props.put("mail.smtp.starttls.enable", "true");
        // use one of the options in the SMTP settings tab in your Mailtrap Inbox
        props.put("mail.smtp.port", "2525");


        //put args into html message
        final Context context = new Context(Locale.ENGLISH);
        context.setVariable("link", link);
        String html = templateEngine.process("emails/email", context);

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            MimeMessage mimeMessage = new MimeMessage(session);
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            // Set From: header field
            String from = "vkoval2002@gmail.com";
            message.setFrom(new InternetAddress(from));
            message.setTo(to);
            message.setText(html, true);
            System.out.println("sending...");
            Transport.send(mimeMessage);
            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

