/**
 * 
 */
package com.jinm.example.ch07;

import org.springframework.batch.item.ItemProcessor;

/**
 * 
 *
 * 2013-9-19下午01:15:37
 */
public class CreditBillProcessor implements
		ItemProcessor<CreditBill, CreditBill> {

	@Override
    public CreditBill process(CreditBill bill) throws Exception {
		System.out.println(bill.toString());
		return bill;
	}
}
