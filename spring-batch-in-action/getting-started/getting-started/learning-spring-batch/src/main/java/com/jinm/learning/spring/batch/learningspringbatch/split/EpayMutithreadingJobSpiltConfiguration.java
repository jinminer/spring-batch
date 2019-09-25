package com.jinm.learning.spring.batch.learningspringbatch.split;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * epay multithreading job spilt application.
 * Created by jinm on  2019/09/25.  contact: keanemer.gmail.com
 */

@Configuration
public class EpayMutithreadingJobSpiltConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    /**
     * 创建job运行Flow,我们利用
     * split(new SimpleAsyncTaskExecutor()).add()让flow异步执行,add()中可以添加多个Flow
     *
     * @return
     */
    @Bean
    public Job spiltJob() {
        return jobBuilderFactory.get("spiltJob5")
                .start(jobSpiltFlow1())
                .split(new SimpleAsyncTaskExecutor())
                .add(jobSpiltFlow2())
                .split(new SimpleAsyncTaskExecutor())
                .add(jobSpiltFlow3())
                .end().build();

    }

    // 创建Flow1
    @Bean
    public Flow jobSpiltFlow1() {
        return new FlowBuilder<Flow>("jobSpiltFlow1")
                .start(stepBuilderFactory.get("jobSpiltStep1").tasklet(tasklet()).build())
                .next(stepBuilderFactory.get("jobSpiltStep2").tasklet(tasklet()).build()).build();

    }

    // 创建Flow2
    @Bean
    public Flow jobSpiltFlow2() {
        return new FlowBuilder<Flow>("jobSpiltFlow2")
                .start(stepBuilderFactory.get("jobSpiltStep3").tasklet(tasklet()).build())
                .next(stepBuilderFactory.get("jobSpiltStep4").tasklet(tasklet()).build()).build();

    }

    // 创建Flow3
    @Bean
    public Flow jobSpiltFlow3() {
        return new FlowBuilder<Flow>("jobSpiltFlow2")
                .start(stepBuilderFactory.get("jobSpiltStep5").tasklet(tasklet()).build())
                .next(stepBuilderFactory.get("jobSpiltStep6").tasklet(tasklet()).build()).build();

    }

    private Tasklet tasklet() {
        return new PrintTasklet();
    }

    // step执行的任务类（可以写为外部类，此处为了方便，写为内部类）
    private class PrintTasklet implements Tasklet {

        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            System.out.println("has been execute on stepName:" + chunkContext.getStepContext().getStepName()
                    + ",has been execute on thread:" + Thread.currentThread().getName());
            return RepeatStatus.FINISHED;
        }

    }

}
