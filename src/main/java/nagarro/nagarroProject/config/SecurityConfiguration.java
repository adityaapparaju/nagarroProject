package nagarro.nagarroproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    
    @Autowired
	AuthenticationSuccessHandler successHandler;
	

    @Autowired
    PasswordEncoder passwordEncoder;
       
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                	.antMatchers("/main").hasAnyAuthority("User1","User2","UserTest")
                    .antMatchers(
                            "/js/**",
                            "/css/**",
                            "/img/**",
                            "/webjars/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login").successHandler(successHandler)
                            .permitAll().defaultSuccessUrl("/main")
                .and()
                    .logout()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                .permitAll();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   

    @Override
    @Autowired
	public void configure(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.inMemoryAuthentication()
		.passwordEncoder(passwordEncoder).withUser("user").password(passwordEncoder.encode("user")).authorities("User2").and()
				.withUser("admin").password(passwordEncoder.encode("admin")).authorities("User1").and()
				.withUser("test").password(passwordEncoder.encode("test")).authorities("UserTest");
		
	}
}
