/**
 * 
 */
package com.jinm.example.ch06.jms;

import org.springframework.batch.item.ItemProcessor;

import com.jinm.example.ch06.CreditBill;

/**
 *
 * 2013-3-25下午11:59:14
 */
public class TransactionItemProcessor implements ItemProcessor<CreditBill, CreditBill> {
	private String errorCount = "3";
	private int index = 0;

	@Override
    public CreditBill process(CreditBill item) throws Exception {
		index ++;
		if(errorCount.equals(""+index)){
			index = 0;
			throw new RuntimeException("make error!");
		}
		System.out.println("TransactionItemProcessor.process() item is:" + index);
		return item;
	}
	
	public String getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(String errorCount) {
		this.errorCount = errorCount;
	}
}
