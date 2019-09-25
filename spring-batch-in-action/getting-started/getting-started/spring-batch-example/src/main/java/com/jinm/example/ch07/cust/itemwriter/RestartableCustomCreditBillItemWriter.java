/**
 * 
 */
package com.jinm.example.ch07.cust.itemwriter;

import java.util.ArrayList;
import java.util.List;

import com.jinm.example.ch07.CreditBill;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;


/**
 * 
 *
 * 2013-9-29下午12:18:47
 */
public class RestartableCustomCreditBillItemWriter implements ItemWriter<CreditBill>, ItemStream {
	private List<CreditBill> result = new ArrayList<CreditBill>();
	private int currentLocation = 0;
	private static final String CURRENT_LOCATION = "current.location";
	
	@Override
	public void write(List<? extends CreditBill> items) throws Exception {
		for(;currentLocation < items.size();){
			result.add(items.get(currentLocation++));
		}
	}

	@Override
	public void open(ExecutionContext executionContext)
			throws ItemStreamException {
		if(executionContext.containsKey(CURRENT_LOCATION)){
			currentLocation = new Long(executionContext.getLong(CURRENT_LOCATION)).intValue();
        }else{
        	currentLocation = 0;
        }
	}

	@Override
	public void update(ExecutionContext executionContext)
			throws ItemStreamException {
		executionContext.putLong(CURRENT_LOCATION, new Long(currentLocation).longValue());
	}

	@Override
	public void close() throws ItemStreamException {}

	public List<CreditBill> getResult() {
		return result;
	}
}
