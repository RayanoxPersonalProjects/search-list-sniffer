package com.rb.searchlistsniffer;

import com.rb.searchlistsniffer.configuration.ScrapConf;
import com.rb.searchlistsniffer.configuration.ScrapPageConf;
import com.rb.searchlistsniffer.logs.Logger;
import com.rb.searchlistsniffer.mail.MailSender;
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
    Logger logger;
    @Autowired
    MailSender mail;

    @Autowired
    ScrapConf conf;

    public void process() {
        if(!conf.validateConf())
            return;

        try {
            processSniff();
        } catch (Exception e) {
            String message = String.format("An exception occured in the sniffing process. Ending program. Stacktrace = %s", ExceptionUtils.getStackTrace(e));
            logger.error(message);
            mail.error(message);
        }
    }

    private void processSniff() throws Exception {
        while(true) {
            Optional<ScrapPageConf> siteProductFound = conf.getPagesConf().stream()
                    .filter(scrapper::isProductFoundInSearchList)
                    .findAny();

            if(siteProductFound.isPresent()) {
                notificateEnding(siteProductFound.get());
                logger.info("End of program (success)");
                return;
            }

            Thread.sleep(conf.getWaitTimeLoopMinutes()*60*1000);
        }
    }

    private void notificateEnding(ScrapPageConf scrapPageConf) {
        String message = String.format("The product has been found on URL: %s", scrapPageConf.getUrl());
        logger.info(message);
        mail.info(message);
    }


}
