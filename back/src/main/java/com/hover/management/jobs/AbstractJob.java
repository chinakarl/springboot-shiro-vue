package com.hover.management.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: zhaihx
 * @Description:
 * @Date: Created in 17:53 2018/8/16
 * @ModifiedBy:
 **/
public abstract class AbstractJob implements Job {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        ConfigurableApplicationContext cac = (ConfigurableApplicationContext) context.getJobDetail().getJobDataMap().get("ConfigurableApplicationContext");
        boolean pro = proDO();
        logger.debug(getClass().getSimpleName() + ":前置任务完成...result:" + pro);
        if(pro) {
            doTask();
            logger.debug(getClass().getSimpleName() + ":执行任务完成...");
            afterDO();
            logger.debug(getClass().getSimpleName() + ":后置任务完成...");
        }
    }


    protected abstract void doTask();

    /**
     * 定时任务前置工作，返回true则开始执行任务，返回false则终止本次任务。
     * 基类中空实现，默认返回true，如子类业务需要，重写该方法即可
     */
    protected boolean proDO() {
        return true;
    }

    /**
     * 定时任务后置工作，处理任务执行后需要做的扫尾工作。
     * 基类中空实现，如子类业务需要，重写该方法即可
     */
    protected void afterDO() {

    }
}
