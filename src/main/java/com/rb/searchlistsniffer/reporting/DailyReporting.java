package com.rb.searchlistsniffer.reporting;

import com.rb.searchlistsniffer.configuration.ScrapConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class DailyReporting {

    @Autowired
    ScrapConf conf;


    private LocalDate lastReportDate = LocalDate.now();
    private int loopCountsSinceLastReport = 0;

    public boolean shouldReport() {
        return !LocalDate.now().equals(lastReportDate);
    }

    public void resetReportData() {
        this.lastReportDate = LocalDate.now();
        this.loopCountsSinceLastReport = 0;
    }

    public int getLoopCountsSinceLastReport() {
        return loopCountsSinceLastReport;
    }

    public void addLoopCount() {
        this.loopCountsSinceLastReport++;
    }
}
