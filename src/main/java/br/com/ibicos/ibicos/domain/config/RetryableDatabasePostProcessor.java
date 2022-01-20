package br.com.ibicos.ibicos.domain.config;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.ibicos.ibicos.domain.repository.RetryableDataSource;
import groovy.util.logging.Slf4j;

@Slf4j
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class RetryableDatabasePostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {            
            return new RetryableDataSource( (DataSource) bean);
        }
        
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
    
    
}
