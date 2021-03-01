package com.rb.searchlistsniffer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("conf")
@Getter @Setter
public class ScrapConf {

    @Autowired
    ConfValidator confValidator;

    private List<ScrapPageConf> pagesConf;
    private boolean debugMode = false;
    private int waitTimeLoopMinutes;

    public boolean validateConf() {
        return confValidator.validateConf(this);
    }
}
