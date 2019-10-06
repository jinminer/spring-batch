package com.jinm.learning.hellobatch.jobflowone;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * .
 * Created by jinm on  2019/10/06.  contact: keanemer.gmail.com
 */

@Configuration
public class JobFlowOneConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job getJob(){
        return jobBuilderFactory.get("jobFlowOne00")
//                .start(step1())
//                .next(step2())
//                .next(step3())
//                .build();
                .start(step1())
                .on(RepeatStatus.CONTINUABLE.toString()).to(step2())
                .from(step2()).on(RepeatStatus.CONTINUABLE.toString()).to(step3())
                .end()
                .build();

    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("jobFlowOneStep100").tasklet((contribution, chunkContext) -> {
            System.out.println("jobFlowOneStep100");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step step2(){
        return stepBuilderFactory.get("jobFlowOneStep200").tasklet((contribution, chunkContext) -> {
            System.out.println("jobFlowOneStep200");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step step3(){
        return stepBuilderFactory.get("jobFlowOneStep300").tasklet((contribution, chunkContext) -> {
            System.out.println("jobFlowOneStep300");
            return RepeatStatus.FINISHED;
        }).build();
    }

}
