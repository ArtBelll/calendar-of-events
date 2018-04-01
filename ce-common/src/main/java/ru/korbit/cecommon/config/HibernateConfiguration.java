package ru.korbit.cecommon.config;

import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
@EntityScan("ru.korbit.cecommon.domain")
public class HibernateConfiguration {

    @Bean
    public HibernateTransactionManager getTransactionManager(EntityManagerFactory entityManagerFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(entityManagerFactory.unwrap(SessionFactory.class));
        return txManager;
    }
}
