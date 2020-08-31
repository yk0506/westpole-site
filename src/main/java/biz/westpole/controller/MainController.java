package biz.westpole.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.westpole.dto.LoginDto;


@Controller
public class MainController {
	
	public static String CURR_LOGIN_ID = null;

	@GetMapping("/")
	public String main() {
		return "westpole/contents/index.html";
	}
	
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		String id = (String)session.getAttribute("westpole.login.id");
		
		// 이미 로그인한 경우 메인페이지 
		if(id != null && id.equals("admin")) {
			System.out.println("이미 로그인 상태");
			return "redirect:/";
		}
		
		return "westpole/contents/login.html";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(true);
		String id = (String)session.getAttribute("westpole.login.id");
		
		if(id != null) {
			
			Cookie cookie = new Cookie("westpoleid", null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			
			session.removeAttribute("westpole.login.id");
			CURR_LOGIN_ID = null;
		}
		
		return "westpole/contents/index.html";
	}
	
	@PostMapping("/login/submit")
	public String submit(HttpServletRequest request, HttpServletResponse response, LoginDto loginDto, Model model) {
		
		HttpSession session = request.getSession(true);
		
	
		// todo: 아이디 패스워드 DB 데이터 비교하는 로직 추가
		if( loginDto.getId().equals("admin") 
				&& loginDto.getPassword().equals("xptmxm") ) {
			
			Cookie cookie = new Cookie("westpoleid", "admin");
			cookie.setMaxAge(86400);
			cookie.setPath("/");
			response.addCookie(cookie);
			
			System.out.println("로그인 성공");
			
			session.setAttribute("westpole.login.id", "admin");
			
			CURR_LOGIN_ID = "admin";
			
			return "redirect:/";
			
		} else {
			
			model.addAttribute("message", "Login fail!");
			System.out.println("실패");
			
			return "westpole/contents/login.html";
		}
		
	}
	
	@ResponseBody
	@CrossOrigin(origins = {"http://localhost:3000"}) 
	@GetMapping("/login/state")
	public String state(HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);
		String id = (String)session.getAttribute("westpole.login.id");
		
		System.out.println( "CURR_LOGIN_ID = " + CURR_LOGIN_ID );
		
		System.out.println("api 요청");

		
		return "{ \"westpoleid\" :\"" + CURR_LOGIN_ID + "\"}";
	}
}
