package com.jinm.learning.spring.batch.learningspringbatch.reader.jdbc;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

@Configuration
public class EpayJDBCItemReaderConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("epayJdbcItemWriter")
    private ItemWriter<? super Person> epayJdbcItemWriter;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job DBJdbcItemReaderJob() {
        return jobBuilderFactory.get("epayJdbcItemReaderJob2")
                .start(DBJdbcItemReaderJobStep())
                .build();

    }

    @Bean
    public Step DBJdbcItemReaderJobStep() {
        return stepBuilderFactory.get("epayJdbcItemReaderJobStep")
                .<Person, Person>chunk(100)
                .reader(epayJdbcItemReader())
                .writer(epayJdbcItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<Person> epayJdbcItemReader() {
        JdbcPagingItemReader<Person> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource); // 设置数据源
        reader.setFetchSize(100); // 设置一次最大读取条数
        reader.setRowMapper(new PersonMapper()); // 把数据库中的每条数据映射到Person对中
        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id,name,per_desc,create_time,update_time,sex,score,price"); // 设置查询的列
        queryProvider.setFromClause("from person_buf"); // 设置要查询的表
        Map<String, Order> sortKeys = new HashMap<String, Order>();// 定义一个集合用于存放排序列
        sortKeys.put("id", Order.ASCENDING);// 按照升序排序
        queryProvider.setSortKeys(sortKeys);
        reader.setQueryProvider(queryProvider);// 设置排序列
        return reader;
    }

}
