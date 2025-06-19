//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new String[]{"/**", "/css/**", "/img/**", "/js/**"});
    }

//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((ahr) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)ahr.requestMatchers(new String[]{"/", "/toInsert", "/insert", "/error/**"})).permitAll().anyRequest()).authenticated());
//        http.formLogin((login) -> ((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)login.loginPage("/").loginProcessingUrl("/login")).failureUrl("/?error=true")).defaultSuccessUrl("/employee/showList", false)).usernameParameter("mailAddress").passwordParameter("pass"));
//        http.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout**")).logoutSuccessUrl("/").deleteCookies(new String[]{"JSESSIONID"}).invalidateHttpSession(true));
//        return (SecurityFilterChain)http.build();
//    }
//
//    @Bean
//    private PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
