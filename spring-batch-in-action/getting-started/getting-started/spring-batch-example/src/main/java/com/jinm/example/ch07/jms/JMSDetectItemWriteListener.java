/**
 * 
 */
package com.jinm.example.ch07.jms;

import org.junit.Assert;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.jms.core.JmsTemplate;

import com.jinm.example.ch07.CreditBill;

/**
 *
 * 2013-9-21下午08:51:51
 */
public class JMSDetectItemWriteListener implements StepExecutionListener{
	private JmsTemplate jmsTemplate;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		int writeCount = 0;
		Object obj = jmsTemplate.receiveAndConvert();
		while(null != obj){
			writeCount++;
			CreditBill result = (CreditBill) obj;
			System.out.println("Receive from jms queue:"+result);
			obj = jmsTemplate.receiveAndConvert();
		}
		Assert.assertEquals(stepExecution.getWriteCount(), writeCount);
		return stepExecution.getExitStatus();
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
