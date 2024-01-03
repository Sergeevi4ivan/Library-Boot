package ru.panas.springBootLibrary.LibraryBoot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


// этот метод можно было бы создать в SecurityConfig, но тогда возникоет ошибка
// циклической зависимости из-за создания спрингом двух бинов с PasswordEncoder
@Configuration
public class EncoderConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
