package kasyan.catalog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error){
		ModelAndView mw = new ModelAndView();
		if (error != null) {
			mw.addObject("error", "Invalid username and password!");
		}
		mw.setViewName("login");
		return mw;
	}
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String getMainPage(){
		return "/main";
	}
	
	@RequestMapping(value="/403", method=RequestMethod.GET)
	public String accessDenidePage(){
		return "403";
	}
	
}
