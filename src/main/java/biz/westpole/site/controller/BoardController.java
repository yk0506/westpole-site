package biz.westpole.site.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import biz.westpole.site.domain.BoardDTO;
import biz.westpole.site.domain.CommonMessageDTO;
import biz.westpole.site.service.BoardService;
import biz.westpole.site.service.MainService;

@Controller
public class BoardController {
	
	private Logger log = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private MainService mainService;

	@GetMapping("/board/list")
	public String list(@ModelAttribute("params") BoardDTO params, Model model ) {
		
		try {
			
			ArrayList<BoardDTO> bltnList = boardService.selectBulletinList(params);
			
			model.addAttribute("bltnList", bltnList);
			model.addAttribute("params", params);
			

		} catch(Exception e) {
			log.error( e.toString() );
		}
			
		return "westpole/board/list";
	}
	
	@GetMapping("/board/read/{bltnNo}")
	public String getBulletin(@ModelAttribute("params") BoardDTO params, @PathVariable int bltnNo, Model model) {
		
		try {
			BoardDTO bltn = boardService.getBulletin(bltnNo);
			
			model.addAttribute("bltn", bltn);
			model.addAttribute("params", params);
			
		} catch(Exception e) {
			log.error( e.toString() );
		}
		
		return "westpole/board/view";
		
	}
	
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public String form(HttpServletRequest request, Model model) {
		
		try {
			
			//추후 아이디연동 추가 			
			String id = mainService.getCookieUserID(request);
			
			model.addAttribute("userId", id);
			
		} catch(Exception e) {
			log.error( e.toString() );
		}
		
		return "westpole/board/write";
		
	}
	
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String write(BoardDTO params)
			{
		
		try {
			
			boardService.insertBulletin(params);
			
		} catch(Exception e) {
			log.error( e.toString() );
		}
		
		return "redirect:/board/list";
		
		
	}
	
	@RequestMapping(value = "/board/validPassword", method = RequestMethod.POST)
	@ResponseBody()
	public String validPassword(BoardDTO params, Model model) {
		
		CommonMessageDTO obj = new CommonMessageDTO();
		ObjectMapper objMapper = new ObjectMapper();
		String objJson = null; 
		
		BoardDTO bltn = new BoardDTO();
		
		try {
			
			obj.setParams(params);
			
			bltn = boardService.getBulletin(params.getBltnNo());
			
			if(bltn.getPassword().equals(params.getPassword())) {
				obj.setMessage("valid password.");
				obj.setRedirectUri("/board/" + params.getMode());
				
			} else {
				obj.setMessage("[ERROR] Wrong password.");
			}
			
			objJson = objMapper.writeValueAsString(obj);
			
		} catch(Exception e) {
			log.error( e.toString() );
		}
		
		return objJson;
	}
	
	
	@RequestMapping(value = "/board/modify", method = RequestMethod.POST)
	public String modify(BoardDTO params, Model model) {
		
		log.debug(params.getMode() + " -> " + params.getBltnNo());
		
		try {
		
			if(params.getMode().equals("delete")) {
				
				// 삭제
				boardService.deleteBulletin(params);
				
			} else {
				
				BoardDTO bltn = boardService.getBulletin(params.getBltnNo());
				
				model.addAttribute("bltn", bltn);
				model.addAttribute("params", params);
				
				return "westpole/board/update";
			}
		} catch(Exception e) {
			log.error( e.toString() );
		}
		
		return "redirect:/board/list?currentPageNo=" + params.getCurrentPageNo();
	}
	
	
	@RequestMapping(value = "/board/update", method = RequestMethod.POST)
	public String update(BoardDTO params, Model model) {
		
		try {
			
			boardService.updateBulletin(params);
			
		} catch(Exception e) {
			log.error( e.toString() );
		}
		
		return "redirect:/board/list?currentPageNo=" + params.getCurrentPageNo();
	}
	
}
