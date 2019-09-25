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
 * 2013-9-21下午05:25:54
 */
public class JobLaunchFileSets {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch07/job/job-filesets.xml", "filesetsWriterJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
