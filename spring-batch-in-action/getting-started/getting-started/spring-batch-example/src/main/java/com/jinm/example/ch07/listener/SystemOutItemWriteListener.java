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
 * 2013-9-29下午01:50:36
 */
public class SystemOutItemWriteListener implements ItemWriteListener<CreditBill> {
	@Override
    public void beforeWrite(List<? extends CreditBill> items) {
		System.out.println("SystemOutItemWriteListener.beforeWrite()");
	}

	@Override
    public void afterWrite(List<? extends CreditBill> items) {
		System.out.println("SystemOutItemWriteListener.afterWrite()");
	}

	@Override
    public void onWriteError(Exception exception,
                             List<? extends CreditBill> items) {
		System.out.println("SystemOutItemWriteListener.onWriteError()");
	}
}
