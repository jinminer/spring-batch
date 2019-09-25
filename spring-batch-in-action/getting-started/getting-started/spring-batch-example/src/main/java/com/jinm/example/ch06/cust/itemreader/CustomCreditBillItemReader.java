/**
 * 
 */
package com.jinm.example.ch06.cust.itemreader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.jinm.example.ch06.CreditBill;

/**
 *
 * 2013-9-7下午02:12:56
 */
public class CustomCreditBillItemReader implements ItemReader<CreditBill> {
	private List<CreditBill> list = new ArrayList<CreditBill>();
	
	public CustomCreditBillItemReader(List<CreditBill> list){
		this.list = list;
	}

	@Override
	public CreditBill read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		if (!list.isEmpty()) {
            return list.remove(0);
        }
		return null;
	}
}
