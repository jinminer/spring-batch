/**
 * 
 */
package com.jinm.example.ch04.exitstatus;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.launch.support.ExitCodeMapper;

/**
 *
 * 2013-3-18下午10:14:50
 */
public class CustomerExitCodeMapper implements ExitCodeMapper {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.launch.support.ExitCodeMapper#intValue(java.lang.String)
	 */
	@Override
    public int intValue(String exitCode) {
		if (ExitStatus.COMPLETED.getExitCode().equals(exitCode)) {
			return 1;
		} else if (ExitStatus.FAILED.getExitCode().equals(exitCode)) {
			return 2;
		} else if (ExitStatus.STOPPED.getExitCode().equals(exitCode)) {
			return 3;
		} else {
			return 4;
		}
	}
}
