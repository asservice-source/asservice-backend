package com.fwg.asservice.service.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
 
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.fwg.asservice.model.Mail;
import com.fwg.asservice.service.MailService;
 
@Service("mailService")
public class MailServiceImpl implements MailService {
 
    @Autowired
    JavaMailSender mailSender;
 
    @Autowired
    VelocityEngine velocityEngine;
 
    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
 
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String emailTemplate = "/email-templates/email-template.vm";
            
            String emailFrom = "info@asservice.com";
            
            if(mail.getMailFrom() != null && !mail.getMailFrom().equals("")){
            	emailFrom = mail.getMailFrom();
            }
            
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(emailFrom);
            mimeMessageHelper.setTo(mail.getMailTo());
            mail.setMailContent(geContentFromTemplate(emailTemplate, mail.getModel()));
            //mail.setMailContent(mail.getMailContent());
            mimeMessageHelper.setText(mail.getMailContent(), true);
 
            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
 
    public String geContentFromTemplate(String emailTemplate, Map <String, Object> model) {
        StringBuffer content = new StringBuffer();
        try {
            content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, emailTemplate, model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
 
}
