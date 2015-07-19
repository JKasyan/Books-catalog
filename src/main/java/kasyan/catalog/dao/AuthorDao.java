package kasyan.catalog.dao;

import java.util.List;

import kasyan.catalog.dto.Author;
import kasyan.catalog.exceptions.AuthorNotFoundException;

public interface AuthorDao {
	public List<Author> selectAllAuthors();
	public Author selectAuthor(int id) throws AuthorNotFoundException;
	public void insertAuthor(Author author);
	public void updateAuthor(Author author);
	public List<Author> selectByList(List<Integer> ids);
	public void deleteAuthor(int id) throws AuthorNotFoundException;
}
