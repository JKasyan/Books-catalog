package kasyan.catalog.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import kasyan.catalog.dao.CatalogDao;
import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.dto.User;

public class CatalogDaoImpl implements CatalogDao{
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Book> getAllBooks() {
		Session session = sessionFactory.openSession();
		return session.createQuery("from Book").list();
	}

	@SuppressWarnings("unchecked")
	public List<Author> getAllAuthors() {
		Session session = sessionFactory.openSession();
		return session.createQuery("from Author").list();
	}

	public User getByEmail(String email) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("select U from User U where U.email = :email");
		query.setString("email", email);
		return (User) query.uniqueResult();
	}

}
