/**
 * 
 */
package com.jinm.example.ch05;

import org.springframework.batch.repeat.RepeatStatus;

/**
 *
 * 2013-3-23上午12:46:34
 */
public class HelloWorldService {
	
	public RepeatStatus hello() throws Exception {
		System.out.println("***---HelloWorldService.hello()---***");
		return RepeatStatus.FINISHED;
	}
}
