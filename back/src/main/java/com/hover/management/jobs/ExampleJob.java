package com.hover.management.jobs;

import com.hover.common.util.holder.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: zhaihx
 * @Description: 定时任务样例
 **/
public class ExampleJob extends AbstractJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThreadPoolExecutor threadPool;

    @Override
    protected boolean proDO()
    {
        ThreadPoolTaskExecutor taskExecutor =
                (ThreadPoolTaskExecutor) SpringContextHolder.getBean("exampleThreadPool");
        try
        {
            threadPool = taskExecutor.getThreadPoolExecutor();
        }
        catch (Exception e)
        {
            logger.error("样例异常:", e);
            return false;
        }
        return true;
    }

    @Override
    protected void doTask() {

        threadPool.execute(() ->{
            try {
                doExampleRegularly();
            } catch (Exception e) {

                logger.error("样例异常,", e);
            }
        });
    }

    /**
    * @Author: zhaihx
    * @Description: 投放
    * @Date: 18:06 2019/5/28
    **/
    private void doExampleRegularly() {
        //扫描规则提醒
        System.out.println("样例成功");
    }
}
