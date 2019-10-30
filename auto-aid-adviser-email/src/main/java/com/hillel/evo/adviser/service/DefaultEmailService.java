package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.EmailConfigurationProperties;
import com.hillel.evo.adviser.parameters.MessageParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Service
@ConditionalOnProperty(prefix = "email", name = "service", havingValue = "default", matchIfMissing = true)
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.DataflowAnomalyAnalysis"})
public class DefaultEmailService implements EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEmailService.class);
    private final JavaMailSender javaMailSender;
    private final EmailConfigurationProperties emailProperties;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DefaultEmailService(JavaMailSender javaMailSender, EmailConfigurationProperties emailProperties) {
        this.javaMailSender = javaMailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public boolean sendMessage(MessageParameters dto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailProperties.getUsername());
            helper.setTo(dto.getToAddresses());
            helper.setCc(dto.getCcAddresses());
            helper.setBcc(dto.getBccAddresses());
            helper.setSubject(dto.getSubject());
            helper.setText(dto.getText(), dto.getHtml());
            helper.setSentDate(new Date());
            javaMailSender.send(message);
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
    }
}
