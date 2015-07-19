package kasyan.catalog.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.exceptions.AuthorNotFoundException;
import kasyan.catalog.exceptions.BookNotFoundException;
import kasyan.catalog.services.CatalogService;
import kasyan.catalog.validators.AuthorPropertyEditorSupport;
import kasyan.catalog.validators.BookValidator;

import org.apache.log4j.Logger;
import org.hibernate.search.errors.EmptyQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
	protected void initBinder(WebDataBinder binder) {
		logger.debug(bookValidator);
		binder.setValidator(bookValidator);
		binder.registerCustomEditor(Author.class, "authors",
				new AuthorPropertyEditorSupport());
	}

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ModelAndView booksListPage() {
		ModelAndView mw = new ModelAndView();
		mw.addObject("books", catalogService.getBooks());
		mw.setViewName("books");
		return mw;
	}

	@RequestMapping(value = "/books_search", method = RequestMethod.POST)
	public ModelAndView searchBooks(HttpServletRequest request) {
		String title = (String) request.getParameter("title");
		logger.debug("Searching books by: " + title);
		ModelAndView mw = new ModelAndView();
		mw.addObject("books", catalogService.findBooksByTitle(title));
		mw.setViewName("books");
		return mw;
	}

	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/create_book", method = RequestMethod.GET)
	public ModelAndView showCreateBookPage() {
		ModelAndView mw = new ModelAndView();
		List<Author> authors = catalogService.getAuthors();
		mw.addObject("book", new Book());
		mw.addObject("authors", authors);
		mw.setViewName("create_book");
		return mw;
	}

	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/new_book", method = RequestMethod.POST)
	public ModelAndView createBookAndShowMainPage(@Valid Book book,
			BindingResult bindingResult) {
		ModelAndView mw = new ModelAndView();
		logger.debug("Book: " + book);
		logger.debug("Length of title: " + book.getTitle().length()
				+ ", length of description: "
				+ book.getShortDescription().length() + ", length of date: "
				+ book.getDatePublish().length());
		logger.debug("bindingResult has errors: " + bindingResult.hasErrors());
		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				logger.debug(error);
			}
			mw.addObject("authors", catalogService.getAuthors());
			mw.setViewName("create_book");
			return mw;
		}
		//
		List<Author> authorsList = book.getAuthors();
		logger.debug("List of authors: " + authorsList);
		List<Book> books = catalogService.addBook(book);
		mw.addObject("books", books);
		mw.setViewName("books");
		return mw;
	}

	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/delete_book/{id}", method = RequestMethod.GET)
	public ModelAndView deleteBookAndShowBooks(@PathVariable int id) throws BookNotFoundException {
		logger.debug("Delete the book with id = " + id);
		catalogService.deleteBook(id);
		List<Book> books = catalogService.getBooks();
		ModelAndView mw = new ModelAndView();
		mw.addObject("books", books);
		mw.setViewName("books");
		return mw;
	}
	
	@RequestMapping(value = "/books_of_author/{id}", method = RequestMethod.GET)
	public ModelAndView showBookOfAuthor(@PathVariable int id) throws AuthorNotFoundException{
		ModelAndView mw = new ModelAndView();
		List<Book> books = catalogService.getBooksByAuthor(id);
		mw.addObject("books", books);
		mw.setViewName("books_of_author");
		return mw;
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/modify_book/{id}", method = RequestMethod.GET)
	public String showModifyBookPage(@PathVariable int id, Model model) throws BookNotFoundException {
		logger.debug("Modification the book with id = " + id);
		model.addAttribute("book", catalogService.getBookById(id));
		model.addAttribute("authors", catalogService.getAuthors());
		return "modify_book";
	}
	
	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/modify_book/update_book", method = RequestMethod.POST)
	public ModelAndView modifyBook(@Valid Book book, BindingResult bindingResult) {
		ModelAndView mw = new ModelAndView();
		if(bindingResult.hasErrors()){
			logger.debug("Updated book has errors"+bindingResult.getAllErrors());
			mw.addObject("authors", catalogService.getAuthors());
			mw.setViewName("modify_book");
			return mw;
		}
		catalogService.modifyBook(book);
		mw.addObject("books", catalogService.getBooks());
		mw.setViewName("books");
		return mw;
	}
	
	@ExceptionHandler(BookNotFoundException.class)
	public String handleBookNotFoundException(){
		return "404";
	}
	
	@ExceptionHandler(EmptyQueryException.class)
	public String handleEmptyQueryException(){
		return "books";
	}
}
