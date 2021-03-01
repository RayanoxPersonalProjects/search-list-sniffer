package com.rb.searchlistsniffer.configuration;

import com.rb.searchlistsniffer.logs.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class ConfValidator {

    @Autowired
    Logger logger;

    public boolean validateConf(ScrapConf conf) {
        if(CollectionUtils.isEmpty(conf.getPagesConf())) {
            logger.error("The conf pages list is empty. Exiting");
            return false;
        }

        return conf.getPagesConf().stream()
                .noneMatch(this::isInvalideConf);
    }

    private boolean isInvalideConf(ScrapPageConf conf) {
        return Arrays.stream(ScrapPageConf.class.getDeclaredFields())
                .noneMatch(field ->  checkFieldNull(field, conf));
    }

    private boolean checkFieldNull(Field field, ScrapPageConf conf) {
        try {
            return field.get(conf) == null;
        }catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
