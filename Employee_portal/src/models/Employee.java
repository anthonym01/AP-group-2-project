package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "Employee")
public class Employee implements Serializable {
	// `type`, `ID_number`, `password`, `first_name`, `last_name`, `Email`,
	// `Contact_number`
	@Id
	@Column(name = "ID_number")
	// @OneToMany(mappedBy = "Employee", cascade = CascadeType.REMOVE)
	//@ManyToOne
	//@JoinColumn(name = "ID_number", nullable = false, foreignKey = @ForeignKey(name = "assigned_to",
			// foreignKeyDefinition = "FOREIGN KEY (ID_number) REFERENCES student(id) ON
			// DELETE CASCADE"SS
			//foreignKeyDefinition = "FOREIGN KEY (ID_number) REFERENCES Employee(id) ON DELETE CASCADE"))
	private int Id;

	@Column(name = "type")
	private int type;

	@Column(name = "password")
	private String password;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "Email")
	private String email;

	@Column(name = "Contact_number")
	private String telNum;

	public Employee() {

	}

	public Employee(int Id, int type, String password, String lastName, String firstName, String email, String telNum) {
		super();
		this.telNum = telNum;
		this.Id = Id;
		this.type = type;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	// Copy Constructor
	public Employee(Employee fireable) {
		this.telNum = fireable.telNum;
		this.Id = fireable.Id;
		this.type = fireable.type;
		this.email = fireable.email;
		this.firstName = fireable.firstName;
		this.lastName = fireable.lastName;
		this.password = fireable.password;
	}

	public void create(Employee payload) {

	}

	public void update(Employee payload) {

	}

	public List<Employee> readAll() {// Return a list of Employees from the Database
		List<Employee> EmployeeList = new ArrayList<>();

		return EmployeeList;
	}

	public void delete(int ID) {// Remove Via ID

	}

	// Getters and Setters

	public int getId() {
		return Id;
	}

	public void setId(int Id) {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Employee [Id=" + Id + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", telNum=" + telNum + "]";
	}
}
