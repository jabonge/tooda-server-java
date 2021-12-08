package com.ddd.tooda;

import com.ddd.tooda.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ToodaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToodaApplication.class, args);
    }

}
