package com.rb.searchlistsniffer;

import com.rb.searchlistsniffer.configuration.ScrapConf;
import com.rb.searchlistsniffer.configuration.ScrapPageConf;
import com.rb.searchlistsniffer.reporting.ReportingFacade;
import com.rb.searchlistsniffer.scrapper.Scrapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Core{

    @Autowired
    private Scrapper scrapper;
    @Autowired
    ReportingFacade reporting;

    @Autowired
    ScrapConf conf;

    public void process() {
        reporting.reportStartingApp();

        if(!conf.validateConf())
            return;

        try {
            processSniff();
        } catch (Exception e) {
            String message = String.format("An exception occured in the sniffing process. Stacktrace = %s", ExceptionUtils.getStackTrace(e));
            reporting.reportError(message);
        }

        reporting.reportInfo("End of program");
    }

    private void processSniff() throws Exception {
        while(true) {
            Optional<ScrapPageConf> siteProductFound = conf.getPagesConf().stream()
                    .filter(scrapper::isProductFoundInSearchList)
                    .findAny();

            if(siteProductFound.isPresent()) {
                reporting.reportEndAppSuccess(siteProductFound.get());
                return;
            }

            reporting.reportDaily();
            reporting.reportWeekly();

            reporting.reportConsole(String.format("End of loop. Waiting %d minutes", conf.getWaitTimeLoopMinutes()));
            Thread.sleep(conf.getWaitTimeLoopMinutes()*60*1000);
        }
    }

}
