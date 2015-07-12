package kasyan.catalog.dao;

import java.util.List;

import kasyan.catalog.dto.Author;

public interface AuthorDao {
	public List<Author> selectAllAuthors();
	public Author selectAuthor(int id);
	public void insertAuthor(Author author);
	public void updateAuthor(Author author);
	public List<Author> selectByList(List<Integer> ids);
}
