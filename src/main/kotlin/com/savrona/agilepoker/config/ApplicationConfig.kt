package com.savrona.agilepoker.config

import com.savrona.agilepoker.user.User
import com.savrona.agilepoker.user.UserRepository
import lombok.RequiredArgsConstructor

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@RequiredArgsConstructor
class ApplicationConfig (private val userRepository: UserRepository){

    @Bean
    public fun userDetailsService() : UserDetailsService? {
        return UserDetailsService { userName ->  userRepository.findOneByUserName(userName)}
    }

    @Bean
    public fun authenticationProvider() : AuthenticationProvider {
        val daoAuthenticationProvider :DaoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(userDetailsService())
        daoAuthenticationProvider.setPasswordEncoder(passWordEncoder())
        return daoAuthenticationProvider
    }

    @Bean
    public fun passWordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
    
    @Bean
    public fun authenticationManneger (config : AuthenticationConfiguration) : AuthenticationManager {
        return config.authenticationManager
    }
}