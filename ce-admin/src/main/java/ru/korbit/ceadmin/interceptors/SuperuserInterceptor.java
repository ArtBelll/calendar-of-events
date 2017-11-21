package ru.korbit.ceadmin.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.korbit.ceadmin.controllers.SessionController;
import ru.korbit.cecommon.dao.UserDao;
import ru.korbit.cecommon.exeptions.Forbidden;
import ru.korbit.cecommon.packet.RoleOfUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SuperuserInterceptor extends SessionController implements HandlerInterceptor {

    public SuperuserInterceptor(UserDao userDao) {
        super(userDao);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!isRole(request, RoleOfUser.SUPERUSER)) {
            throw new Forbidden("Permission denied");
        }
        return true;
    }
}
