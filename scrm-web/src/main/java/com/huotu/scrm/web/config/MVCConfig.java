package com.huotu.scrm.web.config;

import com.huotu.scrm.common.SysConstant;
import com.huotu.scrm.service.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;

/**
 * Created by helloztt on 2017-06-27.
 */
@Configuration
@EnableWebMvc
@ComponentScan({
        "com.huotu.scrm.web.controller"
})
@Import({MVCConfig.ThymeleafConfig.class, ServiceConfig.class})
public class MVCConfig extends WebMvcConfigurerAdapter {
//    @Autowired
//    private HeaderInterceptor headerInterceptor;
//    @Autowired
//    private CustomerInterceptor customerInterceptor;
//    @Autowired
//    private UserInterceptor userInterceptor;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private ThymeleafViewResolver jsViewResolver;
    //只是为了初始化
    @Autowired
    private SysConstant sysConstant;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
//        registry.addInterceptor(headerInterceptor).addPathPatterns("/mall/**");
//        registry.addInterceptor(customerInterceptor).addPathPatterns("/mall/**");
//        registry.addInterceptor(userInterceptor).addPathPatterns("/site/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //移除原来的Jackson Converter 加入新的MessageConverter
        HttpMessageConverter mappingJackson2XmlHttpMessageConverter;
        HttpMessageConverter mappingJackson2HttpMessageConverter = null;
        if (converters.stream().anyMatch(converter -> converter instanceof MappingJackson2HttpMessageConverter)) {
            mappingJackson2HttpMessageConverter = converters.stream()
                    .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                    .findAny().get();
            converters.remove(mappingJackson2HttpMessageConverter);
        }
        if (converters.stream().anyMatch(converter -> converter instanceof MappingJackson2XmlHttpMessageConverter)) {
            mappingJackson2XmlHttpMessageConverter = converters.stream()
                    .filter(converter -> converter instanceof MappingJackson2XmlHttpMessageConverter)
                    .findAny().get();
            converters.remove(mappingJackson2XmlHttpMessageConverter);
        }
        if (mappingJackson2HttpMessageConverter != null && !converters.contains(mappingJackson2HttpMessageConverter))
            converters.add(mappingJackson2HttpMessageConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/*.txt","/resources/**/*", "/image/**/*", "/js/**/*", "/**/*.html")
                .addResourceLocations("/resources/","/resources/", "/image/", "/js/", "/");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(jsViewResolver);
        registry.viewResolver(thymeleafViewResolver);
    }

    @Configuration
    public static class ThymeleafConfig {
        @Autowired
        private ApplicationContext applicationContext;
        @Autowired
        private Environment env;

        @Bean
        public SpringResourceTemplateResolver templateResolver() {
            // SpringResourceTemplateResolver automatically integrates with Spring's own
            // resource resolution infrastructure, which is highly recommended.
            SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
            templateResolver.setApplicationContext(this.applicationContext);
            templateResolver.setPrefix("/views/");
            templateResolver.setSuffix(".html");
            // HTML is the default value, added here for the sake of clarity.
            templateResolver.setTemplateMode(TemplateMode.HTML);
            templateResolver.setCharacterEncoding("UTF-8");
            // Template cache is true by default. Set to false if you want
            // templates to be automatically updated when modified.
            if (env.acceptsProfiles("!container")) {
                templateResolver.setCacheable(false);
            } else {
                templateResolver.setCacheable(true);
            }
            return templateResolver;
        }

        @Bean
        public SpringTemplateEngine templateEngine() {
            // SpringTemplateEngine automatically applies SpringStandardDialect and
            // enables Spring's own MessageSource message resolution mechanisms.
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(templateResolver());
            templateEngine.addDialect(new Java8TimeDialect());
            return templateEngine;
        }

        @Bean(name = "thymeleafViewResolver")
        public ThymeleafViewResolver viewResolver() {
            ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
            viewResolver.setTemplateEngine(templateEngine());
            // NOTE 'order' and 'viewNames' are optional
            viewResolver.setOrder(2);
            viewResolver.setCharacterEncoding("UTF-8");
            viewResolver.setContentType("text/html;charset=utf-8");
            return viewResolver;
        }

        @Bean(name = "jsViewResolver")
        public ThymeleafViewResolver jsViewResolver(){
            SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
            templateResolver.setApplicationContext(this.applicationContext);
            templateResolver.setPrefix("/views/");
            templateResolver.setTemplateMode(TemplateMode.JAVASCRIPT);
            templateResolver.setCharacterEncoding("UTF-8");
            if (env.acceptsProfiles("!container")) {
                templateResolver.setCacheable(false);
            } else {
                templateResolver.setCacheable(true);
            }

            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);
            templateEngine.addDialect(new Java8TimeDialect());

            ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
            viewResolver.setTemplateEngine(templateEngine);
            viewResolver.setOrder(1);
            viewResolver.setViewNames(new String[]{"*.js", "*.JS"});
            viewResolver.setCharacterEncoding("UTF-8");
            viewResolver.setContentType("application/javascript;charset=utf-8");
            return viewResolver;
        }
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

}
