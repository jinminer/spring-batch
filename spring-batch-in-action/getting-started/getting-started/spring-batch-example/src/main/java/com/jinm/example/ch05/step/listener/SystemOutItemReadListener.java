/**
 * 
 */
package com.jinm.example.ch05.step.listener;

import org.springframework.batch.core.ItemReadListener;

/**
 *
 * 2012-9-1上午08:09:37
 */
public class SystemOutItemReadListener implements ItemReadListener<String> {

	@Override
    public void beforeRead() {
		System.out.println("ItemReadListener.beforeRead()");
	}

	@Override
    public void afterRead(String item) {
		System.out.println("ItemReadListener.afterRead()");
	}

	@Override
    public void onReadError(Exception ex) {
		System.out.println("ItemReadListener.onReadError()");
	}

}
