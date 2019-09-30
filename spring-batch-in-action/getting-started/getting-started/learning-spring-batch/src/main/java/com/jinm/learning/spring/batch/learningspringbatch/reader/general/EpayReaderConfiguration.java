package com.jinm.learning.spring.batch.learningspringbatch.reader.general;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
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
public class EpayReaderConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job epayJob() {
        return jobBuilderFactory.get("epayItemReaderJob1").start(epayJobStep()).build();

    }

    public Step epayJobStep() {
        return stepBuilderFactory.get("epayItemReaderJobStep").<String, String>chunk(2)
                .reader(epayItemReader()).writer(epayItemWriter()).build();
    }

    private ItemWriter<? super String> epayItemWriter() {
        return (ItemWriter<String>) items -> {
            for (String item : items) {
                System.out.println("output writer data: " + item);
            }
        };
    }

    @Bean
    public EpayItemReader epayItemReader() {
        List<String> data = Arrays.asList("dazhonghua", "xiaoriben", "meilijian", "falanxi", "deyizhi", "aierlan",
                "fandigang", "bajisitan", "baieluosi");
        return new EpayItemReader(data);
    }

}
