package com.jinm.learning.hellobatch.jobflowone;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * .
 * Created by jinm on  2019/10/06.  contact: keanemer.gmail.com
 */

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.jinm.learning.hellobatch.jobflowone")
public class JobFlowOneApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(JobFlowOneApplication.class).run(args);
    }

}
