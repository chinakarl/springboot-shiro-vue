package com.hover.management;

import com.hover.common.util.listeners.ApplicationHolderListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: zhx
 * @description: SpringBoot启动类
 * @date: 2018/10/24 11:55
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.hover"})
@MapperScan({"com.hover"})
//@ImportResource({"classpath:/dubbo/*.xml"})
public class MyApplication extends SpringBootServletInitializer {

    @Bean
    ApplicationHolderListener applicationHolderListener() {
        return new ApplicationHolderListener();
    }

    public static void main(String[] args) {
//        SpringApplication application = new SpringApplication(MyApplication.class);
//        application.setBannerMode(Banner.Mode.OFF);
//        application.run(args);
        new SpringApplicationBuilder(MyApplication.class).web(WebApplicationType.NONE).run(args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        // 注意这里要指向原先用main方法执行的Application启动类
//        return builder.sources(MyApplication.class);
//    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//        };
//    }

}
