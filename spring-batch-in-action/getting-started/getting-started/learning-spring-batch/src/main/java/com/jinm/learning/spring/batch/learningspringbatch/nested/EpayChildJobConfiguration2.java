package com.jinm.learning.spring.batch.learningspringbatch.nested;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * .
 * Created by jinm on  2019/09/25.  contact: keanemer.gmail.com
 */

@Configuration
public class EpayChildJobConfiguration2 {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job childJob2() {
        return jobBuilderFactory.get("childJob2")
                .start(childJob2Step1())
                .next(childJob2Step2())
                .build();

    }

    @Bean
    public Step childJob2Step1() {
        return stepBuilderFactory.get("childJob2Step1").tasklet((contribution, chunkContext) -> {
            System.out.println("Hello---->childJob2Step1");
            return RepeatStatus.FINISHED;
        }).build();

    }

    @Bean
    public Step childJob2Step2() {
        return stepBuilderFactory.get("childJob2Step2").tasklet((contribution, chunkContext) -> {
            System.out.println("Hello---->childJob2Step2");
            return RepeatStatus.FINISHED;
        }).build();

    }


}
