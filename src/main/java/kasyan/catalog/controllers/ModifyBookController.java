package kasyan.catalog.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.services.CatalogService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/modify_book", method = RequestMethod.GET)
public class ModifyBookController {

	@Autowired
	private CatalogService catalogService;
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ModelAndView showModifyBookPage(@PathVariable int id) {
		logger.debug("Modification the book with id = " + id);
		Book book = catalogService.getBookById(id);
		List<Author> authors = catalogService.getAuthors();
		ModelAndView mw = new ModelAndView();
		mw.addObject("book", book);
		mw.addObject("authors", authors);
		mw.setViewName("modify_book");
		return mw;
	}

	@Secured(value = { "ROLE_ADMIN" })
	@RequestMapping(value = "/change_book", method = RequestMethod.POST)
	public ModelAndView modifyBook(HttpServletRequest request) {
		ModelAndView mw = new ModelAndView();
		int id = Integer.valueOf(request.getParameter("id_book"));
		String title = request.getParameter("title");
		String shortDescription = request.getParameter("desc");
		String datePublish = request.getParameter("date");
		String[] authors = request.getParameterValues("authors");
		logger.debug("Title: " + title + ", shortDescription: "
				+ shortDescription + ", datePublish: " + datePublish
				+ ", authors: " + Arrays.toString(authors));
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setShortDescription(shortDescription);
		book.setDatePublish(datePublish);
		List<Book> books = catalogService.modifyBook(book, authors);
		mw.addObject("books", books);
		mw.setViewName("all_books");
		return mw;
	}
}
