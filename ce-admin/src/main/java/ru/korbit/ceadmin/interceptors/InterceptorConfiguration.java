package ru.korbit.ceadmin.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.korbit.cecommon.dao.UserDao;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final UserDao userDao;

    @Autowired
    public InterceptorConfiguration(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SuperuserInterceptor(userDao)).addPathPatterns("/superuser/**");
        registry.addInterceptor(new AdminInterceptor(userDao)).addPathPatterns("/admin/**");
    }
}
