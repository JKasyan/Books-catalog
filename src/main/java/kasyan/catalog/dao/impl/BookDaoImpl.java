package kasyan.catalog.dao.impl;

import java.util.HashSet;
import java.util.List;

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

public class BookDaoImpl implements BookDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Book> selectAllBooks() {
		Session session = sessionFactory.openSession();
		return session.createQuery("from Book").list();
	}

	public Book selectBook(int id) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("select B from Book B where B.id = :id");
		query.setInteger("id", id);
		return (Book) query.uniqueResult();
	}

	public void updateBook(Book book, List<Integer> authorsId) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		//
		Query queryBook = session.createQuery("select B from Book B where B.id = :id");
		queryBook.setInteger("id", book.getId());
		Book changedBook = (Book) queryBook.uniqueResult();
		//
		changedBook.setTitle(book.getTitle());
		changedBook.setShortDescription(book.getShortDescription());
		changedBook.setDatePublish(book.getDatePublish());
		//
		Query query = session.createQuery("Select A from Author A where A.id IN (:authorsId)");
		query.setParameterList("authorsId", authorsId);
		@SuppressWarnings("unchecked")
		List<Author> authors = query.list();
		changedBook.setAuthors(new HashSet<Author>(authors));
		//
		session.save(changedBook);
		transaction.commit();
		session.close();
	}
	
	
	public void updateBook(Book book) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Query queryBook = session.createQuery("select B from Book B where B.id = :id");
		queryBook.setInteger("id", book.getId());
		Book changedBook = (Book) queryBook.uniqueResult();
		changedBook.setTitle(book.getTitle());
		changedBook.setShortDescription(book.getShortDescription());
		changedBook.setDatePublish(book.getDatePublish());
		session.save(changedBook);
		transaction.commit();
		session.close();
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
		org.apache.lucene.search.Query query = qb.keyword().onFields("title","shortDescription","authors.name").matching(title).createQuery();
		Query queryHib = fullTextSession.createFullTextQuery(query, Book.class);
		List<Book> list = queryHib.list();
		tr.commit();
		return list;
	}

	public void insertBook(Book book) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(book);
		transaction.commit();
	}

	public void deleteBook(int id) {
		Session session = this.sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		Query query = session.createQuery("Select B from Book B where B.id = :id");
		query.setInteger("id", id);
		Book book = (Book) query.uniqueResult();
		session.delete(book);
		tr.commit();
	}

}
