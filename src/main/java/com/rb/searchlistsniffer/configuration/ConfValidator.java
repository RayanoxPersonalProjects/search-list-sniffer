package com.rb.searchlistsniffer.configuration;

import com.rb.searchlistsniffer.reporting.ReportingFacade;
import com.rb.searchlistsniffer.reporting.logs.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

@Component
public class ConfValidator {

    @Autowired
    ReportingFacade reporter;

    public boolean validateConf(ScrapConf conf) {
        if(CollectionUtils.isEmpty(conf.getPagesConf())) {
            reporter.reportError("The conf pages list is empty. Exiting");
            return false;
        }

        return conf.getPagesConf().stream()
                .noneMatch(this::isInvalideConf);
    }

    private boolean isInvalideConf(ScrapPageConf conf) {
        return Arrays.stream(ScrapPageConf.class.getDeclaredMethods())
                .filter(method -> method.getName().toLowerCase(Locale.ROOT).contains("get"))
                .anyMatch(method ->  checkFieldNull(method, conf));
    }

    private boolean checkFieldNull(Method method, ScrapPageConf conf) {
        try {
            return method.invoke(conf) == null;
        }catch (IllegalAccessException | InvocationTargetException e) {
            reporter.reportError(ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(e);
        }
    }

}
