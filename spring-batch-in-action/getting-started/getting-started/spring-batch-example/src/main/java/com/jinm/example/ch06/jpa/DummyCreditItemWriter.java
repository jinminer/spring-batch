/**
 * 
 */
package com.jinm.example.ch06.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * 
 *
 * 2013-9-1上午09:04:55
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
