package com.jinm.learning.spring.batch.learningspringbatch.split;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * epay multithreading job spilt configuration .
 * Created by jinm on  2019/09/25.  contact: keanemer.gmail.com
 */
@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.jinm.learning.spring.batch.learningspringbatch.split")
public class EpayMutithreadingJobSpiltApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(EpayMutithreadingJobSpiltApplication.class).run(args);

    }

}
