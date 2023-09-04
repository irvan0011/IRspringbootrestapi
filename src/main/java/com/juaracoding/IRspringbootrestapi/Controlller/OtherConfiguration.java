package com.juaracoding.IRspringbootrestapi.Controlller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:other.properties")
public class OtherConfiguration {
    public static String getFlagLogging() {
        return flagLogging;
    }

    @Value("${flag.logging}")
    private void setFlagLogging(String flagLogging) { //private pada setter dapat digunakan untuk memberikan
        OtherConfiguration.flagLogging = flagLogging;
    }

    private  static  String flagLogging;
}
