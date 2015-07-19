package kasyan.catalog.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import kasyan.catalog.dao.UserDao;
import kasyan.catalog.dto.User;
import kasyan.catalog.dto.UserRole;

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

	public void insertUser(User user) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction =  session.beginTransaction();
		session.save(user);
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole("ROLE_USER");
		session.save(userRole);
		transaction.commit();
	}

}
