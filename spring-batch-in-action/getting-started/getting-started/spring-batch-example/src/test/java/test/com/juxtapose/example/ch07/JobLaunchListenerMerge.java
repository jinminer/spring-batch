/**
 * 
 */
package test.com.juxtapose.example.ch07;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;

import test.com.juxtapose.example.JobLaunchBase;

/**
 * 
 *
 * 下午09:09:42
 */
public class JobLaunchListenerMerge extends JobLaunchBase{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		executeJob("ch07/job/job-listener.xml", "mergeChunkJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
