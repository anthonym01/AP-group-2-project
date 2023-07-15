package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import factory.SessionFactoryBuilder;

@Entity
@Table(name = "students")

public class example {

	// Maps variables to table columns
	@Id
	@Column(name = "id")
	int id;

	@Column(name = "name")
	String firstName;

	@Column(name = "lastname")
	String lastName;

	@Column(name = "email")
	String email;

	public example() {

	}

	public example(int id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public void create() {
		Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();

		Transaction transaction = session.beginTransaction();
		session.save(new example(1, "Leo", "Brom", "@gmail."));
		transaction.commit();
		session.close();
	}

	public void update() {
		Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		example s = (example) session.get(example.class, this.id);
		s.setFirstName(this.firstName);
		s.setLastName(this.lastName);
		session.update(s);
		transaction.commit();
		session.close();

	}

	public List<example> readAll() {
		List<example> studentList = new ArrayList<>();
		Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();

		studentList = (List<example>) session.createQuery("FROM Student").getResultList();
		transaction.commit();
		session.close();
		return studentList;
	}

	public void delete() {
		Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();

		example s = (example) session.get(example.class, this.id);
		session.delete(s);
		transaction.commit();
		session.close();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
