package dev.drugowick.investments.configuration.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Order(value = 200)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    private static final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Value("${dev.drugowick.investments.devmode:#{'0'}}")
    private String devMode;

    @Value("${dev.drugowick.investments.devmode.password:#{'xibanga'}}")
    private String devPass;

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsManager());
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        return jdbcUserDetailsManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * Special treatment for development servers
         */
        if (devMode.equals("1") && devPass.equals("aiowas")) {
            log.info("DEVMODE: Allowing /h2-console endpoint. Please make sure you didn't change the h2-console endpoint configuration.");
            log.info("DEVMODE: Be aware that X-Frame-Options and Cross-site request forgery are enabled in this mode. YOU SHOULD ONLY USE THESE CONFIGURATIONS FOR DEVELOPMENT PURPOSES!");

            http.authorizeRequests()
                    //.antMatchers("/").permitAll()
                    .antMatchers("/h2-console/**").permitAll();
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }


        /**
         * This simple configuration grants access only to the login page for non-authenticated users.
         *
         * When roles are applied, here's also the place to configure.
         */
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }
}
