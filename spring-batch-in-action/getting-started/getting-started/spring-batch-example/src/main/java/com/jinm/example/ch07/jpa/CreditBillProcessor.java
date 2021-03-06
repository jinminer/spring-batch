/**
 * 
 */
package com.jinm.example.ch07.jpa;

import org.springframework.batch.item.ItemProcessor;

/**
 *
 * 2013-1-6下午09:55:38
 */
public class CreditBillProcessor implements
		ItemProcessor<CreditBill, DestinationCreditBill> {

	@Override
    public DestinationCreditBill process(CreditBill bill) throws Exception {
		System.out.println(bill.toString());
		DestinationCreditBill destCreditBill = new DestinationCreditBill();
		destCreditBill.setAccountID(bill.getAccountID());
		destCreditBill.setAddress(bill.getAddress());
		destCreditBill.setAmount(bill.getAmount());
		destCreditBill.setDate(bill.getDate());
		destCreditBill.setId(bill.getId());
		destCreditBill.setName(bill.getName());
		return destCreditBill;
	}
}
