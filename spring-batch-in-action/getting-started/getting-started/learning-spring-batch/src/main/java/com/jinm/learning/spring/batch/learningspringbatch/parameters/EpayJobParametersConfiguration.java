package com.jinm.learning.spring.batch.learningspringbatch.parameters;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

@Configuration
public class EpayJobParametersConfiguration implements StepExecutionListener{

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    private Map<String, JobParameter> parames;

    @Bean
    public Job epayParametersJob() {
        return jobBuilderFactory.get("epayParametersJob")
                .start(epayParametersJobStep())
                .build();
    }

    private Step epayParametersJobStep() {

        return stepBuilderFactory.get("epayParametersJobStep")
                .listener(this)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("parame is: "+parames.get("info"));
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println(stepExecution.getStepName()+"运行之前...........");
        parames = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println(stepExecution.getStepName()+"运行之完毕...........");
        return null;
    }

}
