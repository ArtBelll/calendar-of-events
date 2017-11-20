package ru.korbit.ceadmin.controllers;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.korbit.cecommon.dao.UserDao;
import ru.korbit.cecommon.domain.User;
import ru.korbit.cecommon.exeptions.ResourceNotFoundException;
import ru.korbit.cecommon.exeptions.UnAuthorized;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
public class SessionController {

    private final static String KEY = "5do1idc92ujdllichl2f0yrtg1h0b06msft2cs6fpyjcs4so9ppa9np15s2itexn40fjk3h90k6frautj5y0ir9ujdldnm2lk30p8keeay0zp29mhl0zf8c0scyc8to9dvcyvib1pvwshmnvwf9ua4usuu6kb8kiztxvgea1dnox542yxv1i0t3lgvbygm94i8s7gf1oby4bwk1miwrj28t3zcyydmlwbgrw3ygwndv5aqqx1qpcerc2vn237i4rz8mosf6y29cf2fwfxdv3o58jvzqtpr8zggbnxq0v0c8l9yyg0zctm4n40aidu0rgromk05g1g2ypp22xm4ucdvlbmgvslpynm7tsecllv5cj0g3b0vvirijgyubypvtrlj9rujzwyrvs1uj4";
    private final static String K64 = TextCodec.BASE64.encode(KEY);

    private static final String TOKEN_HEADER = "X-EventCalendar-Token";
    private static final String USER_ID = "user_uuid";

    final UserDao userDao;

    SessionController(UserDao userDao) {
        this.userDao = userDao;
    }

    String setSessionUser(final @NotNull User user) {
        val claims = new HashMap<String, Object>();
        claims.put(USER_ID, user.getUuid());
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(new Date())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, K64)
                .compact();
    }

    User getSessionUser(final @NotNull HttpServletRequest request) {
        val cookies = Optional.ofNullable(request.getCookies())
                .orElseThrow(() -> new UnAuthorized("No cookies set"));
        val payload = Stream.of(cookies)
                .filter(c -> c.getName().equalsIgnoreCase(TOKEN_HEADER))
                .findFirst()
                .orElseThrow(() -> new UnAuthorized("There's no X-EventDiary-Token cookie"))
                .getValue();
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

    ResponseEntity<?> createdResponse(final Object data,
                                      final String jwt,
                                      final boolean ssl) throws URISyntaxException {
        val headers = new HttpHeaders();
        if (jwt != null && !"".equals(jwt))
            setJwtToken(headers, jwt, ssl);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    static boolean isSslRequest(final @NotNull HttpServletRequest request) {
        return request.getHeader("X-Real-IP") != null;
    }

    private void setJwtToken(final HttpHeaders headers, final String jwt, boolean ssl) {
        headers.add(HttpHeaders.SET_COOKIE, TOKEN_HEADER + "=" + jwt +
                "; path = /api/admin/v1 " +
                (ssl ? "; Secure" : "; HttpOnly"));
    }
}
