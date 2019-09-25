/**
 * 
 */
package com.jinm.example.ch06.jpa;

import org.springframework.batch.item.ItemProcessor;

/**
 * 
 *
 * 2013-9-1上午09:04:49
 */
public class CreditBillProcessor implements
		ItemProcessor<CreditBill, CreditBill> {

	@Override
    public CreditBill process(CreditBill bill) throws Exception {
		System.out.println(bill.toString());
		return bill;
	}
}
