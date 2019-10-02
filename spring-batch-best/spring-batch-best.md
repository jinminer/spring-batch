# 1 overview

## 1.1 what is batch processing

* 批处理特点
  * finite data
    * finite(non-infinite) : the data can be processed to complete
    * 有限数据量，数据能够处理并完成
  * no interaction
    * interaction: web request, message
    * batch processing program run on the server without the need of interaction
    * 不需要交互，在后台服务运行
  * no interruption
    * from start to end
    * 不可中断，一个批处理任务从开始到结束无外界因素导致中断，框架内部需要提供容错处理机制
* 发展历程
  * historical
    * 批处理概念始于大型机出现
  * nowadays
    * 第三方
      * hadoop、spark
      * predictive model: off-line computing

## 1.2 typical scenario

> Non-interactive application can consider using batch processing: Spring Batch is one of the solution

* ETL
  * Extract Transform Load - 数据抽取转换和加载
* Report - 报表
  *  bank statement
* Data Science
  * predictive model - 预测模型
* Big Data
  * Hadoop/Spark

## 1.3 what is spring batch

* Java word leading batch processing framework 

* from Accenture and Spring
* Spring Batch 是 JSR-352 标准的来源



## 1.4 spring batch features

> Based on Spring Framework: IoC, Testing, Spring Boot ...

* Job flow state machine - 状态机
  * Job is flow of independent steps which can be transformed between different states. 
  * Steps can be configured in xml file or using Java-based configuration
* Transaction processing - 作业处理是事务级
  * Supported by the framework
  * Chunk-based
* Declarative IO - 声明式 IO
  * Easy use input and output support, focus on business logic
* Error handling - 差错处理
  * Offline processing, framework supports to skip and log errors
* Scalability ability - 可扩展机制
  * Distribution and integration with other framework like Hadoop



# 2 get started

## 2.1 Spring Boot based system

* start.spring.io
  * Spring Initializer
  * Choose what you want: Batch
* Download the zip file: Maven project
* Import into IDE



## 2.2 Create a simple Job

* Configuration package
* Add JobConfiguration class - Java based config(xml)
  * `@Configuration` 
  * `@EnableBatchProcessing` 

* Dependencies - `@Autowired` 
  * `JobBuilderFactory` 
  * `StepBuilderFactory` 



## 2.3 Config MySQL DB

As previous demo that based on the in memory H2 DB, we will config to use MySQL DB to persist JobRepository related data

1. Introduce jdbc and mysql dependencies in pom file
2. Add config items in application.properties file



# 3 job



## 3.1 job



## 3.2 job flow



## 3.3 job split



## 3.4 job decider



## 3.5 job nested



## 3.6 job listener



## 3.7 job parameters



# 4 item reader



## 4.1 item reader



## 4.2 db item reader



## 4.3 flat file item reader



## 4.4 xml item reader



## 4.5 multiple file reader



## 4.6 item reader exception



# 5 item writer



## 5.1 item writer



## 5.2 db item writer

 

## 5.3 flat file item writer



## 5.4 xml item writer



## 5.5 multiple file writer



# 6 item processor



## 6.1 item processor



# 7 exception retry



## 7.1 exception retry



## 7.2 exception retry strategy



## 7.3 exception skip



## 7.4 exception skip listener





# 8 job launcher



## 8.1 job launcher





## 8.2 job operator





## 8.3 job scheduler





































































