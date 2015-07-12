package test.kasyan.catalog.dto.book;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;

public class BookTest extends TestCase{
	
	private Book bookOne;
	private Book bookTwo;
	private Author author;
	
	@Override
	protected void setUp() throws Exception {
		author = new Author();
		author.setId(1);
		author.setName("Ernest");
		author.setSecondName("Hamingway");
		bookOne = new Book();
		bookOne.setId(1);
		bookOne.setTitle("The old man and the see");
		bookOne.setDatePublish("1952");
		bookOne.setShortDescription("Philosophical work");
		Set<Author> authors = new HashSet<Author>();
		authors.add(author);
		bookOne.setAuthors(authors);
		Set<Book> books = new HashSet<Book>();
		books.add(bookOne);
		author.setBooks(books);
		//
		bookTwo = new Book();
		bookTwo.setId(1);
		bookTwo.setTitle("The old man and the see");
		bookTwo.setDatePublish("1952");
		bookTwo.setShortDescription("Philosophical work");
		bookTwo.setAuthors(authors);
		
	}
}
