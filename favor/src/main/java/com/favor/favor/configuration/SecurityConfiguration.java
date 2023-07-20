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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration   {
//    extends WebSecurityConfigurerAdapter 삭제
    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


//    protected void configure(HttpSecurity http) throws Exception {
    /*
    이전 방식은 Deprecated된 WebSecurityConfigurerAdapter 클래스에서
    protected void configure(HttpSecurity http) 메서드를 오버라이딩하여 보안 구성을 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        /*
        HttpSecurity 객체를 인자로 받아 보안 구성을 수행하고,
        구성이 완료된 SecurityFilterChain 객체를 반환하는 방식으로 수정
         */

        httpSecurity.
            httpBasic().disable()
            .csrf().disable() //CSRF 방지
            .cors()

            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/auth/**","/",
                    "/v2/api-docs", "/swagger-resources/**", "/swagger-ui/index.html", "/swagger-ui.html","/webjars/**", "/swagger/**",   // swagger
                    "/h2-console/**",
                    "/favicon.ico",
                    "/users/sign-in",
                    "/users/sign-up",
                    "/users/profile/**",
                    "/photos/**").permitAll()
            .anyRequest().authenticated()


            .and()
            .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())

            .and()
            .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())

            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
