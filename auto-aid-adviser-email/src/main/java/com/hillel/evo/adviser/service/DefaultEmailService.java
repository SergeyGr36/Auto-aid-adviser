package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.parameters.MessageParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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
    public void sendMessage(MessageParameters dto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(dto.getToAddresses());
            helper.setCc(dto.getCcAddresses());
            helper.setBcc(dto.getBccAddresses());
            helper.setSubject(dto.getSubject());
            helper.setText(dto.getText(), dto.getHtml());
            helper.setSentDate(new Date());

            for (String pathToAttachment : dto.getAttachments()) {
                FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
                helper.addAttachment(file.getFilename(), file);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(message);
    }
}
