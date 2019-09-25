/**
 * 
 */
package com.jinm.example.ch11.partition.db;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;


/**
 * 
 *
 * 2014-3-22上午10:11:29
 */
public class PartitionStepExecutionListener implements StepExecutionListener {
	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("ThreadName=" + Thread.currentThread().getName() + "; " 
				+ "StepName=" + stepExecution.getStepName() + ";");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}
