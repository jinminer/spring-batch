/**
 * 
 */
package com.jinm.example.ch08;

import org.springframework.batch.item.ItemProcessor;


/**
 * 
 *
 * 2013-9-30上午10:34:34
 */
public class TranslateItemProcessor implements
		ItemProcessor<CreditBill, DestinationCreditBill> {

	@Override
    public DestinationCreditBill process(CreditBill bill) throws Exception {
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
