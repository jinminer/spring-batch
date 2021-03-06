# spring-batch-getting-started

## 1 spring batch 简介

### 1.1 什么是批处理

**批处理**是指在面对复杂的业务以及海量的数据处理时，无需人工干预，仅需定期读入批量数据，然后完成相应业务处理并进行归档操作的一系列工作

* 批处理应用的特点

  * 自动执行：根据系统设定的工作步骤自动完成
  * 数据量大：少则百万，多则千万甚至上亿
  * 定时执行：如每天执行、每周或每月执行

* 批处理流程划分

  * 读数据：数据可能来自文件、数据库或消息队列等
  * 处理数据：处理读取的数据并形成输出结果，如：银行对账系统的资金对账处理
  * 写数据：将输出结果写入文件、数据库或消息队列等

* 批处理典型应用场景：

  * 系统A从数据库读取数据，经过业务处理后，导出系统B需要的数据到文件中，系统B读取文件，经过业务处理后，最后存放在数据库中。通常情况下该工作在夜间凌晨00:00~2:00之间进行，此时对系统的性能影响最小

    ![spring-batch-scenes-1](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.1-spring-batch-scenes-1.png>)

### 1.2 spring batch

* spring batch 是一个轻量级的、完善的批处理框架，旨在帮助企业建立健壮、高效的批处理应用
* 通过spring batch能够支持简单、复杂以及大数据量的批处理作业
  * spring batch 提供了大量可重用的组件，包括日志、追踪、事物、任务作业统计、任务重启、跳过、重复、资源管理。
  * 对于大数据量和高性能的批处理任务，spring batch 同样提供了高级功能和特性来支持，比如分区功能、远程功能等。
* spring batch**是一个批处理应用框架，不是调度框架，但需要和调度框架合作来构建完成批处理任务**。
  * 它只关注批处理任务相关的问题，如：事物、并发、监控、执行等，并不提供相应的调度功能。
  * 如果需要使用调度框架，可以集成其他商业或开源的调度框架，如：Quartz、Tivoli、Control-M、Cron等

#### 1.2.1 典型应用场景

典型的批处理应用通常从数据库、文件或队列中读取数据，之后使用一些方法处理数据(如：抽取、分析、处理、过滤等)，最终使用修改过的格式将数据写回目标系统。通常在一个无需用户交互的离线环境下，spring batch 能够自动进行基本的批处理迭代，也能够为一个数据集提供事物保证。

* spring batch支持的业务场景：
  * 定期提交批处理任务
  * 并行批处理，即并行处理任务
  * 企业消息驱动处理
  * 大规模的并行处理
  * 手动或定时重启
  * 安顺序处理依赖的任务（可扩展为工作流驱动的批处理）
  * 部分处理：如在回滚时忽略记录
  * 完整的批处理事物
* spring batch技术目标
  * 利用spring编程模型，使程序员专注于业务处理，让spring框架管理流程
  * 明确分离批处理的执行环境和应用
  * 将通用核心的服务以接口形式提供
  * 提供开箱即用的简单的、默认的核心执行接口
  * 提供spring框架中配置、自定义和扩展服务
  * 所有默认实现的核心服务能够容易地被扩展与替换，不会影响基础层
  * 提供一个简单地部署模式，如使用Maven进行编译

#### 1.2.2 spring batch 架构



![](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.2.2-spring-batch-core-structure-1.png)



* spring batch 核心架构分为三层：
  * 应用层：包含所有地批处理作业，通过spring框架管理开发人员自定义地代码
  * 核心层：spring batch 启动和控制所需要的核心类，如 `JobLauncher`、`Job`、`step`等
  * 基础架构层：
    * 提供底层通用的读-`ItemReader`、写-`ItemWriter`和服务处理（如：`RetryTemplate`-重试模板、`RepeatTemplate`-重复模板等，可以被应用层和核心层使用）；应用层和核心层建立在基础架构层之上。
    * spring batch的三层体系架构都可以进行独立水平扩展，并且不会对其他层级造成影响



### 1.3 spring batch 优势

spring batch 框架通过提供丰富的即开即用的组件和高可用性、高扩展性的能力，使得开发批处理应用的人员专注于业务的处理，提升批处理应用的开发效率，通过spring batch 可以快速地构建出轻量级地、健壮地并行处理应用



#### 1.3.1 丰富地开箱即用组件

* 多种类型资源的读、写
  * 读：支持文本文件、XML文件、数据库、JMS队列数据的读取
  * 写：支持文本文件、XML文件、数据库、JMS队列数据的写入
* 基础设置：作业仓库、作业调度器等



#### 1.3.2 面向chunk的处理

面向chunk的处理，支持多次读、一次写，避免了多次对资源的写入，大幅度提升了批处理应用的处理效率



#### 1.3.3 事物管理能力

spring batch 框架默认采用spring提供的声明式事务管理模型，面向chunk的操作支持事物管理，同时支持位每个tasklet操作设置细粒度的事物配置：

* 隔离级别
* 传播行为
* 超时设置



#### 1.3.4 元数据管理

* 自动记录 - 方便维护查看
  *   Job 执行情况：
    * 成功
    * 失败
    * 失败的异常信息
  * Step 执行情况：
    * 成功
    * 失败
    * 失败的异常信息
  * 执行次数
  * 重试次数
  * 跳过次数
  * 执行时间



#### 1.3.5 监控机制多样

* 提供多种监控技术，支持对批处理操作的信息进行查看和管理
* 提供灵活的监控模式：
  * 直接查看数据库
  * 通过spring batch 提供的 API 查看，并且可以基于这些 API 自己扩展开发独立的管理监控台
  * 通过 spring batch admin 进行查看
  * 通过 JMX 控制台查看



#### 1.3.6 批处理流程丰富

* spring batch 支持顺序任务、条件分支任务，基于顺序和条件分支任务可以组织复杂的任务流程
* spring batch 支持复用已经定义的 Job 和 Step，同时开放 Job 和 Step 的继承属性，方便扩展



#### 1.3.7 批处理流程的健壮性

spring batch 支持作业的跳过、重试、重启能力，避免因错误导致批处理作业的异常中断

* `Skip` - 跳过：通常在发生非致命异常的情况下，应该不中断批处理应用
* `Retry` - 重试：发生瞬态异常情况下，应该能够通过重试操作避免该类异常，保证批处理应用的连续性和稳定性
* `Restart` - 当批处理应用因错误执行失败后，应该能够在最后执行失败的地方重新启动 Job 实例



#### 1.3.8 批处理的扩展性

spring batch 通过并发和并行技术实现应用的横向、纵向扩展机制，满足数据处理性能的需要，提供了多种扩展机制：

* 多个线程执行一个 Step (Multithreaded step)
* 多个线程并行执行多个 Step (Parallelizing step)
* 远程执行作业 (Remote chunking)

* 分区执行 (Partitioning step)



#### 1.3.9 良好的粘性

spring batch 提供多种 Adapter 能力，可以很方便的适配集成现有服务，避免重新开发



### 1.4 spring batch 2.0 new features

相对于spring batch 1.X，spring batch 2.X 新特性如下：

* 支持 Java 5
* 非顺序的 Step 支持
* 面向 Chunk 处理
* 强化元数据访问
* 增强扩展性
* 可配置



#### 1.4.1 

spring batch 2.X 版本开始，支持 Java 5 的增强特性，如泛型、参数化类型等。



#### 1.4.2 支持非顺序的 Step

* spring batch 1.X 版本仅支持顺序执行 Step

  ![step-execute-proces-1](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.2-step-execute-proces-1.png>)



* spring batch 2.X 版本支持根据条件判断执行 Step

  ![step-execute-proces-2](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.2-step-execute-proces-2.png>)

  ![step-execute-proces-3](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.2-step-execute-proces-3.png)



#### 1.4.3 面向 Chunk 处理

* spring batch 1.X 版本对数据处理默认提供的策略式面向Item处理

  ![data-handle-strategy-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.3-data-handle-strategy-1.png)

  ​	![data-handle-strategy-2](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.3-data-handle-strategy-2.png)
  * 这时 ItemReader 和 ItemWriter 为了实现回滚场景，需要在内部定义复杂的方法，如：mark - 标记方法、reset - 恢复方法、clear - 清除方法等

    ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.3-item-rollback-1.png)



* spring batch 2.X 版本对数据处理默认提供的策略式面向 Chunk 处理

  * 面向 Chunk 的操作，简化了 ItemReader 和 ItemWriter 接口的复杂度

  * 如果提交间隔式多次，那么实际的读操作会被调用多次，而写操作只会被调用1次

  * 即读 Item 被汇总到列表中，最终统一写出

    ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.3-data-handle-strategy-3.png)

    ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.3-data-handle-strategy-4.png)

  * 数据回滚

    ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.3-data-handle-strategy-chunk-1.png)

    ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.3-data-handle-strategy-chunk-2.png)
    
  * `ItemProcessor` -  数据处理
  
    * 是 `ItemTransformer` 的重新命名，并将其层级提升到和 `ItemReader` 与 `ItemWriter` 相同等级
    * 通常的业务场景需要在数据写入前，对数据进行处理
  
    ![item-processor](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.3-item-processor-1.png)



#### 1.4.4 元数据访问

![metadata-access-1](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.4.4-metadata-access-1.png>)

* `JobRepository` - 增删改查接口
* `JobExplorer` - 元数据访问接口
* `JobOperator` - 元数据访问接口



#### 1.4.5 扩展性

spring batch 2.0 增加支持多进程执行任务

* 远程分块
  * 远程分块是指把一个Step进行技术分割，并且在分割时不需要对处理数据的结构由明确的了解
  * 任何输入源能够使用单进程读取并再动态分割后作为“块”发送给远程工作的工作进程
  * 远程进程实现了监听者模式，反馈请求、处理数据最终将处理结果异步返回
  * 请求和返回之间的传输会被确保在发送者和单个消费者之间
  * spring batch 在 spring integration 顶部实现了远程分块的特性
* 分区
  * 进行分区时，需要对数据的结构有一定的了解，如主键的范围、待处理文件的名字等
  * 分区模式的优点在于分区中每一个元素的处理器都能像一个普通 spring batch 任务的单步一样运行，不必取实现任何特殊的或是新的模式，来让它们能够更容易配置与测试
  * 分区并不存在从一个地方读取所有输入数据并进行序列化的瓶颈，所以比远程分块更具扩展性
  * spring batch中两个支持分区的接口：
    * `PartitionHandler` 
      * 知道执行结构，即它需要将请求发送到远程步骤，并使用任何可以使用的网格或是远程技术收集计算结果
      * `PartitionHandler` 是一个 SPI，它和  spring batch通过 TaskExecutor 为本地执行提供了一个外部实现方式，这个功能在有大量 I/O 操作处理场景时很有用
    * `StepExecutionSplitter` 



#### 1.4.6 可配置性

* spring batch 1.X 没有独立的命名空间，所有批处理的配置都需要通过 `<bean>` 、`<property>` 元素来配置
* spring batch 2.X 版本中增加了批处理命名空间，可通过 `<job>` 、 `<step>` 等属性进行批处理配置



### 1.5 spring batch 2.2 新特性

相对于 spring batch 2.0 系列，spring batch 2.2.X 系列提供了如下新的特性：

1. 支持 spring data 集成
2. 支持 java 配置
3. 支持重试模块(spring retry)重构
4. 作业参数变化(job parameters)



#### 1.5.1 spring data 集成



#### 1.5.2 支持 JavaConfig

* xml config

  ![configuration-xml-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.5.2-configuration-xml-1.png)

  ![configuration-xml-2](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.5.2-configuration-xml-2.png)



* java config

  * `@EnableBatchProcessing` - 依赖默认的 spring bean 对象，包括：
    * `JobLauncher` - 作业调度器
    * `JobRegistry` - 作业注册器
    * `PlatformTransactionManager` - 事物管理器
    * `JobBuilderFactory` - 作业构建工厂
    * `StepBuilderFactory` - 作业步构建工厂

  ![configuration-javaconfig-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/1.5.2-configuration-javaconfig-1.png)



#### 1.5.3 spring retry

* spring batch 2.2 之前：`org.springframework.batch.retry`
* spring batch 2.2 + ：`org.springframework.retry` 



#### 1.5.4 job parameters

- spring batch 2.2 之前：作业参数(Job Parameters)被强制用来标识作业实例(Job Instance)
- spring batch 2.2 + ：作业参数是否用来标识作业实例，用户可以自由选择
- 作业实例关联缺陷
  - 2.2 之前的版本强制作业参数作为作业的实例，即作业参数与作业实例ID进行关联，导致在作业重启的情况下无法修改作业参数
  - 自2.2版本开始将作业参数与作业执行器的 ID 进行关联，变得更灵活



## 2 spring batch hello world

### 2.1 示例

* 场景

  * 描述：个人使用信用卡消费，银行定期发送银行卡消费账单，本例模拟银行处理个人信用卡消费对账单对账，银行需要定期地把个人消费地记录导出成 CSV 格式文件，然后交给对账系统处理，本例子模拟银行读入 CSV 文件，经过处理后，生成新的对账单

  * 架构图

    ![scene-structure-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/getting-started/2.1-scene-structure-1.png)

### 2.2 代码

#### 2.2.1 项目结构

#### 2.2.2 准备对账文件



### 2.3 定义 Job 基础设施



### 2.4 定义对账 Job

#### 2.4.1 配置 ItemReader

#### 2.4.2 配置 ItemProcessor

#### 2.4.3 配置 ItemWriter



### 2.5 执行 Job

#### 2.5.1 Java 调用

#### 2.5.2 JUnit 单元测试



### 2.6 概念预览

Spring batch 中的一些基本概念

* `Job Repository` - 作业仓库，负责 Job、Step 执行过程中的状态保存
* `Job Launcher` - 作业调度器，提供执行 Job 的入口
* `Job` - 作业，由多个 Step 组成，封装整个批处理操作 
* `Step` - 作业步，Job 的一个执行环节，由多个或者一个 Step 组装成 Job
* `Tasklet` - Step 中具体执行逻辑的操作，可以重复执行，可以设置具体的同步、异步操作等
* `Chunk` - 给定数量 Item 的集合，可以定义对 Chunk 的读操作、处理操作、写操作，提交间隔等，这是 Spring Batch 框架的一个重要特性
* `Item` - 一条数据记录
* `ItemReader` - 从数据源(文件系统、数据库、队列等)中读取 Item
* `ItemWriter` - 将 Item 批量写入数据源(文件系统、数据库、队列等)




