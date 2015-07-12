package kasyan.catalog.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;

@Transactional
public interface CatalogService {
	public List<Book> getBooks();
	public List<Author> getAuthors();
	public List<Book> findBooksByTitle(String title);
	public Book getBookById(int id);
	public List<Book> modifyBook(Book book, String[] authors);
	public List<Book> addBook(Book book, String[] authorsIds);
	public void deleteBook(int id);
}
