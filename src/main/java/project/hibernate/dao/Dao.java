package project.hibernate.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import project.hibernate.util.HibernateUtil;

public class Dao {
	protected Session session;
	protected Transaction transaction;
	public Session openSession() {
		session = HibernateUtil.getSessionFactory().openSession();
		return session;
	}
	public Session openSessionWithTransaction() {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		return session;
	}
	public void closeSession(Session session) {
		session.close();
	}
	public void closeSessionWithTransaction(Session session) {
		transaction.commit();
		session.close();
	}
	public void generateDatabase() {
		session = HibernateUtil.getSessionFactory().openSession();
		session.close();
	
	}
}
