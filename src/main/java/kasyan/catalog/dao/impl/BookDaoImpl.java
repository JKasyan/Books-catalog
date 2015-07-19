package kasyan.catalog.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import kasyan.catalog.dao.BookDao;
import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.exceptions.AuthorNotFoundException;
import kasyan.catalog.exceptions.BookNotFoundException;

public class BookDaoImpl implements BookDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Book> selectAllBooks() {
		Session session = sessionFactory.openSession();
		return session.createQuery("from Book").list();
	}

	public Book selectBook(int id) throws BookNotFoundException {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("select B from Book B where B.id = :id");
		query.setInteger("id", id);
		if(query.uniqueResult() == null) throw new BookNotFoundException(id);
		return (Book) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> selectBooks(String title) {
		Session session = this.sessionFactory.openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		try {
			fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Transaction tr = fullTextSession.beginTransaction();
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Book.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onFields("title").matching(title).createQuery();
		Query queryHib = fullTextSession.createFullTextQuery(query, Book.class);
		List<Book> list = queryHib.list();
		tr.commit();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private List<Author> selectAuthorsByNamesAndSecondNames(List<Author> authors){
		List<String> authorsNames = new ArrayList<String>();
		List<String> authorsSecondNames = new ArrayList<String>();
		for(Author author:authors){
			authorsNames.add(author.getName());
			authorsSecondNames.add(author.getSecondName());
		}
		logger.debug("List of authors names: " + authorsNames);
		logger.debug("List of authors second names: " + authorsSecondNames);
		Session session = this.sessionFactory.openSession();
		Query query = session
				.createQuery("Select A from Author A where A.name in (:authorsNames) and A.secondName in (:authorsSecondNames)");
		query.setParameterList("authorsNames", authorsNames);
		query.setParameterList("authorsSecondNames", authorsSecondNames);
		return query.list();
	}
	
	public void insertBook(Book book) {
		logger.debug("Inserting new book: " + book);
		List<Author> authors = selectAuthorsByNamesAndSecondNames(book.getAuthors());
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		logger.debug("Authors for new book: " + authors);
		book.setAuthors(authors);
		session.saveOrUpdate(book);
		transaction.commit();
	}
	
	public void updateBook(Book book) {
		List<Author> authors = selectAuthorsByNamesAndSecondNames(book.getAuthors());
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Book bookForUpdate = (Book) session.get(Book.class, book.getId());
		if(book.getTitle() != null){
			bookForUpdate.setTitle(book.getTitle());
		}
		if(book.getShortDescription() != null){
			bookForUpdate.setShortDescription(book.getShortDescription());
		}
		if(book.getDatePublish() != null){
			bookForUpdate.setDatePublish(book.getDatePublish());
		}
		if(authors != null){
			bookForUpdate.setAuthors(authors);
		}
		session.update(bookForUpdate);
		transaction.commit();
		session.close();
	}
	
	public void deleteBook(int id) throws BookNotFoundException {
		Session session = this.sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		Query query = session.createQuery("Select B from Book B where B.id = :id");
		query.setInteger("id", id);
		if(query.uniqueResult() == null) throw new BookNotFoundException(id);
		Book book = (Book) query.uniqueResult();
		session.delete(book);
		tr.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Book> selectBooksByAuthorId(int id) throws AuthorNotFoundException {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("Select A.books from Author A where A.id = :id");
		query.setInteger("id", id);
		if(query.list() == null) throw new AuthorNotFoundException(id);
		return query.list();
	}

}
