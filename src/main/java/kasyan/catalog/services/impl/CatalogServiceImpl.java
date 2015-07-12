package kasyan.catalog.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import kasyan.catalog.dao.AuthorDao;
import kasyan.catalog.dao.BookDao;
import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.services.CatalogService;

public class CatalogServiceImpl implements CatalogService {

	private AuthorDao authorDao;
	private BookDao bookDao;
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public void setAuthorDao(AuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
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

	public Book getBookById(int id) {
		return bookDao.selectBook(id);
	}

	/*
	 * public List<Book> modifyBook(Book book, String[] authorsIds) {
	 * logger.debug("authors: "+Arrays.toString(authorsIds)); Book
	 * bookForChanging = bookDao.selectBook(book.getId());
	 * bookForChanging.setTitle(book.getTitle());
	 * bookForChanging.setShortDescription(book.getShortDescription());
	 * bookForChanging.setDatePublish(book.getDatePublish()); if(authorsIds !=
	 * null){ List<Integer> ids = new ArrayList<Integer>(); for(int i =
	 * 0;i<authorsIds.length;i++){ ids.add(Integer.valueOf(authorsIds[i])); }
	 * List<Author> authors = authorDao.selectByList(ids);
	 * bookForChanging.setAuthors(new HashSet<Author>(authors)); }
	 * bookDao.updateBook(bookForChanging); return bookDao.selectAllBooks(); }
	 */

	public List<Book> addBook(Book book, String[] authorsIds) {
		List<Integer> ids = new ArrayList<Integer>();
		for (int i = 0; i < authorsIds.length; i++) {
			ids.add(Integer.valueOf(authorsIds[i]));
		}
		List<Author> authors = authorDao.selectByList(ids);
		book.setAuthors(new HashSet<Author>(authors));
		bookDao.insertBook(book);
		return bookDao.selectAllBooks();
	}

	public List<Book> modifyBook(Book book, String[] authors) {
		logger.debug("authors: " + Arrays.toString(authors));
		if (authors != null) {
			List<Integer> ids = new ArrayList<Integer>();
			for (int i = 0; i < authors.length; i++) {
				ids.add(Integer.valueOf(authors[i]));
			}
			bookDao.updateBook(book, ids);
		}else{
			bookDao.updateBook(book);
		}
		return bookDao.selectAllBooks();
	}

	public void deleteBook(int id) {
		bookDao.deleteBook(id);
	}

}
