package com.numericaideas.multipleauthstrategies.configuration;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Configuration
    @Order(1)
    public class AdminSecurityConfig {

        @Autowired
        AdminAuthenticationProvider adminAuthenticationProvider;

        @Bean
        AuthenticationManager authenticationManager() {
            return new ProviderManager(adminAuthenticationProvider);
        }

        @Bean
        public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {

            http.securityMatcher("/admin/**")
                    .authenticationManager(authenticationManager())
                    .sessionManagement(session ->
                            session.maximumSessions(1)
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/admin/login").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .loginPage("/admin/login")
                            .loginProcessingUrl("/admin/login")
                            .defaultSuccessUrl("/admin")
                    )
                    .logout(handler -> handler
                            .logoutUrl("/admin/logout")
                            .logoutSuccessUrl("/admin/login")
                    );

            return http.build();
        }
    }

    @Configuration
    @Order(2)
    public class ApiSecurityConfig {

        @Autowired
        private JwtTokenFilter tokenFilter;

        @Bean
        public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

            http.securityMatcher("/api/**")
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/auth").permitAll()
                            .anyRequest().authenticated()
                    ).exceptionHandling(handler -> handler
                            .authenticationEntryPoint((req, res, ex) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                    )
                    .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }
    }
}
