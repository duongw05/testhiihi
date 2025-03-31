package com.language.service.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import com.language.service.config.ApplicationProperties;
import com.language.service.service.abs.loginsession.LoginSessionService;
import com.language.service.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LoginSessionService loginSessionService;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment environment;

    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(applicationContext.getBean(JpaAuthenticationProvider.class));
        return authenticationManagerBuilder.build();
    }

    @Bean
    @Primary
    protected AuthenticationManagerBuilder configure(AuthenticationManagerBuilder auth) throws Exception {
        return auth.authenticationProvider(applicationContext.getBean(JpaAuthenticationProvider.class));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        return http
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("*"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(loginFilter(http), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(refreshTokenFilter(http), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                .requestMatchers(Constants.LOGIN_URL).permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .anyRequest()
                .authenticated())
                .exceptionHandling((exception)-> exception.authenticationEntryPoint(new UnauthorizedAuthenticationEntryPoint()))
                .build();
        // @formatter:on
    }


    @Bean
    @Lazy
    public TokenProvider tokenProvider() {
        TokenProvider provider = new TokenProvider();
        provider.setApplicationProperties(applicationProperties);
        provider.setKeyPairProvider(keyPairProvider());
        return provider;
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> filterRegistrationBean(JwtAuthenticationFilter jwtAuthenticationFilter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrar = new FilterRegistrationBean<>(jwtAuthenticationFilter);
        registrar.setEnabled(false);
        return registrar;
    }

    @Bean
    public LoginFilter loginFilter(HttpSecurity http) throws Exception {
        LoginFilter filter = new LoginFilter();
        filter.setAuthenticationManager(authManager(http));
        filter.setMessageSource(messageSource);
        filter.setLoginSessionService(loginSessionService);
        filter.setTokenProvider(tokenProvider());
        return filter;
    }


    @Bean
    public RefreshTokenFilter refreshTokenFilter(HttpSecurity http) throws Exception {
        RefreshTokenFilter filter = new RefreshTokenFilter();
        filter.setAuthenticationManager(authManager(http));
        filter.setKeyPairProvider(keyPairProvider());
        filter.setMessageSource(messageSource);
        filter.setTokenProvider(tokenProvider());
        return filter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(messageSource, keyPairProvider(), environment);
    }


    @Bean
    public KeyPairProvider keyPairProvider() {
        return new StaticKeyPairProvider();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }
}
