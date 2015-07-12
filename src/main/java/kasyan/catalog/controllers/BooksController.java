package kasyan.catalog.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.services.CatalogService;
import kasyan.catalog.validators.BookValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BooksController {
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private BookValidator bookValidator;
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}
	
	public void setBookValidator(BookValidator bookValidator) {
		this.bookValidator = bookValidator;
	}
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder){
		binder.setValidator(bookValidator);
	}
	
	@RequestMapping(value="/all_books", method=RequestMethod.GET)
	public ModelAndView booksListPage(){
		ModelAndView mw = new ModelAndView();
		mw.addObject("books", catalogService.getBooks());
		mw.setViewName("all_books");
		return mw;
	}
	
	@RequestMapping(value="/books_search", method=RequestMethod.POST)
	public ModelAndView searchBooks(HttpServletRequest request){
		String title = (String) request.getParameter("title");
		logger.debug("Searching books by: "+title);
		ModelAndView mw = new ModelAndView();
		mw.addObject("books", catalogService.findBooksByTitle(title));
		mw.setViewName("all_books");
		return mw;
	}
	
	@Secured(value = {"ROLE_ADMIN"})
	@RequestMapping(value="/create_book", method=RequestMethod.GET)
	public ModelAndView showCreateBookPage(){
		ModelAndView mw = new ModelAndView();
		List<Author> authors = catalogService.getAuthors();
		mw.addObject("authors", authors);
		mw.setViewName("create_book");
		return mw;
	}
	
	@Secured(value = {"ROLE_ADMIN"})
	@RequestMapping(value="/new_book", method=RequestMethod.POST)
	public ModelAndView createBookAndShowMainPage(HttpServletRequest request){
		ModelAndView mw = new ModelAndView();
		//
		String title = request.getParameter("title");
		String shortDescription = request.getParameter("desc");
		String datePublish = request.getParameter("date");
		//
		String[] authors = request.getParameterValues("authors");
		logger.debug("Parameters for new book: title - " + title + ", shortDescription - "
				+ shortDescription + ", datePublish - " + datePublish
				+ ", authors with ids - " + Arrays.toString(authors));
		Book book = new Book(title, shortDescription, datePublish);
		List<Book> books = catalogService.addBook(book, authors);
		mw.addObject("books", books);
		mw.setViewName("all_books");
		return mw;
	}
	
	@Secured(value = {"ROLE_ADMIN"})
	@RequestMapping(value="/delete_book/{id}", method=RequestMethod.GET)
	public ModelAndView deleteBookAndShowMainPage(@PathVariable int id){
		logger.debug("Delete the book with id = " + id);
		catalogService.deleteBook(id);
		List<Book> books = catalogService.getBooks();
		ModelAndView mw = new ModelAndView();
		mw.addObject("books", books);
		mw.setViewName("all_books");
		return mw;
	}
}
