package ru.korbit.celoader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@SpringBootApplication(scanBasePackages = {"ru.korbit.celoader", "ru.korbit.cecommon"},
        exclude = {HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
@Slf4j
public class LoaderApp {

    public static void main(final String[] args) {
        val app = SpringApplication.run(LoaderApp.class, args);
        app.getBean(Schedule.class).run();
    }

    @Value(value = "classpath:redisson.json")
    private Resource redissonConfig;

    @Value(value = "classpath:hibernate.cfg.xml")
    private Resource hibernateProperties;

    @Bean
    public RedissonClient redissonClient() throws IOException {
        val config = Config.fromJSON(redissonConfig.getInputStream());
        return Redisson.create(config);
    }

    @PostConstruct
    public void clearExecutor() throws Exception {
        redissonClient().getKeys().deleteByPattern("*" + LoaderConstants.EXECUTOR_NAME + "*");
        log.info("Remove keys with pattern *{}*", LoaderConstants.EXECUTOR_NAME);
    }
}
