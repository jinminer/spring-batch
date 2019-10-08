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
  * demonstrate fail() and stopAndRestart()



## 4.2 flow

* Flow is nothing but a collection of Steps and related transitions, it is made up Steps or other flows
* Flow can be reused within and Job, from a Job to Job
* Using FlowBuilder to build a flow
  * Build a flow normally the same as build a job begins with start() and next() to end()





## 4.3 split

Execute multiple flows in parallel

* Define two flows
  * The included step will print the step name and the thread it executed in
* Create a Job start the two flows asynchronously using split
* Launch the Job to see the result







## 4.4 decider

In some situations, more information than the ExitStatus may be required to decide which step to ececute next. In this case, a JobExecutionDecider can be used to assist in the decision.



## 4.5 job nested

* One Job can be nested in another job. For simplicity, we call the nested job the child job, while the nesting job the parent job.
* Say we have two child jobs, childJob1 and childJob2 and these two jobs ar nested in parent job EODJob.
* The nested job will not execute separately but be launched by the parent job.



## 3.6 job listener

- What is Listener used in Spring Batch?
  - A way to control the job execution flow
- How to implement a listenter?
  - Implement the interface or using the annotation
- Listeners provided by Spring Batch, from JOB level to ITEM level:
  - JobExecutionListener(before..., after...)
  - StepExecutionListener(before..., after...)
  - ChunkListener(before..., after..., error...)
  - ItemReadListener, ItemProcessListener, ItemWriteListener(before..., after..., error)



## 4.7 job parameters

* what is it used for?
  * Pass information at runtime
* How it is used?
  * Pass in "key = value" pairs as program arguments



# 4 item reader



## 4.1 what is item reader

* ItemReader
  * interface for providing the data
* read() method
  * Reads a piece of input data and advance to the next one. Implementations must return null at the end of the input data set



## 4.2 reading data from db

In real practice, we nomally have to read input data from the database by using pagination, so here we will focus on how to read DB data using JdbcPagingItemReader provided by Spring Batch.



## 4.3 reading from flat files

Using to display how to read data from flat files

* `FlatFileItemReader`
  * Set lines to skip
  * Set resource
  * Set line tokenizer how to parse the line to get the FieldSet(similar to ResultSet)
  * 

## 4.4 xml item reader

Using `StaxEventItemReader<T>` to read data from xml file.



## 4.5 reading from multiple resources

It is not uncommon that we need to read data from multiple resources, say from multiple files in a given directory.

We can use `MultiResourceItemReader` to register these input files and set a delegate item reader for processing each standalone file.



## 4.6 Make ItemReader restartable

For chunk-oriented step, Spring Batch provide facility to manage the state.

How state is managed in a step is via `ItemStream` interface which provide developers the access the component where the state is maintained. This component mentioned here is `ExecutionContext` which in fact is a map of key value paris. The map represents the state of a particular step. The ExecutionContext makes it possible to restart a step cause the state is persisted in the JobRepository.[Check DB]

When there is something wrong during execution, the last state will be **updated** to JobRepository.

Next time when the job runs, the last state will be used to populate the ExecutionContext and the can continue running from where is left last time.

Check ItemStream interface: open() will be called at the begin of the step and ExecutionContext will be populated with the value from DB; update() will be called at the end of each step or transaction to update the ExecutionContext; close() is called when all chunk of data is done.



# 5 item writer



## 5.1 overview

Read is individually.

Write is chunked, will write a block of items(say a single batch update int DB to improve the efficiency)



## 5.2 writing data to db

Spring batch providers us various ways to write data to db:

* Neo4jItemWriter
* MongoItemWriter
* RepositoryItemWriter
* HibernateItemWriter
* JdbcBatchItemWriter
* JpaItemWriter
* GemfireItemWriter

 

## 5.3 Writing to flat files

`FlatFileItemWriter<T>` to write (serialize) each of the object of type T to (a line of String in) a file.

Let`s say a scenario, we read data from DB and write to a file.



## 5.4 wirting to xml files

The same as writing data to flat file, we just need to use `StaxEventItemWriter<T>` to write data to xml file.

In order to serialize data to xml, we can use `XStreamMarshall`.



## 5.5 multiple file writer

Using `CompositeItemWriter<T>` and `ClassifierCompositeItemWriter<T>` to write data to different file.



# 6 item processor



## 6.1 overview

ItemProcessor is used to do business logic, validation, filter and etc.

`ItemProcessor<I,O> ` is a interface with one method `O process(I item)` 

The method will accept an object of type I and return an object of type O.

If the method return null, the item will not be passed to ItemWriter thus can be filtered from been output.  



# 7 exception retry



## 7.1 exception retry



## 7.2 exception retry strategy



## 7.3 exception skip



## 7.4 exception skip listener





# 8 job launcher



## 8.1 job launcher





## 8.2 job operator





## 8.3 job scheduler





































































