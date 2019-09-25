/**
 * 
 */
package com.jinm.example.ch08.listener;

import com.jinm.example.ch08.CreditBill;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;

/**
 *
 * 2013-10-1上午12:01:33
 */
public class SystemOutAnnotation {
	@BeforeProcess
	public void beforeProcess(CreditBill item) {
		System.out.println("SystemOutAnnotation.beforeProcess()");
	}

	@AfterProcess
	public void afterProcess(CreditBill item, CreditBill result) {
		System.out.println("SystemOutAnnotation.afterProcess()");
	}

	@OnProcessError
	public void onProcessError(CreditBill item, Exception e) {
		System.out.println("SystemOutAnnotation.onProcessError()");
	}
}
