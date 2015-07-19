package kasyan.catalog.dao;

import java.util.List;

import kasyan.catalog.dto.Book;
import kasyan.catalog.exceptions.AuthorNotFoundException;
import kasyan.catalog.exceptions.BookNotFoundException;

public interface BookDao {
	public List<Book> selectAllBooks();
	public Book selectBook(int id) throws BookNotFoundException;
	public List<Book> selectBooks(String title);
	public void updateBook(Book book);
	public void insertBook(Book book);
	public void deleteBook(int id) throws BookNotFoundException;
	public List<Book> selectBooksByAuthorId(int id) throws AuthorNotFoundException;
}
