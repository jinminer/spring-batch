/**
 * 
 */
package com.jinm.example.ch06.ibatis;

import org.springframework.batch.item.ItemProcessor;

/**
 *
 * 2013-1-6下午09:55:38
 */
public class CreditBillProcessor implements
		ItemProcessor<CreditBill, CreditBill> {

	@Override
    public CreditBill process(CreditBill bill) throws Exception {
		System.out.println(bill.toString());
		return bill;
	}
}
