package kseoni.ch.pkmn.configurations;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import kseoni.ch.pkmn.PkmnApplication;
import kseoni.ch.pkmn.views.LoginView;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Import({PkmnApplication.class, ApplicationConfiguration.class})
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends VaadinWebSecurity {

    //private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserDetailsService jdbcUserDetailsManager;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/ping").permitAll()
                .requestMatchers(new AntPathRequestMatcher("/cards")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/public/**")).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/cards").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/cards/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/cards").hasRole("ADMIN")
                .requestMatchers("/error", "/error**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .requestMatchers("/rest/admin-ui/cardEntities/**").permitAll()
                .requestMatchers("/rest/admin-ui/studentEntities/**").permitAll());
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(customizer -> customizer.successForwardUrl("/success"));
        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        );
        http.userDetailsService(jdbcUserDetailsManager);
        //http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        super.configure(http);

        setLoginView(http, LoginView.class);
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}