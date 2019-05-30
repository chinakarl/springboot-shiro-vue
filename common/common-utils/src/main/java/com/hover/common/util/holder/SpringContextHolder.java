package com.hover.common.util.holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/28
 */
@Component
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware,DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

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
    @SuppressWarnings("unchecked")
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
     * 从指定的配置文件中加载Bean到ApplicationContext
     * @param configLocationString 指定配置文件路径
     */
    public static void loadBean(final String configLocationString) {
        final ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) getApplicationContext();
        final XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) ctx.getBeanFactory());
        beanDefinitionReader.setResourceLoader(getApplicationContext());
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext()));
        try {
            final String[] configLocations = new String[] { configLocationString };
            for (final String configLocation : configLocations) {
                beanDefinitionReader.loadBeanDefinitions(getApplicationContext().getResources(configLocation));
            }
        } catch (final BeansException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void cleanApplicationContext() {
        logger.debug("Cleaning 'ApplicationContext' in SpringContextHolder:" + applicationContext);
        applicationContext = null;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() {
        cleanApplicationContext();
    }
}
