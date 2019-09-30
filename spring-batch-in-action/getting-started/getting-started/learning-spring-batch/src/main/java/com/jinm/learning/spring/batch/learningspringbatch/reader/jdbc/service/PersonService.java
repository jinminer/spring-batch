package com.jinm.learning.spring.batch.learningspringbatch.reader.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * persion service.
 * Created by jinm on  2019/09/27.  contact: keanemer.gmail.com
 */

@Service
public class PersonService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createPerson(){

        List<String> list = new ArrayList<>();
        for (int i = 2; i < 10008; i++) {
             list.add("insert into person_buf (`id`, `name`, `per_desc`, `create_time`, `update_time`, `sex`, `score`, `price`)" +
                     "values ("+ i +", 'jinm', 'time', '2019-09-27', '2019-09-27', 'm', 98.98, 98.98)");
        }
        jdbcTemplate.batchUpdate(list.toArray(new String[list.size()]));
    }

}
