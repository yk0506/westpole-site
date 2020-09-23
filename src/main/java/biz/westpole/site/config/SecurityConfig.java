package biz.westpole.site.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import biz.westpole.site.handler.CustomLoginSuccessHandler;
import biz.westpole.site.handler.CustomLogoutSuccessHandler;
import biz.westpole.site.service.LoginService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private LoginService loginService;
	
	@Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	
        http.authorizeRequests()
        	.antMatchers("/toc", "/board/write", "/board/modify").hasRole("USER")
        	.antMatchers("/", "/board/list", "/board/read/**").permitAll()
        	.anyRequest().permitAll();
        
        http
        	.formLogin()
        	.loginPage("/login")
        	.loginProcessingUrl("/login")
        	.failureUrl("/login?result=fail")
        	.defaultSuccessUrl("/", true)
        	.usernameParameter("username")
        	.passwordParameter("password")
        	.successHandler(new CustomLoginSuccessHandler());
        
        http
	    	.logout()
	    	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	    	.logoutSuccessUrl("/")
	    	.invalidateHttpSession(true)
	    	.logoutSuccessHandler(new CustomLogoutSuccessHandler());
        
        http.csrf().disable();
        
    }

    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    
    
    
}
