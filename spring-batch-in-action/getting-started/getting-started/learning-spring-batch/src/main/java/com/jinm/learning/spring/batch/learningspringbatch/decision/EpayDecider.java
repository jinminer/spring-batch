package com.jinm.learning.spring.batch.learningspringbatch.decision;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * .
 * Created by jinm on  2019/09/25.  contact: keanemer.gmail.com
 */

public class EpayDecider implements JobExecutionDecider {

    private int count=0;

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        count++;
        if(count%2==0) {
            return new FlowExecutionStatus("EVEN");
        }else {
            return new FlowExecutionStatus("ODD");
        }
    }

}
