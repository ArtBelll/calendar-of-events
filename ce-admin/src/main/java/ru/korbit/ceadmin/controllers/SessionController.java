package ru.korbit.ceadmin.controllers;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.korbit.cecommon.dao.UserDao;
import ru.korbit.cecommon.domain.User;
import ru.korbit.cecommon.exeptions.ResourceNotFoundException;
import ru.korbit.cecommon.exeptions.UnAuthorized;
import ru.korbit.cecommon.packet.RoleOfUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SessionController {

    private final static String KEY = "5do1idc92ujdllichl2f0yrtg1h0b06msft2cs6fpyjcs4so9ppa9np15s2itexn40fjk3h90k6frautj5y0ir9ujdldnm2lk30p8keeay0zp29mhl0zf8c0scyc8to9dvcyvib1pvwshmnvwf9ua4usuu6kb8kiztxvgea1dnox542yxv1i0t3lgvbygm94i8s7gf1oby4bwk1miwrj28t3zcyydmlwbgrw3ygwndv5aqqx1qpcerc2vn237i4rz8mosf6y29cf2fwfxdv3o58jvzqtpr8zggbnxq0v0c8l9yyg0zctm4n40aidu0rgromk05g1g2ypp22xm4ucdvlbmgvslpynm7tsecllv5cj0g3b0vvirijgyubypvtrlj9rujzwyrvs1uj4";
    private final static String K64 = TextCodec.BASE64.encode(KEY);

    private static final String TOKEN_HEADER = "X-EventCalendar-Token";
    private static final String USER_ID = "user_uuid";

    final UserDao userDao;

    protected SessionController(UserDao userDao) {
        this.userDao = userDao;
    }

    String setSessionUser(final @NotNull User user) {
        val claims = new HashMap<String, Object>();
        claims.put(USER_ID, user.getUuid());
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, K64)
                .compact();
    }

    User getSessionUser(final @NotNull HttpServletRequest request) {
        val payload = Optional.ofNullable(request.getHeader(TOKEN_HEADER))
                .orElseThrow(() -> new UnAuthorized("No token"));
        try {
            val claims = Jwts.parser()
                    .setSigningKey(K64)
                    .parseClaimsJws(payload)
                    .getBody();
            val uuid = Optional.of((String) claims.get(USER_ID))
                    .map(UUID::fromString)
                    .orElseThrow(() -> new UnAuthorized("Field user_id not exist"));
            return userDao.get(uuid)
                    .orElseThrow(() -> new ResourceNotFoundException("user_uuid", uuid));
        } catch (ResourceNotFoundException e) {
            log.error("getSessionUser ResourceNotFoundException: {}", e);
            throw new UnAuthorized("");
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.error("getSessionUser parseClaimsJws failed: {}", e);
            throw new UnAuthorized("");
        }
    }

    protected boolean isRole(HttpServletRequest request, RoleOfUser role) {
        val user = getSessionUser(request);
        return user.getRoles().contains(role);
    }
}
