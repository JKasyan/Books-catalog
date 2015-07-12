package kasyan.catalog.dao;

import java.util.List;

import kasyan.catalog.dto.Book;

public interface BookDao {
	public List<Book> selectAllBooks();
	public Book selectBook(int id);
	public List<Book> selectBooks(String title);
	public void updateBook(Book book, List<Integer> ids);
	public void updateBook(Book book);
	public void insertBook(Book book);
	public void deleteBook(int id);
}
