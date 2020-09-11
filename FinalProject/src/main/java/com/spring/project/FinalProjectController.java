package com.spring.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Component
public class FinalProjectController {

	@RequestMapping(value="/index.action", method= {RequestMethod.GET})
	public String index(HttpServletRequest req) {
		
		
		HttpSession session = req.getSession();
		session.setAttribute("Countseachculture", "yes");
		
		//req.setAttribute("n", 1);
		
		String n = req.getParameter("p");
		
		req.setAttribute("msg", "회원가입 성공!");	
		req.setAttribute("p", n);
	
		return "main/index.tiles";
	} 
	
}
