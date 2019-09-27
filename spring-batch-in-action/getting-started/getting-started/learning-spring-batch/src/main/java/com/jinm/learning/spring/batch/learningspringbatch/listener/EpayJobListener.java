package com.jinm.learning.spring.batch.learningspringbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;


/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

public class EpayJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println(jobExecution.getJobInstance().getJobName()+"before running......");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println(jobExecution.getJobInstance().getJobName()+"before running......");
    }

}
