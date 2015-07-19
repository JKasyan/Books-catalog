package kasyan.catalog.services.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kasyan.catalog.dao.AuthorDao;
import kasyan.catalog.dao.BookDao;
import kasyan.catalog.dao.UserDao;
import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.dto.User;
import kasyan.catalog.exceptions.AuthorNotFoundException;
import kasyan.catalog.exceptions.BookNotFoundException;
import kasyan.catalog.services.CatalogService;

public class CatalogServiceImpl implements CatalogService {

	private AuthorDao authorDao;
	private BookDao bookDao;
	private UserDao userDao;
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public void setAuthorDao(AuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public List<Book> getBooks() {
		return bookDao.selectAllBooks();
	}

	public List<Author> getAuthors() {
		return authorDao.selectAllAuthors();
	}

	public List<Book> findBooksByTitle(String title) {
		return bookDao.selectBooks(title);
	}

	public Book getBookById(int id) throws BookNotFoundException {
		return bookDao.selectBook(id);
	}

	public List<Book> addBook(Book book) {
		logger.debug("New book: " + book);
		bookDao.insertBook(book);
		return bookDao.selectAllBooks();
	}

	public void modifyBook(Book book){
		logger.debug("Book for updating: " + book);
		bookDao.updateBook(book);
	}

	public void deleteBook(int id) throws BookNotFoundException {
		bookDao.deleteBook(id);
	}

	public void addAuthor(Author author) {
		authorDao.insertAuthor(author);
	}

	public List<Book> getBooksByAuthor(int id) throws AuthorNotFoundException {
		return bookDao.selectBooksByAuthorId(id);
	}

	public Author getAuthorById(int id) throws AuthorNotFoundException {
		return authorDao.selectAuthor(id);
	}

	public void modifyAuthor(Author author) {
		authorDao.updateAuthor(author);
	}

	public void deleteAuthor(int id) throws AuthorNotFoundException {
		authorDao.deleteAuthor(id);
	}

	public void addUser(User user) {
		logger.debug(user);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setEnabled(true);
		userDao.insertUser(user);
	}

}
