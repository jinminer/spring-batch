/**
 * 
 */
package test.com.juxtapose.example.ch06;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;

import test.com.juxtapose.example.JobLaunchBase;

/**
 * 
 *
 * 下午09:09:42
 */
public class JobLaunchListener extends JobLaunchBase{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		executeJob("ch06/job/job-listener.xml", "itemReadJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
