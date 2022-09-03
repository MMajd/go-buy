package com.mmajd.gobuy.admin.config;


import com.mmajd.gobuy.admin.repository.RedisTokenRepositoryImpl;
import com.mmajd.gobuy.admin.repository.UserRepository;
import com.mmajd.gobuy.admin.websecurity.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String sessionKey;

    public SecurityConfig(@Value("${app.session.key}") String sessionKey) {
        this.sessionKey = sessionKey;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return new UserDetailsServiceImpl(repository);
    }

    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder managerBuilder, UserDetailsService service) {
        managerBuilder.authenticationProvider(authenticationProvider(service));
        return managerBuilder.getOrBuild();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RedisTokenRepositoryImpl tokenRepository) throws Exception {
        http
                .authorizeRequests().antMatchers("/public/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin().loginPage("/login").usernameParameter("email").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/logout?logout=true").permitAll()
                .and()
                .rememberMe().rememberMeParameter("remember-me").key(sessionKey)
                .alwaysRemember(true).tokenRepository(tokenRepository)
        ;

        return http.getOrBuild();
    }
}
