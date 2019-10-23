package com.hillel.evo.adviser.email.service;

import com.hillel.evo.adviser.email.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Service
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.DataflowAnomalyAnalysis"})
public class DefaultEmailService implements EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public DefaultEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMessage(MessageDto dto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(dto.getToAddresses());
            helper.setSubject(dto.getSubject());
            helper.setText(dto.getText());
            helper.setSentDate(new Date());

            for (String pathToAttachment : dto.getAttachments()) {
                FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
                helper.addAttachment("Invoice", file);
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(message);
    }
}
