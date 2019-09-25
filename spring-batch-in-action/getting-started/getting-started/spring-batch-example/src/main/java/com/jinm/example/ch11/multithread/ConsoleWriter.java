/**
 * 
 */
package com.jinm.example.ch11.multithread;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

/**
 * 
 *
 * 2013-11-16下午10:58:20
 */
public class ConsoleWriter implements ItemWriter<String> {

	@Override
    public void write(List<? extends String> items) throws Exception {
		System.out.print("Write begin:");
		for(String item : items){
			System.out.print(item + ",");
		}
		System.out.print("Write end!!");
		System.out.println("Job Write Thread name: " + Thread.currentThread().getName());
	}

}
