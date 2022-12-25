package com.banking.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class Services {
	SessionFactory sessionFactory;
	Session session;

	public Services() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public void persist(Object o) throws Exception {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.persist(o);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			throw new Exception("cannotPersist");
		} finally {
			session.close();
		}
	}

	public void save(Object o) throws Exception {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(o);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			throw new Exception("cannotSave");
		} finally {
			session.close();
		}
	}

	public void merge(Object o) throws Exception {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.merge(o);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			throw new Exception("cannotMerge");
		} finally {
			session.close();
		}

	}

	public List<Object> readNamedQueryList(String namedQuery, String valName, Boolean attrExist) throws Exception {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.getNamedQuery(namedQuery);

			if (attrExist) {
				query.setParameter("value", valName);
			}
			List<Object> outputList = (List<Object>) query.list();
			session.getTransaction().commit();
			session.close();
			return outputList;
		} catch (Exception e) {
			throw new Exception("cannotFindObjectList");
		} finally {
			session.close();
		}
	}

	public Object readNamedQueryObject(String namedQuery, String valName, Boolean attrExist) throws Exception {
		session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			Query query = session.getNamedQuery(namedQuery);
			if (attrExist) {
				query.setParameter("value", valName);
			}
			Object outputList = query.getSingleResult();
			session.getTransaction().commit();
			session.close();
			return outputList;
		} catch (Exception e) {
			throw new Exception("cannotFindTheObject");
		} finally {
			session.close();
		}
	}

	public Object readObjectById(Class c, String valName) throws Exception {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Object outputObject = session.get(c, Integer.parseInt(valName));
			session.getTransaction().commit();
			session.close();
			return outputObject;
		} catch (Exception e) {
			throw new Exception("cannotFindTheObject");
		} finally {
			session.close();
		}
	}

	public void delete(Object o) throws Exception {
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(o);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			throw new Exception("cannotDelete");
		} finally {
			session.close();
		}
	}

	public void update(Object o) 
		throws Exception{
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(o);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			throw new Exception("cannotUpdateTheObject");
		} finally {
			session.close();
		}
	}

}
