package com.jinm.learning.hellobatch.jobflowtwo;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * .
 * Created by jinm on  2019/10/06.  contact: keanemer.gmail.com
 */

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.jinm.learning.hellobatch.jobflowtwo")
public class JobFlowTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobFlowTwoApplication.class, args);
    }

}
