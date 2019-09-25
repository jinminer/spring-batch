/**
 * 
 */
package com.jinm.example.ch06.flat;

import java.util.Map;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.JsonLineMapper;

import com.jinm.example.ch06.CreditBill;

/**
 *
 * 2013-4-3上午10:57:50
 */
public class WrappedJsonLineMapper implements LineMapper<CreditBill> {
	private JsonLineMapper delegate;

	@Override
    public CreditBill mapLine(String line, int lineNumber) throws Exception {
		CreditBill result = new CreditBill();
		Map<String, Object> creditBillMap = delegate.mapLine(line, lineNumber);
		result.setAccountID((String)creditBillMap.get("accountID"));
		result.setName((String)creditBillMap.get("name"));
		result.setAmount((Double)creditBillMap.get("amount"));
		result.setDate((String)creditBillMap.get("date"));
		result.setAddress((String)creditBillMap.get("address"));
		return result;
	}

	public JsonLineMapper getDelegate() {
		return delegate;
	}

	public void setDelegate(JsonLineMapper delegate) {
		this.delegate = delegate;
	}
}
