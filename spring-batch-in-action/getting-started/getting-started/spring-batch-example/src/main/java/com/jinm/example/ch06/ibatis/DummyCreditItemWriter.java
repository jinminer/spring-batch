/**
 * 
 */
package com.jinm.example.ch06.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;


/**
 * 
 *
 * 下午03:18:17
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
