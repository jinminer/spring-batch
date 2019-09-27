package com.jinm.learning.spring.batch.learningspringbatch.reader.jdbc;

import java.util.Date;

/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

public class Person {

    private Integer id;
    private String name;
    private String perDesc;
    private Date createTime;
    private Date updateTime;
    private String sex;
    private Float score;
    private Double price;

    public Person() {
        super();
    }

    public Person(Integer id, String name, String perDesc, Date createTime, Date updateTime, String sex, Float score,
                  Double price) {
        super();
        this.id = id;
        this.name = name;
        this.perDesc = perDesc;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.sex = sex;
        this.score = score;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getPerDesc() {
        return perDesc;
    }

    public void setPerDesc(String perDesc) {
        this.perDesc = perDesc;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", perDesc=" + perDesc + ", createTime=" + createTime + ", updateTime="
                + updateTime + ", sex=" + sex + ", score=" + score + ", price=" + price + "]";
    }


}
