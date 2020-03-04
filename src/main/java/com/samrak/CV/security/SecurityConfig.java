package com.samrak.CV.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;


//Solution 3: oAuth: JWT
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		//Traduction: authorise tous pour /authenticate.
		//toute autre request doit etre authenticate ( anyRequest)
		//Stateless: we use the jwt to manage session instead
		http
		.cors()
		.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/authenticate").permitAll()
			.antMatchers("/register").permitAll()
			.antMatchers("/skills").permitAll()
			.antMatchers("/downloadCV/**").permitAll()
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			.antMatchers("/recruiter/**").hasAnyAuthority("USER","ADMIN")
			.antMatchers("/offer/**").hasAnyAuthority("USER","ADMIN")
			.anyRequest().authenticated()
		.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	
//SOLUTION 2: 	BASIC AUTH BCrypt
//	@Bean
//	public AuthenticationProvider authProvider() {
//		
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService);
//		//If no encryption
//		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//		//with Encryption
//		provider.setPasswordEncoder( new BCryptPasswordEncoder());
//		
//		
//	return provider;
//	}
//	

//	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		//csrf : Cross-Site Request Forgery 
//		
//	//Working=>	http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll();
//		
//		http.cors().and()
//	        .authorizeRequests()
//	        .antMatchers(HttpMethod.POST, "/login").permitAll()
//		.and()
//			.csrf().disable().authorizeRequests()
//			.antMatchers("/admin/**").hasAuthority("ADMIN")
//			.antMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
//			.antMatchers("/").permitAll()
//		.and()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//		.and()
//			.formLogin().loginPage("/login").permitAll()
//		.and()
//			.logout().invalidateHttpSession(true)
//			.clearAuthentication(true)
//			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
//			
//		
//	}
//	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
		
		
		//return NoOpPasswordEncoder.getInstance();
		
	}
	
	
	@Bean
    CorsConfigurationSource corsConfigurationSource()
    {
		 	CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://www.samrak.info", "https://www.samrak.info", "https://guarded-castle-37898.herokuapp.com"));
		 	configuration.setAllowedHeaders(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
    }


	
	
//**************************************************************************************************
	
	
//SOLUTION 1) In Memory and direct set Up: 
//	@Bean //We will need it as an object
//	@Override
//	protected UserDetailsService userDetailsService() {

//	List<UserDetails> users = new ArrayList<>();
//	users.add(User.withDefaultPasswordEncoder().username("user").password("1234").roles("USER").build());
//	return new InMemoryUserDetailsManager(users);
//	
	
	//method to personalise Login / Logout pages:
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	
// 		need to create login page
//		http
//			.csrf().disable()
//			.authorizeRequests().antMatchers("/login").permitAll()
//			.anyRequest().authenticated()
//			.and()
//			.formLogin()
//			.loginPage("login").permitAll()
//			.and()
//			.logout().invalidateHttpSession(true)
//			.clearAuthentication(true)
//			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//			.logoutSuccessUrl("/logout-success").permitAll();
	
	
//		http
//		.csrf().disable()
//		.authorizeRequests().antMatchers("/signin").permitAll()
//		.anyRequest().authenticated();
//	
//	}
//	
	
	
	

	
	
}
