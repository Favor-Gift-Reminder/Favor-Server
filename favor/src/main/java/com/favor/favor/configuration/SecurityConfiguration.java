package com.favor.favor.configuration;

import com.favor.favor.auth.JwtAuthenticationFilter;
import com.favor.favor.auth.JwtTokenProvider;
import com.favor.favor.exception.CustomAccessDeniedHandler;
import com.favor.favor.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()

                .csrf().disable() //CSRF 방지
                .cors()

                .and()
                .sessionManagement().sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**","/",
                        "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui.html","/webjars/**", "/swagger/**",   // swagger
                        "/h2-console/**",
                        "/favicon.ico",
                        "/users/sign-in",
                        "/users/sign-up",
                        "/users/profile/**").permitAll()
                .anyRequest().authenticated()


                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())


                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);


    }
}
