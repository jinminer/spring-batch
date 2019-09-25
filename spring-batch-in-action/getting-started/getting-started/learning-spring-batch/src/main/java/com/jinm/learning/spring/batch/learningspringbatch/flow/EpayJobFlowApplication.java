package com.jinm.learning.spring.batch.learningspringbatch.flow;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * epay job flow application.
 * Created by jinm on  2019/09/25.  contact: keanemer.gmail.com
 */

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.jinm.learning.spring.batch.learningspringbatch.flow")
public class EpayJobFlowApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EpayJobFlowApplication.class).run(args);
    }

}
