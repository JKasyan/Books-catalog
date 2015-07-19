package kasyan.catalog.controllers;

import javax.validation.Valid;

import kasyan.catalog.dto.User;
import kasyan.catalog.services.CatalogService;
import kasyan.catalog.validators.UserValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private UserValidator userValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}
	
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}
	
	public void setUserValidator(UserValidator userValidator) {
		this.userValidator = userValidator;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String showRegistrationPage(Model model){
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@RequestMapping(value = "/new_user", method = RequestMethod.POST)
	public String registrationAndShowLoginPage(@Valid User user, 
			BindingResult bindingResult){
		logger.debug(user);
		if(bindingResult.hasErrors()){
			logger.debug(bindingResult.getAllErrors());
			return "registration";
		}
		catalogService.addUser(user);
		return "login";
	}
}
