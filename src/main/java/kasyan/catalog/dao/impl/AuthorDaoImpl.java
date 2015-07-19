package kasyan.catalog.dao.impl;


import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import kasyan.catalog.dao.AuthorDao;
import kasyan.catalog.dto.Author;
import kasyan.catalog.dto.Book;
import kasyan.catalog.exceptions.AuthorNotFoundException;

public class AuthorDaoImpl implements AuthorDao {

	@Autowired
	private SessionFactory sessionFactory;
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Author> selectAllAuthors() {
		Session session = sessionFactory.openSession();
		return session.createQuery("from Author").list();
	}

	public Author selectAuthor(int id) throws AuthorNotFoundException {
		Session session = this.sessionFactory.openSession();
		Query query = session
				.createQuery("select A from Author A where A.id = :id");
		query.setInteger("id", id);
		if (query.uniqueResult() == null)
			throw new AuthorNotFoundException(id);
		return (Author) query.uniqueResult();
	}

	public void insertAuthor(Author author) {
		logger.debug("Inserting new author: " + author);
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(author);
		logger.debug("Succes saving");
		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	public List<Author> selectByList(List<Integer> ids) {
		logger.debug("Ids of authors: " + ids);
		Session session = this.sessionFactory.openSession();
		Query query = session
				.createQuery("Select A from Author A where A.id IN (:ids)");
		query.setParameterList("ids", ids);
		return query.list();
	}

	public void updateAuthor(Author author) {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Author updatedAuthor = (Author) session.get(Author.class,
				author.getId());
		updatedAuthor.setName(author.getName());
		updatedAuthor.setSecondName(author.getSecondName());
		session.update(updatedAuthor);
		transaction.commit();
	}

	public void deleteAuthor(int id) throws AuthorNotFoundException {
		Session session = this.sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Author author = (Author) session.get(Author.class, id);
		if(author == null ) throw new AuthorNotFoundException(id);
		List<Book> books =  author.getBooks();
		logger.debug("Author have books: "+books);
		Iterator<Book> bookIterator = books.iterator();
		while(bookIterator.hasNext()){
			Book book = bookIterator.next();
			if(book.getAuthors().size() == 1){
				logger.debug("Book "+book.getTitle()+" wiil be deleted");
				session.delete(book);
			}
		}
		session.delete(author);
		transaction.commit();
	}

}
