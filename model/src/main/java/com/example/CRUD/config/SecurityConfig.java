package com.example.CRUD.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthSuccessHandler successHandler;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/login", "/register", "/saveUser", "/verify", "/forgotPassword", "/resetPassword/**", "/resetPassword",
                                 "/theaters_Guest", "/uploads/**", "/promotions-photo/**", "/news-photo/**",
                                 "/user/home", "/user/theaters", "/user/newlist", "/user/promotionDetail/**", "/user/profile/**",
                                 "/admin/show/**", "/book_Guest/**", "/user/update-profile/**", "/user/change-password", 
                                 "/user/transactionHistory/**", "/user/mytickets/**", "/user/edit/profile", "/user/edit/upload-avatar",
                                 "/user/edit/update-profile", "/user/edit/update-profile/save", "/user/edit/change-password",
                                 "/user/edit/change-password/save", "/user/edit/history").permitAll()
                .requestMatchers("admin/show/**").hasRole("ADMIN")
                .requestMatchers("/cinemaowner/homecinemaowner/").hasRole("CINEMA_OWNER")
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/guest/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .loginProcessingUrl("/userLogin")
                .successHandler(successHandler)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"))
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }
}
