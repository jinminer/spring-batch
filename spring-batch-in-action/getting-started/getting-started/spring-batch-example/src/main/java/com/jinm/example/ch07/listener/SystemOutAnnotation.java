/**
 * 
 */
package com.jinm.example.ch07.listener;

import java.util.List;

import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.annotation.OnWriteError;

import com.jinm.example.ch07.CreditBill;


/**
 * 
 *
 * 2013-9-29下午01:48:24
 */
public class SystemOutAnnotation {
	@BeforeWrite
	public void beforeWrite(List<? extends CreditBill> items) {
		System.out.println("SystemOutAnnotation.beforeWrite()");
	}

	@AfterWrite
	public void afterWrite(List<? extends CreditBill> items) {
		System.out.println("SystemOutAnnotation.afterWrite()");
	}

	@OnWriteError
	public void onWriteError(Exception exception,
			List<? extends CreditBill> items) {
		System.out.println("SystemOutAnnotation.onWriteError()");
	}
}
