package com.ai.karl.job.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/24
 */
@Configuration
public class QuartzConfig {

    @Value("${quartz.datasource.driverClassName}")
    String quartzDataSourceDriver;

    @Value("${quartz.datasource.url}")
    String quartzDataSourceURL;

    @Value("${quartz.datasource.username}")
    String quartzDataSourceUsername;

    @Value("${quartz.datasource.password}")
    String quartzDataSourcePassword;

    /**
     * 设置quartz属性
     * @throws IOException
     */
    public Properties quartzProperties() throws IOException {
        Properties prop = new Properties();

        /**
         *  Configure Main Scheduler Properties 调度器属性
         */
        //在集群中每个实例都必须有一个唯一的instanceId，但是应该有一个相同的instanceName【默认“QuartzScheduler”】【非必须】
        prop.put("quartz.scheduler.instanceName", "ServerScheduler");
        //Scheduler实例ID，全局唯一，【默认值NON_CLUSTERED】，或者可以使用“SYS_PROP”通过系统属性设置id。【非必须】
        //只有在”org.quartz.scheduler.instanceId”设置为”AUTO”的时候才使用该属性设置。
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        //建议设置为“org.terracotta.quartz.skipUpdateCheck=true”不会在程序运行中还去检查quartz是否有版本更新。【默认false】【非必须】
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");

        /**
         * Configure JobStore 配置数据存储的方式
         */
        // 最大能忍受的触发超时时间(触发器被认定为“misfired”之前)，如果超过则认为“失误”【默认60秒】
        prop.put("org.quartz.jobStore.misfireThreshold", "60000");
        //所有的quartz数据例如job和Trigger的细节信息被保存在内存或数据库中,有两种实现：JobStoreTX(自己管理事务)和JobStoreCMT（application server管理事务，即全局事务JTA）
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        //类似于Hibernate的dialect，用于处理DB之间的差异，StdJDBCDelegate能满足大部分的DB
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        //配置数据源的名称，在后面配置数据源的时候要用到，例如org.quartz.dataSource.clusterDS.driver = com.mysql.jdbc.Driver
        prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
        //数据表前缀
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        //是否集群、负载均衡、容错，如果应用在集群中设置为false会出错
        prop.put("org.quartz.jobStore.isClustered", "true");
        //检入到数据库中的频率(毫秒)。检查是否其他的实例到了应当检入的时候未检入这能指出一个失败的实例，且当前Scheduler会以此来接管执行失败并可恢复的Job通过检入操作，Scheduler也会更新自身的状态记录
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000");

        /**
         * Configure ThreadPool 线程池属性
         */
        //线程池的实现类（定长线程池，几乎可满足所有用户的需求）【默认null】【必须】
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        //指定线程数，至少为1（无默认值）(一般设置为1-100直接的整数合适)【默认-1】【必须】
        prop.put("org.quartz.threadPool.threadCount", "10");

        /**
         * Configure Datasources 数据源
         */
        prop.put("org.quartz.dataSource.quartzDataSource.driver", quartzDataSourceDriver);
        prop.put("org.quartz.dataSource.quartzDataSource.URL", quartzDataSourceURL);
        prop.put("org.quartz.dataSource.quartzDataSource.user", quartzDataSourceUsername);
        prop.put("org.quartz.dataSource.quartzDataSource.password", quartzDataSourcePassword);
        //数据库最大连接数（如果Scheduler很忙，比如执行的任务与线程池的数量差不多相同，那就需要配置DataSource的连接数量为线程池数量+1）
        prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "20");
        return prop;
    }
}
