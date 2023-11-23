

package prescription.config;

//import static org.springframework.security.config.web.servlet.HttpSecurityDsl.authorizeRequests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import prescription.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	
	
	 @Autowired
	    private UserDetailsService userDetailsService;

	    @Bean
	    public static PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	   

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    	http
	                .authorizeHttpRequests(authorizeRequests ->
	                authorizeRequests//.requestMatchers(PathRequest.toH2Console()).permitAll()//requestMatchers(PathRequest.toH2Console()).permitAll()
	                        		.requestMatchers("/register/**").permitAll()
	                                .requestMatchers("/home").permitAll()
	                                .requestMatchers("/index").hasRole("ADMIN")
	                                .requestMatchers("/prescriptions/list").permitAll()
	                                .requestMatchers("/prescriptions/create").permitAll()
	                                .requestMatchers("/prescriptions/create").permitAll()
	                                .requestMatchers("/prescriptions/edit/{id}").permitAll()
	                                .requestMatchers("/prescriptions/edit").permitAll()
	                                .requestMatchers("/prescriptions/delete/{id}").permitAll()
	                                .requestMatchers("/prescriptions/delete").permitAll()
	                                .requestMatchers("/prescriptions/reports/day-wise").permitAll()
	                                .requestMatchers("/api/v1/prescriptions/list").permitAll()
	                                .anyRequest().authenticated()
	                ).formLogin(
	                        form -> form
	                                .loginPage("/login")
	                                .loginProcessingUrl("/login")
	                                .defaultSuccessUrl("/index")
	                                .permitAll()
	                ).logout(
	                        logout -> logout
	                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                                .permitAll()
	                
	                		
	                		
	                		);
	    	
	    	
	        return http.build();
	    }

	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	        		
	                .userDetailsService(userDetailsService)
	                .passwordEncoder(passwordEncoder());
	    }
	}
	
	

	