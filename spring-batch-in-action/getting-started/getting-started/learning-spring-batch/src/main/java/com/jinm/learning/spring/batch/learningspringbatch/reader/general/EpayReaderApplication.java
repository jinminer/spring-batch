package com.jinm.learning.spring.batch.learningspringbatch.reader.general;

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
@ComponentScan(basePackages = "com.jinm.learning.spring.batch.learningspringbatch.reader.general")
public class EpayReaderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EpayReaderApplication.class).run();
    }

}
