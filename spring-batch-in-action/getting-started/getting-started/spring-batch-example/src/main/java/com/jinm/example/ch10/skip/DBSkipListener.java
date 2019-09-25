/**
 * 
 */
package com.jinm.example.ch10.skip;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * 2013-3-29上午08:02:00
 */
public class DBSkipListener implements SkipListener<String, String> {
	private JdbcTemplate jdbcTemplate;

	@Override
    public void onSkipInRead(Throwable t) {
		if (t instanceof FlatFileParseException) {
			jdbcTemplate.update("insert into skipbills "
					+ "(line,content) values (?,?)",
					((FlatFileParseException) t).getLineNumber(),
					((FlatFileParseException) t).getInput());
		}
	}

	@Override
    public void onSkipInWrite(String item, Throwable t) {
		// TODO Auto-generated method stub
	}

	@Override
    public void onSkipInProcess(String item, Throwable t) {
		// TODO Auto-generated method stub
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
