package com.jinm.learning.spring.batch.learningspringbatch.base;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * spring batch job configuration.
 * Created by jinm on  2019/09/25.  contact: keanemer.gmail.com
 */

@Configuration
@EnableBatchProcessing
public class EpayJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Primary
    @Bean
    public Job epayJob(){
        return jobBuilderFactory.get("epayJob").start(epayBillStep()).next(epayClearingStep()).build();
    }

     /**
      *  清算
      */
     @Bean
    public Step epayClearingStep() {
        return stepBuilderFactory.get("epayClearingStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("epay clearing step starting ......");
            return RepeatStatus.FINISHED;
        }).build();
    }

    /**
     *  对账
     */
    @Bean
    public Step epayBillStep() {
        return stepBuilderFactory.get("epayBillStep").tasklet((stepContribution, chunkContext) -> {
            System.out.println("epay bill step starting ......");
            return RepeatStatus.FINISHED;
        }).build();
    }


}
