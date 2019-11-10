package com.hillel.evo.adviser.service;

import com.hillel.evo.adviser.configuration.EmailConfigurationProperties;
import com.hillel.evo.adviser.enums.EmailContentType;
import com.hillel.evo.adviser.parameter.MessageParameters;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class DefaultEmailService implements EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEmailService.class);
    private final JavaMailSender javaMailSender;
    private final EmailConfigurationProperties emailProperties;
    private final TemplateService templateService;

    @Override
    public boolean sendMessage(MessageParameters params) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailProperties.getUsername());
            helper.setTo(params.getToAddresses());
            if (params.getCcAddresses() != null) {
                helper.setCc(params.getCcAddresses());
            }
            if (params.getBccAddresses() != null) {
                helper.setBcc(params.getBccAddresses());
            }
            if (params.getSubject() != null) {
                helper.setSubject(params.getSubject());
            }
            String text = templateService.convert(params, EmailContentType.TEXT);
            String html = templateService.convert(params, EmailContentType.HTML);
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
