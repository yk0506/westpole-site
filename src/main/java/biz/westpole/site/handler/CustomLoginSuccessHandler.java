package biz.westpole.site.handler;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import biz.westpole.site.domain.LoginDTO;


@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	
	private Logger log = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);
	
	private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		LoginDTO params = (LoginDTO)authentication.getPrincipal();
		
		ObjectMapper mapper = new ObjectMapper();
		log.info(  mapper.writeValueAsString(params)  );
		
		Cookie cookie = new Cookie("westpoleid", params.getUsername());
		cookie.setDomain("westpole.biz");
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		response.addCookie(cookie);
	
		resultRedirectStrategy(request, response, authentication);

	}
	
	protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String defaultUrl = "/";
        
        if(savedRequest!=null) {
            String targetUrl = savedRequest.getRedirectUrl();
            log.info("targetUrl : " + targetUrl);
            redirectStratgy.sendRedirect(request, response, targetUrl);
        } else {
            redirectStratgy.sendRedirect(request, response, defaultUrl);
        }
        
    }
}
