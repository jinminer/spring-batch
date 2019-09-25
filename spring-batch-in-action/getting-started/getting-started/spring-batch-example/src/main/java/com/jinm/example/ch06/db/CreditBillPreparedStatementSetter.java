/**
 * 
 */
package com.jinm.example.ch06.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;

/**
 *
 *
 */
public class CreditBillPreparedStatementSetter implements
		PreparedStatementSetter {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.PreparedStatementSetter#setValues(java.sql.PreparedStatement)
	 */
	@Override
    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setString(1, "5");
	}

}
