/**
 * 
 */
package com.jinm.example.ch06.listener;

import org.springframework.batch.core.ItemReadListener;

import com.jinm.example.ch06.CreditBill;

/**
 *
 * 2013-9-16下午06:22:13
 */
public class ErrorItemReadListener implements ItemReadListener<CreditBill> {
	@Override
    @SuppressWarnings("unused")
	public void beforeRead() {
		int i = 1/0;
		System.out.println("ErrorItemReadListener.beforeRead()");
	}

	@Override
    public void afterRead(CreditBill item) {
		System.out.println("ErrorItemReadListener.afterRead()");
	}

	@Override
    public void onReadError(Exception ex) {
		System.out.println("ErrorItemReadListener.onReadError()");
	}
}
