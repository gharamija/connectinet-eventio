package com.eventio.backend.rest;

import com.eventio.backend.domain.Korisnik;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class WebSecurityBasic {

    @Value("${jwt.secret-key}")
    private String secretKey;
    private String generateToken(Authentication authentication) {
        Korisnik korisnik = (Korisnik) authentication.getPrincipal();
                Date expirationDate = new Date(System.currentTimeMillis() + 10 * 60 * 1000); //10min

        return Jwts.builder()
                .setSubject(korisnik.getUsername())
                .claim("userId", korisnik.getId())
                .claim("uloga", korisnik.getUloga())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/user/register")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/organizator/register")).permitAll()
                .anyRequest().authenticated());

        http.formLogin(configurer -> {
                    configurer.successHandler((request, response, authentication) -> {
                        String token = generateToken(authentication);
                        response.addHeader("Authorization", "Bearer " + token);
                        response.setStatus(HttpStatus.OK.value());
                    });
                });

        http.exceptionHandling(configurer -> {
                    final RequestMatcher matcher = new NegatedRequestMatcher(
                            new MediaTypeRequestMatcher(MediaType.TEXT_HTML));
                    configurer.defaultAuthenticationEntryPointFor((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }, matcher);
                });

        http.logout(configurer -> configurer
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.setStatus(HttpStatus.OK.value())))

                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}

