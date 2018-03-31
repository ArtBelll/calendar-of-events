package ru.korbit.ceadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.korbit.cecommon.dao.UserDao;
import ru.korbit.cecommon.domain.User;
import ru.korbit.cecommon.packet.RoleOfUser;
import ru.korbit.cecommon.utility.PasswordHelper;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class})
@Slf4j
@ComponentScan(value = {"ru.korbit.ceadmin", "ru.korbit.cecommon"})
@EnableScheduling
public class AdminApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AdminApp.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdminApp.class, args);
    }

    @Value(value = "classpath:redisson.json")
    private Resource redissonConfig;

    @Value(value = "classpath:superuser.json")
    private Resource superuserConfig;

    @Bean
    public RedissonClient redissonClient() throws IOException {
        val config = Config.fromJSON(redissonConfig.getInputStream());
        return Redisson.create(config);
    }

    @Autowired
    private UserDao userDao;

    @PostConstruct
    public void createSuperuser() throws IOException {
        val mapper = new ObjectMapper();
        val user = mapper.readValue(superuserConfig.getInputStream(), User.class);
        user.setPassword(PasswordHelper.hashPassword(user.getPassword()));

        val superuser = userDao.getByEmail(user.getEmail());

        if (!superuser.isPresent()) {
            user.getRoles().add(RoleOfUser.SUPERUSER);
            userDao.save(user);
        }
    }

    @Bean
    public WebMvcConfigurer crossConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}
