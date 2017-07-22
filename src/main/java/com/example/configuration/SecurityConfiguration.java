package com.example.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Autowired
	private SimpleAuthenticationSuccessHandler successHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		
		/*http.
		authorizeRequests()			
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			.antMatchers("/library/**").hasAuthority("STUDENT")
			.antMatchers("/library/**").hasAuthority("FACULTY")
			.antMatchers("/employee/staff/**").hasAuthority("LIBRARY_STAFF")
			.antMatchers("/employee/manager/**").hasAuthority("LIBRARY_MANAGER")
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").failureUrl("/login?error=true")
			.usernameParameter("email")
			.passwordParameter("password")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/").and().exceptionHandling()
			.accessDeniedPage("/access-denied");*/
		
		/*http.
		authorizeRequests()
			.antMatchers("/employee/manager/**").hasAuthority("LIBRARY_MANAGER").anyRequest()
			.authenticated().and().csrf().disable().formLogin()
			.loginPage("/login").failureUrl("/login?error=true")
			.defaultSuccessUrl("/employee/manager/home")
			.usernameParameter("email")
			.passwordParameter("password")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/").and().exceptionHandling()
			.accessDeniedPage("/access-denied");*/
		
		http.
		csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login")).and().
		authorizeRequests()
		.antMatchers("/library/**").hasAnyAuthority("STUDENT", "FACULTY")
		.antMatchers("/employee/staff/**").hasAnyAuthority("LIBRARY_STAFF")
		.antMatchers("/employee/manager/**").hasAnyAuthority("LIBRARY_MANAGER")
		.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
		.and().formLogin().successHandler(successHandler)
		.loginPage("/login").and().logout().permitAll().and()
		.csrf().disable().formLogin()
		.loginPage("/login").failureUrl("/login?error=true")
		.usernameParameter("email")
		.passwordParameter("password")
		.and().logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/").and().exceptionHandling()
		.accessDeniedPage("/access-denied");;
		/*http.
		authorizeRequests()
		.antMatchers("/library/**").hasAnyAuthority("STUDENT").anyRequest().authenticated().and().authorizeRequests()
		.antMatchers("/library/**").hasAnyAuthority("FACULTY").anyRequest().authenticated().and().authorizeRequests()
		.antMatchers("/employee/**").hasAnyAuthority("LIBRARY_MANAGER").anyRequest().authenticated().and().authorizeRequests()
		.antMatchers("/employee/**").hasAnyAuthority("LIBRARY_STAFF").anyRequest().authenticated();*/
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
}