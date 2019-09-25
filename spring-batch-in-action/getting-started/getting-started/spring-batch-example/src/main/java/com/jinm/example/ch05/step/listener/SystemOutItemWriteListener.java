/**
 * 
 */
package com.jinm.example.ch05.step.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

/**
 *
 * 2012-9-1上午08:11:52
 */
public class SystemOutItemWriteListener implements ItemWriteListener<String> {

	@Override
    public void beforeWrite(List<? extends String> items) {
		System.out.println("ItemWriteListener.beforeWrite()");		
	}

	@Override
    public void afterWrite(List<? extends String> items) {
		System.out.println("ItemWriteListener.afterWrite()");		
	}

	@Override
    public void onWriteError(Exception exception, List<? extends String> items) {
		System.out.println("ItemWriteListener.onWriteError()");		
	}

}
