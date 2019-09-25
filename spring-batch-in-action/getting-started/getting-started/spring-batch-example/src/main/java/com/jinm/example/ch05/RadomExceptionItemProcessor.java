/**
 * 
 */
package com.jinm.example.ch05;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;

/**
 *
 * 2013-3-22下午04:50:05
 */
public class RadomExceptionItemProcessor implements ItemProcessor<String, String> {
	Random ra = new Random();
	
	@Override
    public String process(String item) throws Exception {
		int i = ra.nextInt(10);
		System.out.println("Process " + item + "; Random i=" + i);
		if(i%2 == 0){
			throw new RuntimeException("make error!");
		}else{
			return item;
		}
	}
}
