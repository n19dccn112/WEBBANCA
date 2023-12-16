package com.n19dccn112.security;

import com.n19dccn112.security.jwt.JwtAuthEntryPoint;
import com.n19dccn112.security.jwt.JwtAuthTokenFilter;
import com.n19dccn112.security.jwt.JwtUtils;
import com.n19dccn112.security.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;

    private final JwtAuthEntryPoint unauthorizedHandler;

    private final JwtUtils jwtUtils;

    public WebSecurityConfig(
            UserDetailsServiceImpl userDetailsService,
            JwtAuthEntryPoint unauthorizedHandler,
            JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        // TODO
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    // cấu hình phân quyền
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,
                        "/api/auth/**",
                        "/api/v1/public**",
                        "/swagger-ui**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**")
                .permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/v1/public**",
                        "/swagger-ui**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/api/categoryDetail**",
                        "/api/categoryDetail/**",
                        "/api/categories**",
                        "/api/categories/**",
                        "/api/categoryTypes**",
                        "/api/categoryTypes/**",
                        "/api/images**",
                        "/api/images/**",
                        "/api/imagesDetail**",
                        "/api/imagesDetail/**",
                        "/api/userProducts**",
                        "/api/userProducts/**",
                        "/api/features**",
                        "/api/features/**",
                        "/api/featureTypes**",
                        "/api/featureTypes/**",
                        "/api/featureDetails**",
                        "/api/featureDetails/**",
                        "/api/unitDetail/**",
                        "/api/unitDetail**",
                        "/api/unit/**",
                        "/api/unit**",
                        "/api/ponds/**",
                        "/api/ponds**",
                        "/api/products/**",
                        "/api/products**",
                        "/api/statusFish/**",
                        "/api/statusFish**",
                        "/api/events/**",
                        "/api/events**",
                        "/api/eventStatus/**",
                        "/api/eventStatus**",
                        "/api/users/**",
                        "/api/users**",
                        "/api/userDetails/**",
                        "/api/userDetails**",
                        "/api/business**",
                        "/api/business/**",
                        "/api/businessDetail**",
                        "/api/businessDetail/**",
                        "/api/fqa**",
                        "/api/fqa/**",
                        "/api/reply**",
                        "/api/reply/**",
                        "/api/districts/**",
                        "/api/districts**",
                        "/api/provinces/**",
                        "/api/provinces**",
                        "/api/wards/**",
                        "/api/wards**")
                .permitAll()
                .antMatchers("/api/orders/shipSuccess","/api/orders/shipCancel")
                .hasRole("SHIP")
                .antMatchers(
                        "/api/paymentMethod/**",
                        "/api/paymentMethod**",
                        "/api/orderStatus/**",
                        "/api/orderStatus**",
                        "/api/orders/**",
                        "/api/orders**",
                        "/api/paymentMethod**",
                        "/api/paymentMethod/**",
                        "/api/users/**",
                        "/api/users**",
                        "/api/userDetails/**",
                        "/api/userDetails**",
                        "/api/business**",
                        "/api/business/**",
                        "/api/businessDetail**",
                        "/api/businessDetail/**",
                        "/api/fqa**",
                        "/api/fqa/**",
                        "/api/reply**",
                        "/api/reply/**",
                        "/api/categoryDetails**",
                        "/api/categoryDetails/**",
                        "/api/categories**",
                        "/api/categories/**",
                        "/api/categoryTypes**",
                        "/api/categoryTypes/**",
                        "/api/images**",
                        "/api/images/**",
                        "/api/imagesDetail**",
                        "/api/imagesDetail/**",
                        "/api/userProducts**",
                        "/api/userProducts/**",
                        "/api/features**",
                        "/api/features/**",
                        "/api/featureTypes**",
                        "/api/featureTypes/**",
                        "/api/featureDetails**",
                        "/api/featureDetails/**",
                        "/api/unitDetail/**",
                        "/api/unitDetail**",
                        "/api/unit/**",
                        "/api/unit**",
                        "/api/products/**",
                        "/api/products**",
                        "/api/statusFish/**",
                        "/api/statusFish**",
                        "/api/events/**",
                        "/api/events**",
                        "/api/eventStatus/**",
                        "/api/eventStatus**"
                )
                .hasAnyRole("SHOP", "USER")

                .anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
