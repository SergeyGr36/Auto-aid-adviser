package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.EmailConfigurationProperties;
import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
@ConditionalOnProperty(prefix = "email", name = "service", havingValue = "default", matchIfMissing = true)
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class DefaultEmailService implements EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEmailService.class);
    private final JavaMailSender javaMailSender;
    private final EmailConfigurationProperties emailProperties;
    private final TemplateService templateService;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DefaultEmailService(JavaMailSender javaMailSender,
                               EmailConfigurationProperties emailProperties,
                               TemplateService templateService) {
        this.javaMailSender = javaMailSender;
        this.emailProperties = emailProperties;
        this.templateService = templateService;
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
            String text = templateService.convert(dto, EmailContentType.TEXT);
            String html = templateService.convert(dto, EmailContentType.HTML);
            helper.setText(text, html);
            helper.setSentDate(new Date());
            javaMailSender.send(message);
            return true;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
    }
}
