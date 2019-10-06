package com.jinm.learning.hellobatch.jobflowtwo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * .
 * Created by jinm on  2019/10/06.  contact: keanemer.gmail.com
 */

@Configuration
public class JobFlowTwoConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step jobFlowTwoStep1(){
        return stepBuilderFactory.get("jobFlowTwoStep1").tasklet(((contribution, chunkContext) -> {
            System.out.println("jobFlowTwoStep1");
            return RepeatStatus.FINISHED;
        })).build();
    }

    @Bean
    public Step jobFlowTwoStep2(){
        return stepBuilderFactory.get("jobFlowTwoStep2").tasklet(((contribution, chunkContext) -> {
            System.out.println("jobFlowTwoStep2");
            return RepeatStatus.FINISHED;
        })).build();
    }

    @Bean
    public Step jobFlowTwoStep3(){
        return stepBuilderFactory.get("jobFlowTwoStep3").tasklet(((contribution, chunkContext) -> {
            System.out.println("jobFlowTwoStep3");
            return RepeatStatus.FINISHED;
        })).build();
    }

    @Bean
    public Flow jobFlowTwoFlow(){
        return new FlowBuilder<Flow>("jobFlowTwoFlow")
                .start(jobFlowTwoStep1())
                .next(jobFlowTwoStep2())
                .build();
    }

    @Bean
    public Job jobFlowTwoJob(){
        return jobBuilderFactory.get("jobFlowTwoJob")
                .start(jobFlowTwoFlow())
                .next(jobFlowTwoStep3())
                .end()
                .build();
    }

}
