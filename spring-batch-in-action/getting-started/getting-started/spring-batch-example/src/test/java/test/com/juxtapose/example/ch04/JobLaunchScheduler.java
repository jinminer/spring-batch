/**
 * 
 */
package test.com.juxtapose.example.ch04;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 *
 * 2013-2-28下午08:34:48
 */
public class JobLaunchScheduler {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ApplicationContext context = new ClassPathXmlApplicationContext("ch04/job/job-spring-scheduler.xml");
	}
}
