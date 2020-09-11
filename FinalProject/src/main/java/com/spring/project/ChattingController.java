package com.spring.project;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//=== #175. (웹채팅관련7) === 

@Controller
public class ChattingController {
	
	@RequestMapping(value="/chatting/multichat.action", method={RequestMethod.GET})
	public String multichat(HttpServletRequest req, HttpServletResponse res, HttpSession session){
		
		String bcode = req.getParameter("bcode");
		req.setAttribute("bcode", bcode);
		
		session = req.getSession();
		session.setAttribute("bcode", bcode);
		
		return "chatting/multichat.tiles2";
	}
	
}
