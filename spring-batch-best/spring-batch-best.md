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



# 3 concepts



## 3.1 job

> A `Job` defines what a job is and how it is to be executed, and a `JobInstance` is a purely organizational object to group executions together, primarily to enable correct restart semantics. A `JobExecution`, however, is the primary storage mechanism for what actually happened during a run and contains many more properties that must be controlled and persisted.

![3-concepts-1-job](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-best/3-concepts-1-job.png>)

## 3.2 job instance

> A `JobInstance` refers to the concept of a logical job run. Consider a batch job that should be run once at the end of the day, such as the 'EndOfDay' `Job` from the preceding diagram. There is one 'EndOfDay' job, but each individual run of the `Job` must be tracked separately. In the case of this job, there is one logical `JobInstance` per day. For example, there is a January 1st run, a January 2nd run, and so on. If the January 1st run fails the first time and is run again the next day, it is still the January 1st run. (Usually, this corresponds with the data it is processing as well, meaning the January 1st run processes data for January 1st). Therefore, each `JobInstance` can have multiple executions (`JobExecution` is discussed in more detail later in this chapter), and only one `JobInstance` corresponding to a particular `Job` and identifying `JobParameters` can run at a given time.



## 3.3 job parameters

> Having discussed `JobInstance` and how it differs from Job, the natural question to ask is: "How is one `JobInstance`distinguished from another?" The answer is: `JobParameters`. A `JobParameters` object holds a set of parameters used to start a batch job. They can be used for identification or even as reference data during the run, as shown in the following image:

![3-concepts-2-jobinstance](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-best/3-concepts-2-jobinstance.png)



## 3.4 job execution

> A `JobExecution` refers to the technical concept of a single attempt to run a Job. An execution may end in failure or success, but the `JobInstance` corresponding to a given execution is not considered to be complete unless the execution completes successfully. Using the EndOfDay `Job` described previously as an example, consider a `JobInstance` for 01-01-2017 that failed the first time it was run. If it is run again with the same identifying job parameters as the first run (01-01-2017), a new `JobExecution` is created. However, there is still only one `JobInstance`.



## 3.5 step

> A `Step` is a domain object that encapsulates an independent, sequential phase of a batch job. Therefore, every Job is composed entirely of one or more steps. A `Step` contains all of the information necessary to define and control the actual batch processing. 
>
> * Tasklet - interface with one execute() method
> * Chunk-based - item-based to process the item one by one
>   * ItemReader - input
>   * ItemProcessor - processing(optional)
>   * ItemWriter - output
>
> 

![3-concepts-3-step](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-best/3-concepts-3-step.png)



## 3.6 step execution

> A `StepExecution` represents a single attempt to execute a `Step`. A new `StepExecution` is created each time a `Step` is run, similar to `JobExecution`. However, if a step fails to execute because the step before it fails, no execution is persisted for it. A`StepExecution` is created only when its `Step` is actually started.



## 3.7 execution context

> * An `ExecutionContext` represents a collection of key/value pairs that are persisted and controlled by the framework in order to allow developers a place to store persistent state that is scoped to a `StepExecution` object or a `JobExecution` object. 
> * It is also important to note that there is at least one `ExecutionContext` per `JobExecution` and one for every `StepExecution`. 
> * The one scoped to the `Step` is saved at every commit point in the `Step`, whereas the one scoped to the Job is saved in between every `Step`



## 3.8 job repository

> `JobRepository` is the persistence mechanism for all of the Stereotypes mentioned above. It provides CRUD operations for `JobLauncher`, `Job`, and `Step` implementations.
>
> When a `Job` is first launched, a `JobExecution` is obtained from the repository, and, during the course of execution, `StepExecution` and `JobExecution` implementations are persisted by passing them to the repository.



## 3.9 job launcher

> `JobLauncher` represents a simple interface for launching a `Job` with a given set of `JobParameters`, as shown in the following example:

```java
public interface JobLauncher {

public JobExecution run(Job job, JobParameters jobParameters)
            throws JobExecutionAlreadyRunningException, JobRestartException,
                   JobInstanceAlreadyCompleteException, JobParametersInvalidException;
}
```



## 3.10 item reader processer and writer

* item reader

  > `ItemReader` is an abstraction that represents the retrieval of input for a `Step`, one item at a time

* item writer

  > `ItemWriter` is an abstraction that represents the output of a `Step`, one batch or chunk of items at a time. 

* item processor

  > `ItemProcessor` is an abstraction that represents the business processing of an item. While the `ItemReader` reads one item, and the `ItemWriter` writes them, the `ItemProcessor` provides an access point to transform or apply other business processing. 



# 4 job flow



## 4.1 job flow introduction

* state machine
  * step 1 finish, should go to step 2 or step 3 ?
* based on the previous demo, create step 2 and step 3
  * using next() to sequentially execute step 1,2,3
  * using on(), to(), from() to show the same as above
  * demonstrate fail() and stopAndTestart()



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





































































