package com.rb.searchlistsniffer.reporting;

import com.rb.searchlistsniffer.configuration.ScrapConf;
import com.rb.searchlistsniffer.reporting.logs.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    @Autowired
    ScrapConf conf;
    @Autowired
    private JavaMailSender emailSender;

    public void send(String subject, String message, LogLevel logLevel) {
        String subjectOut = String.join(" ", formatMailSubject(logLevel), message);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(conf.getProgramName());
        mail.setSubject(subjectOut);
        mail.setText(message);
        mail.setTo(conf.getMailDest());
        emailSender.send(mail);
    }

    private String formatMailSubject(LogLevel logLevel) {
        String programNameOut = String.format("[%]", conf.getProgramName());
        String logLevelOut = LogLevel.Error.equals(logLevel)
                ? String.format("[%]", "ERROR")
                : String.format("[%]", "INFO");

        return new StringBuilder().append(programNameOut)
                .append("-")
                .append(logLevelOut)
                .append("-")
                .toString();
    }
}
