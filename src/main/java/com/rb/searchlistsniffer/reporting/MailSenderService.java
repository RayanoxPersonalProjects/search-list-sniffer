package com.rb.searchlistsniffer.reporting;

import com.rb.searchlistsniffer.configuration.ScrapConf;
import com.rb.searchlistsniffer.reporting.logs.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderService {

    @Autowired
    ScrapConf conf;
    @Autowired
    private JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String mailFrom;

    public void send(String subject, String message, LogLevel logLevel) {
        String subjectOut = String.join(" ", formatMailSubject(logLevel), message);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mailFrom);
        mail.setSubject(subjectOut);
        mail.setText(message);
        mail.setTo(conf.getMailDest());
        emailSender.send(mail);
    }

    private String formatMailSubject(LogLevel logLevel) {
        String programNameOut = String.format("[%s]", conf.getProgramName());
        String logLevelOut = LogLevel.Error.equals(logLevel)
                ? String.format("[%s]", "ERROR")
                : String.format("[%s]", "INFO");

        return new StringBuilder().append(programNameOut)
                .append("-")
                .append(logLevelOut)
                .append("-")
                .toString();
    }
}
