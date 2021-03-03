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

        System.out.println(formattedMessage);

        if(!conf.isDebugMode()) {
            try {
                fileWriter.write(formattedMessage);
                fileWriter.write("\r\n");
                fileWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void logConsole(String message, LogLevel logLevel) {
        System.out.println(getHeader(logLevel).concat(message));
    }

    private String getHeader(LogLevel logLevel) {
        String dateTimeOut = String.format("[%s]", LocalDateTime.now().toString());
        String logLevelOut = LogLevel.Error.equals(logLevel)
                ? String.format("[%s]", "ERROR")
                : String.format("[%s]", "INFO");

        return new StringBuilder()
                .append(dateTimeOut)
                .append("-")
                .append(logLevelOut)
                .append("- ")
                .toString();
    }
}
