package com.jinm.learning.spring.batch.learningspringbatch.flow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * epay job flow configuration.
 * Created by jinm on  2019/09/25.  contact: keanemer.gmail.com
 */

@Configuration
public class EpayJobFlowConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     *  批量短信作业步
     */
    @Bean
    public Step messageStep() {

        return stepBuilderFactory.get("messageStep").tasklet((contribution, chunkContext) -> {
            System.out.println("step3 ---> messageStep");
            return RepeatStatus.FINISHED;
        }).build();

    }

    /**
     *  清算作业步：一清
     */
    @Bean
    public Step clearingStep() {

        return stepBuilderFactory.get("clearingStep").tasklet((contribution, chunkContext) -> {
            System.out.println("flow1 ---> step1 ---> clearingStep");
            return RepeatStatus.FINISHED;
        }).build();

    }

    /**
     *  清算作业步：二清
     */
    @Bean
    public Step subClearingStep() {

        return stepBuilderFactory.get("subClearingStep").tasklet((contribution, chunkContext) -> {
            System.out.println("flow1 ---> step2 ---> subClearingStep");
            return RepeatStatus.FINISHED;
        }).build();

    }

    /**
     *  清算作业步流：一清、二清
     */
    @Bean
    public Flow clearingFlow() {

        return new FlowBuilder<Flow>("clearingFlow")
                .start(clearingStep())
                .next(subClearingStep())
                .build();

    }

    @Bean
    public Job epayJob(){

        return jobBuilderFactory.get("epayFlowJob1")
                .start(clearingFlow())
                .next(messageStep())
                .end()
                .build();

    }

}
