package kasyan.catalog.dao;

import java.util.List;

import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.dto.User;

public interface CatalogDao {
	public List<Book> getAllBooks();
	public List<Author> getAllAuthors();
	public User getByEmail(String email);
}
