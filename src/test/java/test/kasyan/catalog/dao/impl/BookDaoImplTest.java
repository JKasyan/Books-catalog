package test.kasyan.catalog.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;
import kasyan.catalog.dao.BookDao;
import kasyan.catalog.dto.Book;

public class BookDaoImplTest extends TestCase{
	
	private ApplicationContext context;
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Override
	protected void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("catalog-context.xml");
	}
	
	public void testFindBooks(){
		BookDao bookDao = (BookDao) context.getBean("bookDao");
		logger.debug(bookDao);
		List<Book> books = bookDao.selectBooks("old");
		logger.debug(books.size());
		assertTrue(books.size() > 0);
	}
}
