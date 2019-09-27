package com.jinm.learning.spring.batch.learningspringbatch.nested;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * epay job application.
 * Created by jinm on  2019/09/25.  contact: keanemer.gmail.com
 */

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.jinm.learning.spring.batch.learningspringbatch.nested")
public class EpayNestedJobApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EpayNestedJobApplication.class).run();
    }

}
