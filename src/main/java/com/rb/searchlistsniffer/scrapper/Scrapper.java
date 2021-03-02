package com.rb.searchlistsniffer.scrapper;

import com.rb.searchlistsniffer.configuration.ScrapPageConf;
import com.rb.searchlistsniffer.reporting.ReportingFacade;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Scrapper {

    @Autowired
    private ReportingFacade reporter;

    private WebDriver browser;
    private WebDriverWait wait;

    private final static int TIME_OUT_SECONDS = 10;

    public Scrapper() {
        WebDriverManager.chromedriver().setup();
        browser = new ChromeDriver(getOptionsChrome());
        wait = new WebDriverWait(browser, TIME_OUT_SECONDS);
    }

    public boolean isProductFoundInSearchList(ScrapPageConf scrapConf) {
        reporter.reportDebug("Get page: " + scrapConf.getUrl());
        browser.get(scrapConf.getUrl());
        try {
            reporter.reportDebug("Trying to find element...");
            WebElement element = wait
                    .until(browser -> browser.findElement(By.cssSelector(scrapConf.getCssSelector())));

            reporter.reportDebug("Element found !");
            return true;
        }catch (TimeoutException e){
            reporter.reportDebug("Element not found.");
            return  false;
        }
    }

    private ChromeOptions getOptionsChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        return options;
    }

}
