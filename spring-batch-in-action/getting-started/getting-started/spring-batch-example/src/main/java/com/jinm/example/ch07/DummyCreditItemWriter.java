/**
 * 
 */
package com.jinm.example.ch07;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;


/**
 * 
 *
 * 2013-9-29下午02:27:58
 */
public class DummyCreditItemWriter implements ItemWriter<CreditBill> {
	
	public List<CreditBill> creditBills = new ArrayList<CreditBill>();

	@Override
    public void write(List<? extends CreditBill> items) throws Exception {
		creditBills.addAll(items);
	}

	public List<CreditBill> getCredits() {
		return creditBills;
	}
}
