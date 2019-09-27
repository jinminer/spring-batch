package com.jinm.learning.spring.batch.learningspringbatch.decision;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * epay job flow decision configuration.
 *
 * @author jinm
 * @date 2019/09/25.  contact: keanemer.gmail
 */

@Configuration
public class EpayJobFlowDecisionConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //编写Job
    @Bean
    public Job FlowDecisionJob() {
        return jobBuilderFactory.get("FlowDecisionJob1")
                .start(firstStep()).next(decider())
                .from(decider()).on("EVEN").to(evenStep())
                .from(decider()).on("ODD").to(oddStep())
                .from(oddStep()).on("*").to(decider())
                .end()
                .build();

    }
    @Bean
    public Step firstStep() {
        return stepBuilderFactory.get("firstStep").tasklet((contribution, chunkContext) -> {
            System.out.println("Hello firstStep..");
            return RepeatStatus.FINISHED;
        }).build();

    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep").tasklet((contribution, chunkContext) -> {
            System.out.println("Hello evenStep..");
            return RepeatStatus.FINISHED;
        }).build();

    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep").tasklet((contribution, chunkContext) -> {
            System.out.println("Hello oddStep..");
            return RepeatStatus.FINISHED;
        }).build();

    }

    //决策器
    @Bean
    public JobExecutionDecider decider() {
        return new EpayDecider();

    }

}
