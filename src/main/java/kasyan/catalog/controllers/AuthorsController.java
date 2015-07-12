package kasyan.catalog.controllers;

import java.util.List;

import kasyan.catalog.dto.Author;
import kasyan.catalog.services.CatalogService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthorsController {
	
	@Autowired
	private CatalogService catalogService;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}
	
	@RequestMapping(value="/authors", method=RequestMethod.GET)
	public ModelAndView showAuthors(){
		ModelAndView mw = new ModelAndView();
		List<Author> authors = catalogService.getAuthors();
		logger.debug("Authors: "+authors);
		mw.addObject("authors", authors);
		mw.setViewName("authors");
		return mw;
	}
}
