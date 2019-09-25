/**
 * 
 */
package com.jinm.example.ch10.skip;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * 
 *
 * 2013-10-20下午10:06:38
 */
public class ConsoleWriter implements ItemWriter<String> {

	@Override
    public void write(List<? extends String> items) throws Exception {
		System.out.println("Write begin:");
		for(String item : items){
			System.out.print(item + ",");
		}
		System.out.println("Write end!!");
	}

}
