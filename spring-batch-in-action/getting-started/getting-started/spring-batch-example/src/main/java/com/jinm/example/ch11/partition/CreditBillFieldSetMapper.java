/**
 * 
 */
package com.jinm.example.ch11.partition;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;


/**
 * 
 *
 * 2014-1-11下午02:29:42
 */
public class CreditBillFieldSetMapper implements FieldSetMapper<CreditBill> {

	@Override
    public CreditBill mapFieldSet(FieldSet fieldSet) throws BindException {
		CreditBill result = new CreditBill();
		result.setId(fieldSet.readString("id"));
		result.setAccountID(fieldSet.readString("accountID"));
		result.setName(fieldSet.readString("name"));
		result.setAmount(fieldSet.readDouble("amount"));
		result.setDate(fieldSet.readString("date"));
		result.setAddress(fieldSet.readString("address"));
		return result;
	}
}
