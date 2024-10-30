package com.senla.myproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfiguration {


   /* @Bean
    public UserDetailsService userDetailsService (PasswordEncoder encoder){
        *//*UserDetails admin = User.builder().username("admin@gmail.com").password(encoder.encode("admin")).roles("ADMIN").build();
        UserDetails manager = User.builder().username("kaiko@gmail.com").password(encoder.encode("12345")).roles("MANAGER").build();
        System.out.println("PASSWORD "+manager.getPassword());

        UserDetails forwarder = User.builder().username("tuminas@gmail.com").password(encoder.encode("111111")).roles("FORWARDER").build();
        return new InMemoryUserDetailsManager(admin,manager,forwarder);*//*
        return new MyUserDetailsService();
    }*/


/*
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //HTTP Basic authentication
                .csrf(AbstractHttpConfigurer::disable) //откоючить защиту от CSRF атак
                .authorizeHttpRequests(auth -> auth.requestMatchers("/**").authenticated()) // только для аутенфицир. пользователей
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);
        return http.build();
    }
}
