package com.hover.common.util.holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/28
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Fields applicationContext : Spring上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     * @param context spring上下文
     */
    @Override
    public void setApplicationContext(final ApplicationContext context) {
        logger.debug("Injecting 'ApplicationContext' to SpringContextHolder:" + applicationContext);
        if (applicationContext != null) {
            throw new IllegalStateException("ApplicationContextHolder already holded 'applicationContext'.");
        }
        applicationContext = context;
    }

    /**
     * 获取静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("'applicationContext' property is null, ApplicationContextHolder not yet init.");
        }
        return applicationContext;
    }

    /**
     * @Title: getBean
     * @Description: 根据bean名称从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * @param beanName bean名称
     * @param <T> bean的类型
     * @return bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(final String beanName) {
        return (T) getApplicationContext().getBean(beanName);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public void cleanApplicationContext() {
        logger.debug("Cleaning 'ApplicationContext' in SpringContextHolder:" + applicationContext);
        applicationContext = null;
    }
}
