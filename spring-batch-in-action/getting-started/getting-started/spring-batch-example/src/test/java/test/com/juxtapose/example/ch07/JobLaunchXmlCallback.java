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
 * 2013-2-28下午08:34:48
 */
public class JobLaunchXmlCallback {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JobLaunchBase.executeJob("ch07/job/job-xml-callback.xml", "xmlFileReadAndWriterJob",
				new JobParametersBuilder().addDate("date", new Date()));
	}
}
