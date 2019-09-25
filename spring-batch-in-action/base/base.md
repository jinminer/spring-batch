# spring batch base

* 批处理核心原理
* 典型作业配置
* 作业步配置
* 批处理三步策略
  * 数据读
  * 数据处理
  * 数据写
* 不同介质中数据的读、处理、写操作
  * 分隔符类型文件
  * 定长文件
  * JSON 格式文件
  * 复杂类型格式文件
  * XML 格式文件
  * 数据库
    * JDBC
    * Hibernate
    * 存储过程
    * JPA
    * Ibatis
  * JMS 消息队列

## 3 spring batch 核心原理

* spring batch 架构图

  ![spring-batch-structure-1](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3-spring-batch-structure-1.png>)



* spring batch 架构主要领域对象

  * `Job` - 作业：批处理中的核心概念，是 Batch 操作的基础单元
  * `Job Instance` - 作业实例：每个作业执行时，都会生成一个实例，实例会被存放在 `JobRepository` 中，如果作业失败，下次重新执行该作业的时候，会使用同一个作业实例； `Job` 和 `Job Instance` 的关系可以类比为类和实例对象
  *  `Job Parameters` - 作业参数：它是一组用来启动批处理任务的参数，在启动 `Job` 时，可以设置任何需要的作业参数，需要注意的是作业参数会用来标识作业实例，即不同的 `Job` 实例是通过 `Job` 参数来区分的
  *  `Job Execution` - 作业执行器：其负责具体 `Job` 的执行，每次运行 `Job` 都会启动一个新的 `Job` 执行器 
  * `Job Repository` - 作业仓库：其负责存储作业执行过程中的状态数据及结果，为 `JobLauncher` 、`Job` 、`Step` 提供标准的 CRUD 操作 
  * `Job Launcher` - 作业调度器：它根据给定的 `JobParameters` 执行作业 
  * `Step` - 作业步：`Job` 的一个执行环节，多个或一个 `Step` 组装成 `Job` ，封装了批处理任务中的一个独立的连续阶段
  * `Step Execution` - 作业步执行器：它负责具体 `Step` 的执行，每次运行 `Step` 都会启动一个新的执行器  
  * `Tasklet` - 是 `Step` 中具体执行逻辑的操作，可以重复执行，可以设置具体的同步、异步操作等 
  * `Execution Context` - 执行上下文：它是一组框架持久化与控制的 `key/value` 键值对，能够让开发者在 `Step Execution` 或 `Job Execution` 范畴保存需要进行持久化的状态   
  * `Item` - 条目：一条数据记录  
  * `Chunk` - 是给定数量 `Item` 的集合，可以定义对 `Chunk` 的读、写、处理操作，提交间隔等  
  * `Item Reader` - 条目读：其表示 `Step` 读取数据，一次读取一条
  * `Item Processor` - 条目处理：用于表示 `Item` 的业务处理  
  * `Item Writer` - 条目写：用于表示 `Step` 输出数据，一次输出一批



### 3.1 命名空间

Spring Batch 2.X 版本中增加了批处理的命名空间，简化了配置操作，对应的 XSD 如下：

`http://www.springframework.org/schema/batch` 

`http://www.springframework.org/schema/batch/spring-batch-2.2.xsd` 



### 3.2 Job

* 批处理作业 Job 由一组 Step 组成，同时是作业配置文件的顶层元素。每个作业由自己的名字、可以定义 Step 执行的顺序，以及定义作业是否可以重启。Job 的主要属性如下：

  ![beanxml-properties-job-1](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.2-beanxml-properties-job-1.png>)

* `Job` 、`Job Instance`、`Job Execution` 关系

  * Job 执行的时候会生成一个 Job Instance(作业实例)，Job Instance 包含执行 Job 期间产生的数据以及 Job 执行的状态信息等

  * `Job Instance` 通过 `Job Name` (作业名称)和 `Job Parameters` (作业参数)来区分

  * 每次 `Job` 执行的时都有一个 `Job Execution` (作业执行器)，`Job Execution` 负责具体 `Job` 的执行

    ![job-instance-execution-relation-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.2--job-instance-execution-relation-1.png)
    
  * 一个 `Job` 可能有一到多个 `Job Instance`
  
  * 一个 `Job Instance` 可能有一到多个 `Job Execution`  



#### 3.2.1 Job Instance

* `org.springframework.batch.core.JobInstance` 

* Job Instance 是一个运行期的概念，Job 每执行一次都会涉及一个 Job Instance。Job Instance 来源可能有两种：
  * 一种是根据设置的 Job Parameters 从 Job Repository 中获取一个
  * 另外如果根据 Job Parameters 从 Job Repository 没有获取 Job Instance ，则新创建一个

* Job Instance 和 Job Execution
  * 第一次执行 Job 时，会创建一个新的 Job Instance 和新的 Job Execution
  * 每次执行 Job 时，都会创建一个新的 Job Execution
  * 已经完成的 Job Instance，不能被重新执行
  * 在同一时刻，只能有一个 Job Execution 可以执行同一个 Job Instance



#### 3.2.2 Job Parameters

* `org.springframework.batch.core.JobParameters` 

* Job 通过 Job Parameters 来区分不同的 Job Instance，即 Job Name + Job Parameters 来唯一地确定一个 Job Instance。如果 Job Name 一样，则 Job Parameters 肯定不一样，但是对于不同地 Job 来说，允许有相同的 Job Parameters

  ![job-instance-execution-relation](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.2--job-instance-execution-relation-1.png>)

* Job Paramters 
  * 提供 JobParametersBuilder来构建参数
  * 支持四种类型的参数
    * String 
    * Data
    * Long
    * Double

#### 3.2.3 Job Execution

* `org.springframework.batch.core.JobExecution` 

* 表示 Job 执行的句柄，一次 Job 的执行可能成功有可能失败，只有 Job Execution 执行成功后，对应的 Job Instance 才会被完成
* 主要属性：
  * `status` - BatchStatus 对象表示执行状态
    * `BatchStatus.STARTED` - 运行时
    * `BatchStatus.FAILED` - 执行失败
    * `BatchStatus.COMPLETED` - 任务成功结束
  * `startTime` - 任务开始时的系统时间，类型：`java.util.Data` 
  * `endTime` - 任务结束时的系统时间，类型：`java.util.Data` 
  * `exitStatus` - 任务的运行结果，包含返回给调用者的退出码
  * `lastUpdated` - 最近一次 Job Execution 被持久化的系统时间，类型：`java.util.Data` 
  * `executionContext` - 包含运行过程中所需要被持久化的用户数据
  * `failureExceptions` - 在任务执行过程中例外的列表 



### 3.3 Step

* 定义：
  * Step 表示作业中的一个完整步骤，一个 Job 可以由一个或多个 Step 组成。
  * Step 包含一个实际运行的批处理任务中的所有必需的信息，Step 的负责程度通常是由业务决定的
* Step 与 Job 的关系
  
  * 一个 Job 可以拥有一到多个 Step；
  
  * 一个 Step 可以有一到多个 Step Execution(当一个 Step 执行失败，下次重新执行该任务时，会为该 Step 重新生成一个 Step Execution)；
  
  * 一个Job Execution 可以有一到多个 Step Execution(当一个 Job 由多个 Step 组成时，每个 Step 执行都会生成一个新的 Step Execution，则一个 Job Execution 会拥有多个 Step Execution)
  
    ![step-job-relation-1](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.3-step-job-relation-1.png>)

* Step 中可以配置 tasklet、partition、job、flow

  ![step-schema-1](<https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.3-step-schema-1.png>)



* Step 的主要属性

  ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.3-step-properties-1.png)



#### 3.3.1 Step Execution

* Step Execution 是 Step 执行的句柄
* StepExecution 的属性
  * `status` - 表示执行状态
    * BatchStatus.STARTED - 运行时
    * BatchStatus.FAILED - 执行失败
    * BatchStatus.COMPLETED - 入伍成功结束
  * `startTime` - 任务步开始时的系统时间，类型为 `java.util.data` 
  * `endTime` - 任务步结束时的系统时间，类型为 `java.util.data` 
  * `exitStatus` - 任务步的运行结果，包含返回给调用者的退出代码
  * `executionContext` - 在执行过程中任何需要持久化的用户数据
  * `readCount` - 成功读取的记录数
  * `writeCount` - 成功写入的记录数
  * `commitCount` - 执行过程的事务中成功提交次数
  * `rollbackCount` - 执行过程的事务中回滚次数
  * `readSkipCount` - 读取失败而略过的记录数
  * `processSkipCount` - 处理失败而略过的记录数
  * `filterCount` - 被 ItemProcessor 过滤的记录数
  * `writerSkipCount` - 写入失败而略过的记录数



### 3.4 Execution Context

* Execution Context 是 Spring Batch 框架提供的持久化与控制的 key/value 对，能够让开发者在 Step Execution 或 Job Execution 中保存需要进行持久化的状态
* 任务执行失败时，框架会每次 commit 后记录当前的提交记录数已经读的记录数，这样当 Step 发生错误时，下次重启 Job 会根据 Execution Context 中的数据恢复状态，保证继续从上次失败的点重新执行
* Execution Context 分为两类
  * Job Execution 的上下文
  * Step Execution 的上下文
  * 两者之间的关系
    * 一个 Job Execution 对应一个 Job Execution 上下文
    * 每个 Step Execution 对应一个 Job Execution 上下文
    * 同一个 Job 中的 Step Execution 共用 Job Execution 的上下文；因此如果同一个 Job 的不同 Step 间需要共享数据时，则可以通过 Job Execution 的上下文来共享数据



### 3.5 Job Repository

* Spring Batch 提供 Job Repository 来存储 Job 执行期的元数据(这里的元数据是指 Job Instance、Job Execution、Job Parameters、Step Execution、Execution Context 等数据)，并提供两种默认实现：
  * 一种是存放在内存中
  * 一种是将元数据存放在数据库中
    * 通过将元数据存放在数据库中，可以随时监控批处理 Job 的执行状态，查看 Job 执行结果是成功还是失败
    * 使得在 Job 失败得情况下重新启动 Job 成为可能
* Spring Batch 提供了可执行得数据库脚本和 JobRepository 得 CRUD 实现类
  * 数据库脚本位置 
    *  `spring-batch-core-2.2.1.RELEASE.jar!\org\springframework\batch\core\xxx.sql` 
    * 支持数据库：DB2，Derby，H2，HSQLDB，MySQL，Oracle，PostgreSQL，SQLServer，Sybase
  * 默认实现对元数据 CRUD 的操作类
    * `org.springframework.batch.core.repository.support.SimpleJobRepository` 



#### 3.5.1 Job Repository Schema

* Job Repository Schema

  ![job-repository-schema-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.5.1-job-repository-schema-1.png)

* Job Repository Properties

  ![job-repository-properties-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.5.1-job-repository-properties-1.png)



#### 3.5.2 配置 Memory Job Repository

* 默认实现 - `org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean`

* 通常在测试阶段可以使用基于内存的方式



#### 3.5.3 配置 DB Job Repository

* 使用数据库的仓库时，需要首先根据 Spring Batch 提供的数据库脚本完成数据库的初始化
  * 数据库脚本位置：`spring-batch-core-2.2.1.RELEASE.jar!\org\springframework\batch\core\xxx.sql` 

* xml 数据库配置
  * `isolation-level-for-create` 定义创建 Job Execution 时候的事务隔离级别，避免多个 Job Execution 执行一个 Job Instance
  * `table-prefix` 定义使用的数据库表的前缀为 `BATCH_`
  * `max-varchar-length` 定义 varchar 的最大长度



#### 3.5.4 数据库 Schema

* spring batch 进行元数据管理共有九张表，其中有三个(后缀为SEQ)是用来分配主键的：
  * `BATCH_JOB_INSTANCE` 
    * 作业实例表，用于存放 Job 的实例信息
  * `BATCH_JOB_EXECUTION_PATAMS` 
    - 作业参数表，用于存放每个 Job 执行时候的参数信息，该参数实际上是对应 Job 实例的
  * `BATCH_JOB_EXECUTION` 
    * 作业执行器表，用于存放当前作业的执行信息，比如创建时间，执行开始时间，执行结束时间，执行的那个Job 实例，执行状态等
  * `BATCH_JOB_EXECUTION_CONTEXT` 
    * 作业执行上下文表，用于存放作业执行器上下文信息
  * `BATCH_STEP_EXECUTION` 
    * 作业步执行器表，用于存放每个 Step 执行器的信息，比如作业步开始执行时间，执行完成时间，执行状态，读/写次数，跳过次数等信息
  * `BATCH_STEP_EXECUTION_CONTEXT` 
    * 作业步上下文表，用于存放每个作业步上下文的信息
  * `BATCH_JOB_SEQ` 
    * 作业序列表，用于给表 `BATCH_JOB_INSTANCE` 和 `BATCH_JOB_EXECUTION_PATAMS` 提供主键
  * `BATCH_JOB_EXECUTION_SEQ` 
    * 作业执行器序列表，用于给表 `BATCH_JOB_EXECUTION` 和 `BATCH_JOB_EXECUTION_CONTEXT` 提供主键
  * `BATCH_STEP_EXECUTION_SEQ` 
    * 作业步序列表，用于给表 `BATCH_STEP_EXECUTION` 和 `BATCH_STEP_EXECUTION_CONTEXT` 提供主键



### 3.6 Job Launcher

* 概念
  * Job Launcher(作业调度器)是 Spring Batch 基础设施层提供的运行 Job 的能力
  * 通过给定的 Job 名称和作业参数 Job Parameters，可以通过 Job Launcher 执行 Job
  * 通过 Job Launcher 可以在 Java 程序中调用批处理任务，也可以在通过命令行或者其他框架(如定时调度框架 Quartz)中调用批处理任务
  * 批处理应用可以通过 Job Launcher 和外部系统交互，通常情况下外部系统可以同步也可以异步调用批处理应用
  * 批处理应用本身可以访问外部的资源，如数据库、文件、消息队列等
  * spring batch 提供了 Job Launcher 的简单实现
    * `org.springframework.batch.core.launch.support.SimpleJobLauncher` 



### 3.7 ItemReader

* ItemReader 是 Step 中对资源的读处理，Spring Batch 提供了多种类型的读实现：

  * 文本文件
  * XML 文件
  * 数据库
  * JMS 消息

* spring batch 提供的 ItemReader 组件

  ![item-reader-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.7-item-reader-1.png)



### 3.8 ItemProcessor

* ItemProcessor 阶段表示对读数据的数据进行处理，开发者可以实现自己的业务操作来对数据进行处理

* spring batch 提供的处理组件

  ![item-processor-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.8-item-processor-1.png)



### 3.9 ItemWriter

* ItemWriter 是 Step 中对资源的写处理，Spring Batch 提供了多种类型的写实现，如：文本文件、XML 文件、DB等写的处理

* Spring Batch 提供的 ItemWriter 组件

  ![item-writer-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/3.9-item-writer-1.png)



## 4 配置作业 Job

* 一个 Job 由一个或多个 Step 组成，Step 有读、处理、写三部分操作组成；Job 运行期所有的数据通过 Job Repository 进行持久化，同时通过 JobLauncher 负责调度 Job 作业

  ![spring-batch-structure-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4-spring-batch-structure-1.png)

* 为了简化 Spring Batch 的配置，还提供了独立的 XML 元素和属性配置



### 4.1 基本配置

* Job 主要属性

  * `id` - 作业唯一标识

  * `job-repository` - 定义作业仓库

  * `incrementer` - 作业参数递增器

  * `restartable` - 作业是否可以重启

  * `parent` - 指定该作业的父作业

  * `abstract` - 定义作业是否抽象的

  * job xml properties

    ![](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.1-job-xml-properties-1.png)

  * job xml properties schema

    ![job-xml-properties-schema-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.1-job-xml-properties-schema-1.png)

* Job 主要子元素

  * `step` - 定义作业步

  * `split` - 定义并行作业步

  * `flow` - 定义作业流

  * `decision` - 定义作业步执行的条件判断器，用于判断后续执行的作业步

  * `listeners` - 定义作业拦截器

  * `validator` - 定义作业参数校验器

  * job xml sub-properties 

    ![job-xml-subproperties-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.1-job-xml-subproperties-1.png)

  * job xml sub-properties schema

    ![job-xml-subproperties-schema-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.1-job-xml-subproperties-schema-1.png)



#### 4.1.1 重启 Job

* 通过属性 `restartable` 可以定义 Job 是否可以重启，默认情况下 Job 是可以重启的，需要注意的是，即使配置了 Job 可以重新启动，仍需要保证 Job Instance 的状态一定不能为 `COMPLETED` 状态



#### 4.1.2 Job 拦截器

* spring batch 在 Job 执行阶段提供了拦截器，使得在 Job 执行前后能够加入自定义的业务逻辑处理

* Job 执行阶段拦截器需要实现接口

  * `org.springframework.batch.core.JobExecutionListener` 

  * Spring Batch 也提供了默认的 `JobExecutionListener` 实现

    * `org.springframework.batch.core.listener.CompositeJobExecutionListener` 

    * `org.springframework.batch.core.listener.JobExecutionListenerSupport` 

      ![job-listeners-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.1.2-job-listeners-1.png)

* 拦截器异常
  * 拦截器方法如果抛出异常会影响 Job 的正常执行，所以在执行自定义的拦截器时，需要考虑对拦截器发生的异常做处理，避免影响业务
  * 如果拦截器发生异常，会导致 Job 执行的状态为 `FAILED` 
  
* 执行顺序
  * 在配置文件中可以配置多个 listener，拦截器之间的执行顺序安装 listener 定义的顺序执行，即配置文件中定义的顺序。
  * before 方法按照 listener 定义的顺序执行，after 方法按照相反的顺序执行
  
* Java Config

  * Spring Batch 提供了 Annotation 机制，可以不实现 `JobExecutionListener` 接口，直接通过注解定义拦截器
    * `@BeforeJob` - 声明作业执行前的操作
    * `@AfterJob` - 声明作业执行后的操作



#### 4.1.3 Job Parameters 校验

* spring batch 提供了 Job 作业参数的校验功能
* 开发者可以自定义实现参数校验器
  * 需要实现接口 - `org.springframework.batch.core.JobParametersValidator` 
* `JobParametersValidator` 默认实现
  * `org.springframework.batch.core.job.DefaultJobParametersValidator` 
    * 参数校验组合模式，支持一组参数校验
  * `org.springframework.batch.core.job.CompositeJobParametersValidator` 
    * 参数校验默认实现，支持必须输入的参数和可以选择输入的参数
* 在执行 Job 时，可以通过 `org.springframework.batch.core.JobParametersBuilder` 构造作业参数



#### 4.1.4 Job 抽象与继承

* spring batch 支持抽象的 Job 定义和 Job 的继承特性。通过定义抽象的 Job 可以将 Job 的共性进行抽取，定义父类 Job；然后具体的 Job 可以继承父类的特性，并定义自己的属性。通过 Job  的属性 abstract 可以定义抽象的 Job，通过属性 parent 可以指定当前 Job 的父 Job

* 抽象 Job

  ![job-abstract-xml-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.1.4-job-abstract-xml-1.png)

* 继承 Job

  ![job-abstract-xml-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.1.4-job-abstract-xml-1.png)

* merge 列表
  * 如果父子类中均定义了拦截器，则可以通过设置 merge 属性为 true 对拦截器列表合并；如果设置 merge 属性为 false，则子类中定义的拦截器直接覆盖掉父类中定义的拦截器
  * 通过抽象和继承的特性可以方便的对 Job 进行共性抽象和 Job 的复用



### 4.2 高级特性

#### 4.2.1 Step Scope

* Spring Batch 中的 Scope，将 Spring Bean 定义为 Step Scope，支持 Spring Bean 在 step 开始的时候初始化，在 Step 结束的时候销毁 Spring Bean，将 Spring Bean 的声明周期与 Step 绑定
* 通过属性 `Scope="Step"` 来定义 bean 的生命周期并与 Step 绑定；通过使用 Step Scope， 可以支持属性的 Late Binding(属性后绑定)能力

#### 4.2.2 属性 Late Binding

* Spring Batch 通过特定的表达式支持为 Job 或 Step 关联的实体使用后绑定技术。
* 在 Step Scope 中 Spring Batch 提供的可以使用的实体包括
  *  `jobParameters` - 作业参数
  * `jobExecutionContext` - 当前 Job 的执行器上下文
  * `stepExecutionContext` - 当前 Step 的执行器上下文
* Late Binding 可以避免在配置文件中使用硬编码的方式指定读取的配置文件，可以直接在运行期使用 Job 传入的参数



### 4.3 运行 Job

* 执行 Job 的接口 API

  * API 间的联系

    ![job-run-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.3-job-run-1.png)

  * API详解

    ![job-run-2](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.3-job-run-2.png)



#### 4.3.1 调度作业

* JobLauncher 支持对作业的同步、异步两种调用模式



##### 4.3.1.1 同步异步

* 默认情况下，JobLauncher 的 run 操作通过同步方式调用 Job，任何调用 Job 的客户端需要等待 Job 的执行结果返回后才能结束

  ![job-run-sync](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.3.1.1-job-run-sync.png)

* 异步调用 JobLauncher 只需要增加属性 taskExecutor，该属性标识当前执行的线程池

  ![job-run-async](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.3.1.1-job-run-async.png)

##### 4.3.1.2 Job 与外界系统

* 在实际的 Job 使用场景中，标准 Web 应用、定时任务调度器、命令行等都可能触发不同的 Job操作

  ![spring-batch-outside-relation-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.3.1.2-spring-batch-outside-relation-1.png)



#### 4.3.2 命令行执行

* Spring Batch 命令行执行类：

  * `org.springframework.batch.core.launch.support.CommandLineJobRunner` 

  * 通过命令行方式执行 Job 的入口，在一个单独的 JVM 中执行批处理作业；可以手动触发，也可以定义自动任务通过脚本的方式执行批处理作业

    ![job-run-commandline-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.3.2-job-run-commandline-1.png)



#### 4.3.3 与定时任务集成

* spring batch 框架和 spring scheduler 间的关系

  ![spring-batch-scheduler-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.3.3-spring-batch-scheduler-1.png)

* spring scheduler 使用
  1. 定义一个 scheduler，该 scheduler 提供执行定时任务的线程
  2. 定义需要定时操作的方法和调度周期



4.3.4 与 Web 应用集成

* spring batch 基于 spring 开发，可以方便地内嵌在 Web 应用中使用，这样批处理可以通过 HTTP 协议进行远程访问
* 同样可以在 Web 应用中内嵌定时任务处理框架，方便在 Web 应用内部通过定时框架调用 Spring Batch 中定义地 Job

* web 应用、spring batch、spring scheduler 关系

  ![springbatch-web-scheduler-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/4.3.4-springbatch-web-scheduler-1.png)



#### 4.3.5 停止 Job

##### 4.3.5.1 JobOperator 停止 Job

* Job 执行期间通过 `org.springframework.batch.core.launch.JobOperator#stop` 方法停止正在运行地 Job
  * stop 方法返回 Boolean 值，表示当前终止消息是否成功发送，至于什么时候终止消息，则由 spring batch 框架本身决定
* 可以利用 Spring 的特性，将 Bean 暴露为 JMX 服务，同时根据 JConsole 提供的 执行 JMX 服务的入口，一旦 JobOperator 定义为 JMX 服务后，就可以通过 JConsole 的方式操作对应的Stop操作 



##### 4.3.5.2 业务停止 Job

* 通过 `StepExecution#setTerminateOnly()` 操作可以终止正在运行的任务，即在作业步执行期间终止任务
* `StepExecution#setTerminateOnly()` 会发送一个停止消息给框架，一旦 spring batch 框架接收到停止消息，并且框架获取作业的控制权，spring batch 框架会自动终止作业
* 在通过业务操作终止任务操作时，不要在读、处理、写的业务逻辑中终止任务，尽量保证业务操作的完整性



## 5 配置作业步 Step

* Step 作用域最大，然后是 Tasklet，接下来为 Chunk，在每个 Chunk 中可以定义 read、process、write

  ![step-relation-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/5-step-relation-1.png)



### 5.1  配置 Step

* Step 主要属性和元素定义

  ![step-xml-properties-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/5-step-xml-properties-1.png)

  ![step-xml-subproperties-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/5-step-xml-subproperties-1.png)



#### 5.1.1 Step 抽象与继承



#### 5.1.2 Step 执行拦截器

* spring batch 在 Step 执行阶段提供了拦截器，使得在 Step 执行前后能够加入自定义的业务逻辑
* Step 执行阶段拦截器接口：
  * `org.springframework.batch.core.StepExecutionListener` 



### 5.2 配置 Tasklet

* tasklet 元素定义入伍的具体执行逻辑，执行逻辑可以自定义实现，也可以使用 spring batch 的 Chunk 操作：读、处理、写。通过tasklet可以定义事务、处理线程、启动控制、回滚控制、拦截器等

* tasklet属性

  ![tasklet-xml-properties-1](https://raw.githubusercontent.com/jinminer/docs/master/spring-batch/spring-batch-in-action/base/5.2-tasklet-xml-properties-1.png)

* 









