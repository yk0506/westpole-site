package biz.westpole.site.service;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import biz.westpole.site.domain.AccountRepository;
import biz.westpole.site.domain.LoginDTO;
import biz.westpole.site.mapper.LoginMapper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class LoginService implements UserDetailsService {
	
	private Logger log = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private AccountRepository accounts;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("#### load User By Username");
		
		LoginDTO loginDTO = accounts.findById(username);
		
		if( loginDTO == null ) {
			throw new UsernameNotFoundException(username);
		}
		
		loginDTO.setAuthorities(authorities());
		
		return loginDTO;
	}
	
	private Collection<? extends GrantedAuthority> authorities() { 
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")); 
	}

	
	public String getCookieUserID(HttpServletRequest request) throws Exception {
		
		Cookie cookie[] = request.getCookies();
		String id = null;
		
		Cookie c;

		if (cookie != null) {
			for (int i = 0; i < cookie.length; i++) {
			    c = cookie[i];
			    if(c.getName().equalsIgnoreCase("westpoleid")) {
			        id = c.getValue();
			    	break;  
			    }    
			}
		}
		
		return id;
	}
	
	public Boolean validUserInfo(LoginDTO params) throws Exception {
		
		Boolean result = false;
		
		try {
			if( loginMapper.validUserInfo(params) != 0 ) {
				result = true;
			}
			
		} catch(Exception e) {
			log.error(e.toString());
		}
		
		return result;
		
	}
	
	
	public String createAccount(LoginDTO params) throws Exception {
		
		String result = "complete";
		
		
		try {
			
			log.info(params.getUsername() + " / " + params.getNickname());
			
			loginMapper.createAccount(params);
			
		} catch(Exception e) {
			
			result = "error occured";
			log.error(e.toString());
			
		}
		
		return result;
		
	}

	
}
