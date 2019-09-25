/**
 * 
 */
package com.jinm.example.ch08.listener;

import com.jinm.example.ch08.CreditBill;
import org.springframework.batch.core.ItemProcessListener;

/**
 *
 * 2013-10-1上午12:04:10
 */
public class ErrorItemProcessListener implements ItemProcessListener<CreditBill, CreditBill> {

	@SuppressWarnings("unused")
	@Override
	public void beforeProcess(CreditBill item) {
		int i = 1/0;
		System.out.println("ErrorItemReadListener.beforeProcess()");
	}

	@Override
	public void afterProcess(CreditBill item, CreditBill result) {
		System.out.println("ErrorItemReadListener.afterRead()");
	}

	@Override
	public void onProcessError(CreditBill item, Exception e) {
		System.out.println("ErrorItemReadListener.onReadError()");
	}

}
