package com.jinm.learning.spring.batch.learningspringbatch.step.sequential;

import org.springframework.batch.core.BatchStatus;
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
public class EpaySequentialStepJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Primary
    @Bean
    public Job epayJob(){
//        return jobBuilderFactory.get("epaySequentialStepJob")
//                .start(epayBillStep())
//                .next(epayClearingStep())
//                .next(epaySubClearingStep())
//                .build();
        return jobBuilderFactory.get("epaySequentialStepJob3")
                .start(epayBillStep()).on(BatchStatus.COMPLETED.toString()).to(epayClearingStep())
                .from(epayClearingStep()).on(BatchStatus.COMPLETED.toString()).to(epaySubClearingStep()).end()
                .build();
    }

    /**
     *  对账
     */
    @Bean
    public Step epayBillStep() {
        return stepBuilderFactory.get("epayBillStep1").tasklet((stepContribution, chunkContext) -> {
            System.out.println("step1 ---> epay bill step starting ......");
            return RepeatStatus.FINISHED;
        }).build();
    }

     /**
      *  一清
      */
     @Bean
    public Step epayClearingStep() {
        return stepBuilderFactory.get("epayClearingStep2").tasklet((stepContribution, chunkContext) -> {
            System.out.println("step2 ---> epay clearing step starting ......");
            return RepeatStatus.FINISHED;
        }).build();
    }

    /**
     *  二清
     */
    @Bean
    public Step epaySubClearingStep() {
        return stepBuilderFactory.get("epaySubClearingStep3").tasklet((stepContribution, chunkContext) -> {
            System.out.println("step3 ---> epay sub clearing step starting ......");
            return RepeatStatus.FINISHED;
        }).build();
    }


}
