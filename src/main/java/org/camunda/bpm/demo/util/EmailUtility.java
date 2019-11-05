package org.camunda.bpm.demo.util;

import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

/**
 * @author Surabhi.Bhawsar
 *
 */
public class EmailUtility {

    private static final String API_KEY = "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";

    /**
     * @param toEmail
     * @throws IOException
     */
    public static void sendEmail(String toEmail) throws IOException {
        
        if (toEmail == null) {
            toEmail = "surabhi.bhawsar@pepcus.com";
        }
        Mail mail = prepareEmailRequest(toEmail);

        try {
            SendGrid sg = new SendGrid(API_KEY);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * @param toEmail
     * @return
     */
    private static Mail prepareEmailRequest(String toEmail) {

        String fromEmail = "noreply@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", body);

        return new Mail(from, subject, to, content);
    }

}