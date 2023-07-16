package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import factory.SessionFactoryBuilder;

@Entity
@Table(name = "customers")
public class Customer implements Serializable{

	@Id
	@Column(name = "ID")
	private String Id;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "Email")
	private String email;

	@Column(name = "Contact_number")
	private String telNum;

	public Customer() {

	}

	public Customer(String Id, String password, String firstName, String lastName, String email, String telNum) {
		super();
		this.Id = Id;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telNum = telNum;
	}

	// Copy Constructor
	public Customer(Customer customer) {
		Id = customer.Id;
		password = customer.password;
		firstName = customer.firstName;
		lastName = customer.lastName;
		email = customer.email;
		telNum = customer.telNum;

	}

	public void create(Customer payload) {
		Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();

		Transaction transaction = session.beginTransaction();
		session.save(payload);
		transaction.commit();
		session.close();
	}
	
	public void update() {
		Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Customer s = (Customer) session.get(Customer.class, this.Id);
		s.setFirstName(this.firstName);
		s.setLastName(this.lastName);
		session.update(s);
		transaction.commit();
		session.close();

	}

	public List<Customer> readAll() {//Return a list of customers from the Database
		List<Customer> CustomerList = new ArrayList<>();
		Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();

		CustomerList = (List<Customer>) session.createQuery("FROM Customer").getResultList();
		transaction.commit();
		session.close();
		System.out.println(CustomerList);
		return CustomerList;
	}

	public void delete(String ID) {//Remove Via ID
		Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();

		Customer s = (Customer) session.get(Customer.class, ID);
		session.delete(s);
		transaction.commit();
		session.close();
	}
	

	// Getters and Setters

	public String getId() {
		return Id;
	}

	public void setId(String Id) {
		this.Id = Id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	@Override
	public String toString() {
		return "Customer [Id=" + Id + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", telNum=" + telNum + "]";
	}
}
