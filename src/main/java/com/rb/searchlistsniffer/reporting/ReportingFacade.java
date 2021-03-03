package com.rb.searchlistsniffer.reporting;

import com.rb.searchlistsniffer.configuration.ScrapConf;
import com.rb.searchlistsniffer.configuration.ScrapPageConf;
import com.rb.searchlistsniffer.reporting.logs.LogLevel;
import com.rb.searchlistsniffer.reporting.logs.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportingFacade {

    @Autowired
    MailSenderService mail;
    @Autowired
    ScrapConf conf;
    @Autowired
    Logger logger;
    @Autowired
    WeeklyReporting weeklyReport;
    @Autowired
    DailyReporting dailyReport;

    public void reportStartingApp() {
        String title = "START";
        String message = "Starting of program";

        report(title, message, LogLevel.Info);
    }

    public void reportInfo(String message) {
        logger.log(message, LogLevel.Info);
    }

    public void reportConsole(String message) {
        logger.logConsole(message, LogLevel.Info);
    }

    public void reportDebug(String message) {
        if(!conf.isDebugMode())
            return;;

        logger.log(message, LogLevel.Info);
    }

    public void reportError(String message) {
        report("An Error occured", message, LogLevel.Error);
    }

    public void reportDaily() {
        dailyReport.addLoopCount();

        if(!dailyReport.shouldReport())
            return;

        reportInfo("Still working today ...");
        reportInfo("Loop count yesterday: " + dailyReport.getLoopCountsSinceLastReport());

        dailyReport.resetReportData();
    }

    public void reportWeekly() {
        weeklyReport.addLoopCount();

        if(!weeklyReport.shouldReport())
            return;

        String title = "Weekly notif";
        String message = new StringBuilder()
                .append("\t*** Periodic Notif ***\n\n")
                .append("Good news ! Program is still alive :)\n\n")
                .append("- Days since last notif: \n")
                .append(weeklyReport.getDaysSinceLastReport())
                .append("- Count loops: ")
                .append(weeklyReport.getLoopCountsSinceLastReport())
                .toString();

        report(title, message, LogLevel.Info);
        weeklyReport.resetReportData();
    }

    public void reportEndAppSuccess(ScrapPageConf pageConf) {
        String title = "HURRY UP !! PRODUCT AVAILABLE !";
        String message = new StringBuilder()
                .append("A product is now available on following URL -> ")
                .append(pageConf.getUrl())
                .append(". Hurry up and go buy a product right now before it gets out of stock !")
                .toString();

        report(title, message, LogLevel.Info);
    }

    /*
     *  Privates
     */

    private void report(String title, String message, LogLevel logLevel) {
        if(!conf.isDebugMode())
            mail.send(title, message, logLevel);

        logger.log(message, logLevel);
    }
}
