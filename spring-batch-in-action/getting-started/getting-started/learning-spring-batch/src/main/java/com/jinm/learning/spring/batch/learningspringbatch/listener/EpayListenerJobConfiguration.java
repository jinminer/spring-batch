package com.jinm.learning.spring.batch.learningspringbatch.listener;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

@Configuration
public class EpayListenerJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //监听Job执行
    @Bean
    public Job epayListenerJob() {
        return jobBuilderFactory.get("epayListenerJob")
                .start(epayListenerStep())
                .listener(new EpayJobListener())
                .build();
    }

    private Step epayListenerStep() {
        return stepBuilderFactory.get("epayListenerStep")
                .<String,String>chunk(2)
                .faultTolerant()
                .listener(new EpayChunkListener())
                .reader(reader())
                .writer(writer())
                .build();
    }

    private ItemReader<? extends String> reader() {
        return new ListItemReader<>(Arrays.asList("maozedong","zhude","pendehuai","zhouenlai","liushaoqi"));
    }

    private ItemWriter<? super String> writer() {
        return (ItemWriter<String>) items -> {
            for(String item:items) {
                System.out.println("Writing item: "+item);
            }

        };
    }

}
