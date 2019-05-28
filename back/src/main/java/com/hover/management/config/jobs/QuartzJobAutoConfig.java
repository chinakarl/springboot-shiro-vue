package com.hover.management.config.jobs;

import com.hover.common.job.model.QuartzTaskInfo;
import com.hover.common.job.service.QuartzScheduleService;
import com.hover.management.jobs.ExampleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: zhaihx
 * @Description:
 * @Date: Created in 16:58 2018/8/20
 * @ModifiedBy:
 **/
@Configuration
public class QuartzJobAutoConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuartzScheduleService quartzScheduleService;

    @Bean
    public Object jobDetailGenerates() {
        // 新建Job，需要new一个QuartzTaskInfo对象，将要初始化的job信息设置进去，必填信息包含以下三个：
        // 1. CronExpression填写Cron表达式设置触发规则
        // 2. JobName设置Job的名称
        // 3. JobGroup设置Job所在分组，非必填项，建议填写方便区分Job类型

        // 定期投放商品 TaskInfo
        QuartzTaskInfo quartzTaskInfo = new QuartzTaskInfo();
        //根据活动配置的时间动态修改,默认每隔12小时执行一次
        quartzTaskInfo.setCronExpression("0 0/1 0 * * ?");
        //job 名称
        quartzTaskInfo.setJobName(ExampleJob.class.getName());
        //job组名称
        quartzTaskInfo.setJobGroup("exampleJobGroup");
        initJobs(quartzTaskInfo);
        return new Object();
    }

    /**
    * @Author: zhaihx
    * @Description: 初始化job
    * @Date: 13:51 2018/8/21
     * @param taskInfo
    **/
    private void initJobs(QuartzTaskInfo taskInfo) {
        try {
            //存在job更新，不存在新增
            if (quartzScheduleService.checkExists(taskInfo.getJobName(), taskInfo.getJobGroup())) {
                quartzScheduleService.edit(taskInfo);
            } else {
                quartzScheduleService.addJob(taskInfo);
            }
            logger.info("=======自动初始job成功：{}============", taskInfo.getJobName());
        } catch (Exception e) {
            logger.error("initJobs failed", e);
        }
    }

    /**
    * @Author: zhaihx
    * @Description: 创建线程池的bean
    * @Date: 13:50 2019/5/28
    **/
    @Bean(name = "exampleThreadPool")
    public ThreadPoolTaskExecutor getAwardThreadTask()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数：线程池维护线程的最少数量
        executor.setCorePoolSize(30);
        // 最大线程数
        executor.setMaxPoolSize(100);
        // 队列大小
        executor.setQueueCapacity(1000);
        // 空闲线程回收时间间隔
        executor.setKeepAliveSeconds(300);
        // 线程名称前缀
        executor.setThreadNamePrefix("exampleThread");
        // 对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
