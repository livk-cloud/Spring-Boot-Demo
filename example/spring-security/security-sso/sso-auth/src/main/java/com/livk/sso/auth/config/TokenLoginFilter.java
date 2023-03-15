package com.livk.sso.auth.config;

import com.livk.commons.jackson.JacksonUtils;
import com.livk.commons.web.util.WebUtils;
import com.livk.sso.commons.entity.Role;
import com.livk.sso.commons.entity.User;
import com.livk.sso.commons.util.JwtUtils;
import com.nimbusds.jose.jwk.RSAKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * TokenLoginFilter
 * </p>
 *
 * @author livk
 */
public class TokenLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
            "POST");

    private final AuthenticationManager authenticationManager;

    private final RSAKey rsaKey;

    public TokenLoginFilter(AuthenticationManager authenticationManager, RSAKey rsaKey) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.authenticationManager = authenticationManager;
        this.rsaKey = rsaKey;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = JacksonUtils.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                    user.getAuthorities());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            Map<String, Object> map = Map.of("code", HttpServletResponse.SC_UNAUTHORIZED, "msg", "用户名或者密码错误！");
            WebUtils.out(response, map);
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        super.successfulAuthentication(request, response, chain, authResult);
        User user = new User().setUsername(authResult.getName()).setRoles((List<Role>) authResult.getAuthorities());
        String token = JwtUtils.generateToken(user, rsaKey);
        Map<String, Object> map = Map.of("code", HttpServletResponse.SC_OK, "data", token);
        WebUtils.out(response, map);
    }

}
