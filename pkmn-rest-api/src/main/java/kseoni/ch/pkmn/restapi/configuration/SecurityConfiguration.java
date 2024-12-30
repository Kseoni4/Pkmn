package kseoni.ch.pkmn.restapi.configuration;

import kseoni.ch.pkmn.shared.security.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/ping").permitAll()
                .requestMatchers(new AntPathRequestMatcher("/cards")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/public/**")).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/cards").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/cards/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/cards").hasRole("ADMIN")
                .requestMatchers("/error", "/error**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .anyRequest().authenticated());
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.formLogin(login -> login
                .successForwardUrl("/success"));
        //http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}