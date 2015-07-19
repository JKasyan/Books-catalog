package kasyan.catalog.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.dto.User;
import kasyan.catalog.exceptions.AuthorNotFoundException;
import kasyan.catalog.exceptions.BookNotFoundException;

@Transactional
public interface CatalogService {
	
	public List<Book> getBooks();
	public List<Author> getAuthors();
	
	public Book getBookById(int id) throws BookNotFoundException;
	public Author getAuthorById(int id) throws AuthorNotFoundException;
	
	public void deleteBook(int id) throws BookNotFoundException;
	public void deleteAuthor(int id) throws AuthorNotFoundException;
	
	public void modifyBook(Book book);
	public void modifyAuthor(Author author);
	
	public List<Book> addBook(Book book);
	public void addAuthor(Author author);
	
	public List<Book> getBooksByAuthor(int id) throws AuthorNotFoundException;
	public List<Book> findBooksByTitle(String title);
	
	public void addUser(User user);
	
}
