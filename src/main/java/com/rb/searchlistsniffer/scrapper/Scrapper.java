package com.rb.searchlistsniffer.scrapper;

import com.rb.searchlistsniffer.configuration.ScrapPageConf;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component
public class Scrapper {

    private WebDriver browser;
    private WebDriverWait wait;

    private final static int TIME_OUT_SECONDS = 10;

    public Scrapper() {
        WebDriverManager.chromedriver().setup();
        browser = new ChromeDriver(getOptionsChrome());
        wait = new WebDriverWait(browser, TIME_OUT_SECONDS);
    }

    public boolean isProductFoundInSearchList(ScrapPageConf scrapConf) {
        browser.get(scrapConf.getUrl());
        try {
            WebElement element = wait
                    .until(browser -> browser.findElement(By.cssSelector(scrapConf.getCssSelector())));
            return true;
        }catch (TimeoutException e){
            return  false;
        }
    }

    private ChromeOptions getOptionsChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        return options;
    }

}
