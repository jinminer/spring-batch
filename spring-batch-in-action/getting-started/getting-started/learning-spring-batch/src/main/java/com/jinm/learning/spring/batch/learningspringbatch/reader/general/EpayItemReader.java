package com.jinm.learning.spring.batch.learningspringbatch.reader.general;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

public class EpayItemReader implements ItemReader<String> {

    private final Iterator<String> iterator;

    public EpayItemReader(List<String> data) {
        this.iterator = data.iterator();
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator.hasNext()) {
            return this.iterator.next();
        } else {
            return null;
        }
    }


}
