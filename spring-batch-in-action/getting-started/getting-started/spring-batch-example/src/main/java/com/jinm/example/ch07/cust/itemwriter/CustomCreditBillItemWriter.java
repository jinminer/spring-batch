/**
 * 
 */
package com.jinm.example.ch07.cust.itemwriter;

import java.util.List;

import com.jinm.example.ch07.CreditBill;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.TransactionAwareProxyFactory;


/**
 * 
 *
 * 2013-9-29下午12:18:47
 */
public class CustomCreditBillItemWriter implements ItemWriter<CreditBill> {
	private List<CreditBill> result = TransactionAwareProxyFactory.createTransactionalList();
	
	@Override
	public void write(List<? extends CreditBill> items) throws Exception {
		for(CreditBill item : items){
			result.add(item);
		}
	}

	public List<CreditBill> getResult() {
		return result;
	}

}
