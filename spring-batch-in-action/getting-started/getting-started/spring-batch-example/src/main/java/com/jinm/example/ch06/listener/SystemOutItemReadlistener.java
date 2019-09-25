/**
 * 
 */
package com.jinm.example.ch06.listener;

import org.springframework.batch.core.ItemReadListener;

import com.jinm.example.ch06.CreditBill;

/**
 *
 * 2013-9-16下午05:47:05
 */
public class SystemOutItemReadlistener implements ItemReadListener<CreditBill> {
	@Override
    public void beforeRead() {
		System.out.println("SystemOutItemReadlistener.beforeRead()");
	}

	@Override
    public void afterRead(CreditBill item) {
		System.out.println("SystemOutItemReadlistener.afterRead()");
	}

	@Override
    public void onReadError(Exception ex) {
		System.out.println("SystemOutItemReadlistener.onReadError()");
	}
}
