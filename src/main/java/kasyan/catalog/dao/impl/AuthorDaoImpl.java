package kasyan.catalog.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kasyan.catalog.dao.AuthorDao;
import kasyan.catalog.dto.Author;

public class AuthorDaoImpl implements AuthorDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Author> selectAllAuthors() {
		Session session = sessionFactory.openSession();
		return session.createQuery("from Author").list();
	}

	public Author selectAuthor(int id) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("select A from Author A where A.id = :id");
		query.setInteger("id", id);
		return (Author) query.uniqueResult();
	}

	public void insertAuthor(Author author) {
	}

	@SuppressWarnings("unchecked")
	public List<Author> selectByList(List<Integer> ids) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("Select A from Author A where A.id IN (:ids)");
		query.setParameterList("ids", ids);
		return query.list();
	}

	public void updateAuthor(Author author) {
	}

}
