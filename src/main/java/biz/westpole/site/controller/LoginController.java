package biz.westpole.site.controller;

import java.net.http.HttpRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.westpole.site.domain.LoginDTO;
import biz.westpole.site.service.LoginService;


@Controller
public class LoginController {

	private Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	
	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		// 로그인 실패 시 메세지를 띄워줌 
		String result = (String)request.getParameter("result");
		
		if(result != null && result.equals("fail")) {
			model.addAttribute("loginError", true);
		} 
		
		return "westpole/contents/login";
	}
		
	
	@GetMapping("/account/create") 
	public String createForm() {
		return "westpole/contents/create";
	}
	
	@PostMapping("/account/create") 
	public String create(LoginDTO params, Model model) {
		
		String message = "complete";
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		log.info("## before : " + params.getPassword());
		params.setPassword( passwordEncoder.encode(params.getPassword()) );
		log.info("## after : " + params.getPassword());
		
		
		try {
			message = loginService.createAccount(params);
		} catch(Exception e) {
			log.error(e.toString());
		}
		
		model.addAttribute("message", message);
		
		return "westpole/contents/create";
	}
	
}
