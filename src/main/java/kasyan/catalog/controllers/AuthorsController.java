package kasyan.catalog.controllers;

import java.util.List;

import javax.validation.Valid;

import kasyan.catalog.dto.Author;
import kasyan.catalog.exceptions.AuthorNotFoundException;
import kasyan.catalog.services.CatalogService;
import kasyan.catalog.validators.AuthorValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthorsController {
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private AuthorValidator authorValidator;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}
	
	public void setAuthorValidator(AuthorValidator authorValidator) {
		this.authorValidator = authorValidator;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(authorValidator);
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
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value="/create_author", method=RequestMethod.GET)
	public ModelAndView showCreateAuthor(){
		ModelAndView mw = new ModelAndView();
		mw.addObject("author", new Author());
		mw.setViewName("create_author");
		return mw;
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/new_author", method = RequestMethod.POST)
	public ModelAndView createBookAndShowMainPage(@Valid Author author,
			BindingResult bindingResult) {
		logger.debug("New author: "+author+", and books: "+author.getBooks());
		ModelAndView mw = new ModelAndView();
		if(bindingResult.hasErrors()){
			logger.debug("New book has errors"+bindingResult.getAllErrors());
			mw.setViewName("create_author");
			return mw;
		}
		catalogService.addAuthor(author);
		mw.addObject("authors", catalogService.getAuthors());
		mw.setViewName("authors");
		return mw;
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/modify_author/{id}", method = RequestMethod.GET)
	public String showModifyAuthorPage(@PathVariable int id, Model model) throws AuthorNotFoundException{
		model.addAttribute("author", catalogService.getAuthorById(id));
		return "modify_author";
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/modify_author/update_author", method = RequestMethod.POST)
	public ModelAndView modifyAuthorAndShowMainPage(@Valid Author author, BindingResult bindingResult){
		logger.debug("Updated author: "+author);
		ModelAndView mw = new ModelAndView();
		if(bindingResult.hasErrors()){
			logger.debug("Updated author has errors"+bindingResult.getAllErrors());
			mw.setViewName("modify_author");
			return mw;
		}
		catalogService.modifyAuthor(author);
		mw.addObject("authors", catalogService.getAuthors());
		mw.setViewName("authors");
		return mw;
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/delete_author/{id}", method = RequestMethod.GET)
	public ModelAndView deleteAuthorAndShowAuthors(@PathVariable int id) throws AuthorNotFoundException {
		logger.debug("Delete the author with id = " + id);
		catalogService.deleteAuthor(id);
		ModelAndView mw = new ModelAndView();
		mw.addObject("authors", catalogService.getAuthors());
		mw.setViewName("authors");
		return mw;
	}
	@ExceptionHandler(AuthorNotFoundException.class)
	public String handleAuthorkNotFoundException(){
		return "404";
	}
}
