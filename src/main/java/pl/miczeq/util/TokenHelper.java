package pl.miczeq.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import pl.miczeq.exception.DatabaseException;
import pl.miczeq.model.Role;
import pl.miczeq.model.User;
import pl.miczeq.repository.impl.UserRepositoryImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TokenHelper {
    private static final String SECRET = "Studia-Aplikacja-Blog";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";
    private static final int EXPIRES_IN = 10000;

    public static HttpServletResponse addAuthentication(HttpServletResponse response, String username) {
        Claims claims = Jwts.claims().setSubject(username);
        String roleName = "";
        Long userId = null;

        try {
           // Role role = new UserRepositoryImpl().findOneByUsername(username).getRoles().stream().findFirst().orElse(null);
            User user = new UserRepositoryImpl().findOneByUsername(username);
            Role role = user.getRoles().stream().findFirst().orElse(null);
            userId = user.getId();
            if (role != null) roleName = role.getRoleName();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        claims.put("id", userId);
        claims.put("role", roleName);

        String JWT = Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setExpiration(new Date(System.currentTimeMillis() + (EXPIRES_IN * 1000)))
                .compact();

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "X-PINGOTHER,Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");

        JSONObject jsonResponse = new JSONObject();

        try {
            jsonResponse.put("token", TOKEN_PREFIX + " " + JWT);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");

        try {
            response.getWriter().append(jsonResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            String username = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            List<Role> roles = new ArrayList<>();

            try {
                new UserRepositoryImpl().findOneByUsername(username).getRoles().stream().forEach(role -> roles.add(role));
            } catch (DatabaseException e) {
                e.printStackTrace();
            }

            return username != null ? new UsernamePasswordAuthenticationToken(username, null, roles) : null;
        }

        return null;
    }
}
