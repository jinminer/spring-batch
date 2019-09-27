package com.jinm.learning.spring.batch.learningspringbatch.reader.jdbc;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

@Component("epayJdbcItemWriter")
public class EpayJDBCItemWriter implements ItemWriter<Person> {
    @Override
    public void write(List<? extends Person> list) throws Exception {
        for(Person person:list) {
            System.out.println(person);
        }
    }
}
