package com.mypack.dao;

import com.mypack.config.HibernateConnector;
import com.mypack.pojo.Student;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentDAO {
	@SuppressWarnings("unchecked")
	public List<Student> listStudent() {

		try (Session session = HibernateConnector.getInstance().getSession();) {
			Query query = session.createQuery("from Student s");

			@SuppressWarnings("rawtypes")
			List queryList = query.list();
			if (queryList != null && queryList.isEmpty()) {
				return null;
			} else {
				System.out.println("list " + queryList);
				return (List<Student>) queryList;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Student findStudentById(int id) {

		try (Session session = HibernateConnector.getInstance().getSession();) {

			Query query = session.createQuery("from Student s where s.id = :id");
			query.setParameter("id", id);

			@SuppressWarnings("rawtypes")
			List queryList = query.list();
			if (queryList != null && queryList.isEmpty()) {
				return null;
			} else {
				return (Student) queryList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateStudent(Student student) {

		try (Session session = HibernateConnector.getInstance().getSession();) {

			session.saveOrUpdate(student);
			session.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Student addStudent(Student student) {
		Transaction transaction = null;
		try (Session session = HibernateConnector.getInstance().getSession();) {
			transaction = session.beginTransaction();
			session.save(student);
			transaction.commit();
			return student;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void deleteStudent(int id) {

		try (Session session = HibernateConnector.getInstance().getSession();) {

			Transaction beginTransaction = session.beginTransaction();
			Query createQuery = session.createQuery("delete from Student s where s.id =:id");
			createQuery.setParameter("id", id);
			createQuery.executeUpdate();
			beginTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
