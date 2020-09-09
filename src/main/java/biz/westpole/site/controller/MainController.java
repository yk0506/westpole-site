package biz.westpole.site.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.westpole.site.domain.LoginDto;


@Controller
public class MainController {

	private static String USER_ID = "admin";
	private static String PASSWORD = "password";

	private Logger log = LoggerFactory.getLogger(MainController.class);
	
	@GetMapping("/")
	public String main() {
		return "westpole/contents/index";
	}
	
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		
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
		
		// 이미 로그인한 경우 메인페이지 
		if(id != null && id.equals(USER_ID)) {
			log.debug("이미 로그인 상태");
			return "redirect:/toc";
		}
		
		return "westpole/contents/login";
	}
		
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
			
		Cookie cookie = new Cookie("westpoleid", null);
		cookie.setDomain("westpole.biz");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return "westpole/contents/index";
	}
	
	@PostMapping("/login/submit")
	public String submit(HttpServletRequest request, HttpServletResponse response, LoginDto loginDto, Model model) {
		
	
		// todo: 아이디 패스워드 DB 데이터 비교하는 로직 추가
		if( loginDto.getId().equals(USER_ID) 
				&& loginDto.getPassword().equals(PASSWORD) ) {
			
			Cookie cookie = new Cookie("westpoleid", loginDto.getId());
			cookie.setDomain("westpole.biz");
			cookie.setMaxAge(3600);
			cookie.setPath("/");
			response.addCookie(cookie);
			
			return "redirect:/toc";
			
		} else {
			
			model.addAttribute("message", "Login fail. Check your account.");
			log.debug("실패");
			
			return "westpole/contents/login";
		}
	}
	
	@GetMapping("/toc")
	public String wemsToc() {
		return "westpole/contents/toc";	
	}
	
}
