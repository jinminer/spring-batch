/**
 * 
 */
package com.juxtapose.example.ch05.step.listener;

import org.springframework.batch.core.SkipListener;

/**
 * @author bruce.liu(mailto:jxta.liu@gmail.com)
 * 2012-9-1上午08:18:40
 */
public class SystemOutSkipListener implements SkipListener<String, String> {

	@Override
    public void onSkipInRead(Throwable t) {
		System.out.println("SkipListener.onSkipInRead()");		
	}

	@Override
    public void onSkipInWrite(String item, Throwable t) {
		System.out.println("SkipListener.onSkipInWrite()");
	}

	@Override
    public void onSkipInProcess(String item, Throwable t) {
		System.out.println("SkipListener.onSkipInProcess()");		
	}

}
