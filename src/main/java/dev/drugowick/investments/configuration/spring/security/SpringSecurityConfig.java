package dev.drugowick.investments.configuration.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SpringSecurityConfig.class);

    @Value("${dev.drugowick.investments.devmode:#{'0'}}")
    private String devMode;

    @Value("${dev.drugowick.investments.devmode.password:#{'xibanga'}}")
    private String devPass;

    private CustomSuccessHandler customSuccessHandler;

    public SpringSecurityConfig(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
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
         * This simple configuration grants access only to the home page for non-authenticated users.
         *
         * When roles are applied, here's also the place to configure.
         */
        http.antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(this.successHandler());
    }

    private AuthenticationSuccessHandler successHandler() {
        log.info("Setting custom success handler to " + this.customSuccessHandler.getClass());
        return this.customSuccessHandler;
    }
}
