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



@Controller
public class MainController {

	private Logger log = LoggerFactory.getLogger(MainController.class);
	
	@GetMapping("/")
	public String main() {
		return "westpole/contents/index";
	}
	
	@GetMapping("/toc")
	public String wemsToc() {
		return "westpole/contents/toc";	
	}
	
}
