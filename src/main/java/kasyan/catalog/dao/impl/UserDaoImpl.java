package kasyan.catalog.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import kasyan.catalog.dao.UserDao;
import kasyan.catalog.dto.User;

public class UserDaoImpl implements UserDao {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public User selectUser(String email) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("select U from User U where U.email = :email");
		query.setString("email", email);
		return (User) query.uniqueResult();
	}

	public User selectUser(int id) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("select U from User U where U.id = :id");
		query.setInteger("id", id);
		return (User) query.uniqueResult();
	}

}
