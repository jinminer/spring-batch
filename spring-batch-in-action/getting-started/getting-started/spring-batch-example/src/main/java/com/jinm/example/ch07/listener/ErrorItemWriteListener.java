/**
 * 
 */
package com.jinm.example.ch07.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import com.jinm.example.ch07.CreditBill;


/**
 * 
 *
 * 2013-9-29下午01:46:28
 */
public class ErrorItemWriteListener implements ItemWriteListener<CreditBill> {
	@SuppressWarnings("unused")
	@Override
	public void beforeWrite(List<? extends CreditBill> items) {
		int i = 1/0;
		System.out.println("ErrorItemReadListener.beforeRead()");
	}

	@Override
	public void afterWrite(List<? extends CreditBill> items) {
		System.out.println("ErrorItemReadListener.afterRead()");
	}

	@Override
	public void onWriteError(Exception exception,
			List<? extends CreditBill> items) {
		System.out.println("ErrorItemReadListener.onReadError()");
	}
}
