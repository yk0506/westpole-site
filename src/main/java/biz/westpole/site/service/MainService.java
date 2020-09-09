package biz.westpole.site.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class MainService {
	
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
}
