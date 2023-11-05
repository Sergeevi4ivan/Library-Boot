package ru.panas.springBootLibrary.LibraryBoot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.panas.springBootLibrary.LibraryBoot.services.PersonDetailService;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final PersonDetailService personDetailService;
    private final EncoderConfig encoderConfig;

    @Autowired
    public SecurityConfig(PersonDetailService personDetailService, EncoderConfig encoderConfig) {
        this.personDetailService = personDetailService;
        this.encoderConfig = encoderConfig;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers("/people/edit",
                        "/people/index",
                        "/people/new",
                        "/people/show").hasRole("ADMIN")
                .requestMatchers("/auth/login",
                        "/auth/registration",
                        "/error").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN"))

                .formLogin(formLogin -> formLogin
                .loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/books", true)
                .failureUrl("/auth/login?error"))

                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/auth/login"));

        return httpSecurity.build();
    }

    @Autowired
    protected void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailService).passwordEncoder(encoderConfig.getPasswordEncoder());
    }



}
