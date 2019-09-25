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
public class JobLaunchJDBC {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch07/job/job-db-jdbc.xml", "dbWriteJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
