/**
 * 
 */
package com.jinm.example.ch08;

import org.springframework.batch.item.validator.ValidationException;


/**
 * 
 *
 * 2013-9-30下午11:04:52
 */
public class ExistService {
	public CreditBill validate(CreditBill creditBill) throws ValidationException {
		if(Double.compare(0, creditBill.getAmount()) >0) {
			throw new ValidationException("Credit bill cannot be negative!");
		}
		return creditBill;
	}
}
