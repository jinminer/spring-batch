package com.jinm.learning.spring.batch.learningspringbatch.parameters;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.jinm.learning.spring.batch.learningspringbatch.parameters")
public class EpayJobParametersApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(EpayJobParametersApplication.class).run();

    }

}
