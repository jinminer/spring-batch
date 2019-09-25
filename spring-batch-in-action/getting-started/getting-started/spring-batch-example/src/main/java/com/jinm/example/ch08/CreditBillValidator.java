/**
 * 
 */
package com.jinm.example.ch08;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

/**
 *
 * 2013-9-30下午04:36:12
 */
public class CreditBillValidator implements Validator<CreditBill> {

	@Override
	public void validate(CreditBill creditBill) throws ValidationException {
		if(Double.compare(0, creditBill.getAmount()) >0) {
			throw new ValidationException("Credit bill cannot be negative!");
		}	
	}
}
