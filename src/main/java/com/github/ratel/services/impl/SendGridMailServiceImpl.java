package com.github.ratel.services.impl;

import com.github.ratel.services.SendGridMailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendGridMailServiceImpl implements SendGridMailService {

    @Value("${spring.sendgrid.api-key}")
    private String API_KEY;

    private static final String ADDRESS_FROM_SEND = "forgotandry@ukr.net";

    @Override
    public void sendMessage(String toAddress, String subject, String text) {
        Email emailTo = new Email(toAddress);
        Email emailFrom = new Email(ADDRESS_FROM_SEND);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(emailFrom, subject, emailTo,content);

        SendGrid sg = new SendGrid(API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("------------------------------------------");
            System.out.println("StatusCode for send email - " + response.getStatusCode());
            System.out.println("------------------------------------------");
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

}
