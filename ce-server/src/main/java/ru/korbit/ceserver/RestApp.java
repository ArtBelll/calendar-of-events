package ru.korbit.ceserver;

import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;

/**
 * Created by Artur Belogur on 02.11.17.
 */
@SpringBootApplication(
        scanBasePackages = {"ru.korbit.ceserver", "ru.korbit.cecommon"})
@Slf4j
public class RestApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RestApp.class, args);
    }

    @Value(value = "classpath:redisson.json")
    private Resource redissonConfig;

    @Bean
    public RedissonClient redissonClient() throws IOException {
        val config = Config.fromJSON(redissonConfig.getInputStream());
        return Redisson.create(config);
    }

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
        b.modulesToInstall(new GuavaModule(), new Hibernate5Module());
        return b;
    }
}
