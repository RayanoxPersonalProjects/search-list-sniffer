package com.rb.searchlistsniffer.reporting.logs;

import com.rb.searchlistsniffer.configuration.ScrapConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class Logger {

    private static final String LOG_FILE_NAME = "Logs.txt";

    @Autowired
    private ScrapConf conf;
    private FileWriter fileWriter;

    public Logger() throws IOException {
        fileWriter = new FileWriter(LOG_FILE_NAME, true);
    }

    public void log(String message, LogLevel logLevel) {
        String formattedMessage = getHeader(logLevel).concat(message);

        if(conf.isDebugMode())
            System.out.println(formattedMessage);
        else {
            try {
                fileWriter.write(formattedMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getHeader(LogLevel logLevel) {
        String dateTimeOut = String.format("[%]", LocalDateTime.now().toString());
        String logLevelOut = LogLevel.Error.equals(logLevel)
                ? String.format("[%]", "ERROR")
                : String.format("[%]", "INFO");

        return new StringBuilder()
                .append(dateTimeOut)
                .append("-")
                .append(logLevelOut)
                .append("- ")
                .toString();
    }
}
