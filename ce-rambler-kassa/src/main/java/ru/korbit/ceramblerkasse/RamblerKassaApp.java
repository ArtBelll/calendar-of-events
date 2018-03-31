package ru.korbit.ceramblerkasse;

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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import ru.korbit.cecommon.config.Constants;
import ru.korbit.cecommon.services.RamblerKassaService;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@SpringBootApplication(scanBasePackages = {"ru.korbit.ceramblerkasse", "ru.korbit.cecommon"},
        exclude = {HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
@EnableAsync
@Slf4j
public class RamblerKassaApp {

    public static void main(final String[] args) {
        if (args.length > 0 && args[0].equals("dev")) {
            Environment.host = Environment.PROXY;
        }

        val app = SpringApplication.run(RamblerKassaApp.class, args);
        val redissonClient = app.getBean(RedissonClient.class);
        val ramblerKasseLoader = app.getBean(RamblerKasseLoader.class);
        val service = redissonClient.getRemoteService(Constants.QUEUE_NAME);
        service.register(RamblerKassaService.class, ramblerKasseLoader, 2);
    }

    @Value(value = "classpath:redisson.json")
    private Resource redissonConfig;

    @Bean
    public RedissonClient redissonClient() throws IOException {
        val config = Config.fromJSON(redissonConfig.getInputStream());
        return Redisson.create(config);
    }

    @Bean
    public Executor asyncExecutor() {
        val executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.initialize();
        return executor;
    }
}
