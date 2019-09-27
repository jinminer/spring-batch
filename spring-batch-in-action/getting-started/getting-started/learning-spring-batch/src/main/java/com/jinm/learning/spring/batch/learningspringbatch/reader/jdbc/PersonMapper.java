package com.jinm.learning.spring.batch.learningspringbatch.reader.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

public class PersonMapper implements RowMapper<Person> {

    /**
     * rs一条结果集，rowNum代表当前行
     */
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(rs.getInt("id")
                ,rs.getString("name")
                ,rs.getString("per_desc")
                ,rs.getDate("create_time")
                ,rs.getDate("update_time")
                ,rs.getString("sex")
                ,rs.getFloat("score")
                ,rs.getDouble("price"));
    }

}
