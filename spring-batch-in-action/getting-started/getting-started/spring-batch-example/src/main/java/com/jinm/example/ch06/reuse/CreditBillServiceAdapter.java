/**
 * 
 */
package com.jinm.example.ch06.reuse;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.jinm.example.ch06.CreditBill;

/**
 *
 * 2013-9-7下午01:40:07
 */
public class CreditBillServiceAdapter implements InitializingBean{
	private ExistService existService;
	List<CreditBill> creditBillList;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.creditBillList = existService.queryAllCreditBill();
	}
	
	public CreditBill nextCreditBill() {
		if (creditBillList.size() > 0) {
			return creditBillList.remove(0);
		} else {
			return null;
		}
	}

	public ExistService getExistService() {
		return existService;
	}

	public void setExistService(ExistService existService) {
		this.existService = existService;
	}
}
