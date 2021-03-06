/**
 * 
 */
package test.com.juxtapose.example.ch11;

import java.util.Date;

import org.springframework.batch.core.JobParametersBuilder;

import test.com.juxtapose.example.JobLaunchBase;

/**
 * 
 *
 * 2013-11-16下午10:59:46
 */
public class JobLaunchMultiThreadJdbcSynchronizedRestart {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch11/job/job-multithreade-db.xml", "dbRestartSynchronizedJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
