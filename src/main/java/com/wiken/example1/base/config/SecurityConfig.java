package com.wiken.example1.base.config;

import com.wiken.example1.base.securityhandler.LoginSuccessHandler;
import com.wiken.example1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * spring security
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 인가 정책
         */
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/css/**", "/h2-console/**", "/js/**").permitAll()
                .antMatchers("/login", "/signup", "/logout").permitAll();

        /**
         * h2-console
         */
        http.cors().and().csrf().disable()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));

        /**
         * 인증 정책
         */
        http
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .usernameParameter("email")
                .passwordParameter("pwd")
                .successHandler(successHandler())
        .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);
    }

    /**
     * userDetailsService 등록 및 패스워드 암호화 인코더 설정
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    /**
     * LoginSuccessHandler 빈 추가
     */
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler("/");
    }
}
