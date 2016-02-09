package io.pivotal.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class ErrorViewWithoutPrincipalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErrorViewWithoutPrincipalApplication.class, args);
	}
	
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
	
	
	
//	@Bean
//	public SecurityConfigurer securityConfig() {
//		return new SecurityConfigurer();
//	}
	
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http.httpBasic()
			    .and()
				.authorizeRequests()
				.antMatchers("/error").permitAll()
				.anyRequest().authenticated();
		}
		
	}
	
}
