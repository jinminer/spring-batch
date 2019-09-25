/**
 * 
 */
package com.jinm.example.ch09.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.jinm.example.ch09.Constant;
import com.jinm.example.ch09.CreditService;

/**
 *
 * 2013-10-7上午10:20:31
 */
public class VerifyTasklet implements Tasklet {
	private String outputDirectory;
	private String readFileName;
	private CreditService creditService;
	
	/* (non-Javadoc)
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		String status = creditService.verify(outputDirectory, readFileName);
		if (null != status) {
			chunkContext.getStepContext().getStepExecution()
					.getExecutionContext().put(Constant.VERITY_STATUS, status);
		}
		return RepeatStatus.FINISHED;
	}

	public void setCreditService(CreditService creditService) {
		this.creditService = creditService;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public void setReadFileName(String readFileName) {
		this.readFileName = readFileName;
	}

}
