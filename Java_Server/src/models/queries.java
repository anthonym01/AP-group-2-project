package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import factory.SessionFactoryBuilder;

@Entity
@Table(name = "queries")
public class queries implements Serializable {

	private static final Logger logger = LogManager.getLogger(queries.class);

	// SELECT `ID`, `question`, `created`, `created_by`, `assigned_to`,
	// `staff_Reply`
	@Id
	@Column(name = "ID")
	private int Id;

	@Column(name = "question")
	private String question;

	@Column(name = "created")
	private LocalDateTime date_created;

	@Column(name = "Created_by")
	private String created_by;

	@Column(name = "assigned_to")
	private int assigned_to_staff;

	@Column(name = "staff_Reply")
	private String staff_Reply;

	public queries() {

	}

	public queries(
			int Id,
			String question,
			LocalDateTime date_created,
			String created_by,
			int assigned_to_staff,
			String staff_Reply) {
		super();
		this.Id = Id;
		this.question = question;
		this.date_created = date_created;
		this.assigned_to_staff = assigned_to_staff;
		this.staff_Reply = staff_Reply;
	}

	// Copy Constructor
	public queries(queries trivial) {
		this.Id = trivial.Id;
		this.question = trivial.question;
		this.date_created = trivial.date_created;
		this.assigned_to_staff = trivial.assigned_to_staff;
		this.staff_Reply = trivial.staff_Reply;
	}

	public void create(queries payload) {
		try {
			Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();

			Transaction transaction = session.beginTransaction();
			session.save(payload);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
		}
	}

	public void update(queries payload) {
		try {
			Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			queries transactinator = (queries) session.get(queries.class, payload.Id);

			transactinator.question = payload.question;
			transactinator.date_created = payload.date_created;
			transactinator.assigned_to_staff = payload.assigned_to_staff;
			transactinator.staff_Reply = payload.staff_Reply;

			session.update(transactinator);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
		}

	}

	public List<queries> readAll() {// Return a list of this class from the Database

		List<queries> PayloadList = new ArrayList<>();
		try {
			Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			PayloadList = (List<queries>) session.createQuery("FROM queries").getResultList();
			transaction.commit();
			session.close();
			System.out.println(PayloadList);
			return PayloadList;
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
			return PayloadList;
		}

	}

	public void delete(int ID) {// Remove Via ID
		try {

			Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();

			queries s = (queries) session.get(queries.class, ID);
			session.delete(s);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());

		}
	}

	// Getters and Setters

	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

	public LocalDateTime getDate_created() {
		return date_created;
	}

	public void setAssigned_to_staff(int assigned_to_staff) {
		this.assigned_to_staff = assigned_to_staff;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setStaff_Reply(String staff_Reply) {
		this.staff_Reply = staff_Reply;
	}

	public int getAssigned_to_staff() {
		return assigned_to_staff;
	}

	public String getCreated_by() {
		return created_by;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getQuestion() {
		return question;
	}

	public String getStaff_Reply() {
		return staff_Reply;
	}

	public void setDate_created(LocalDateTime date_created) {
		this.date_created = date_created;
	}

	@Override
	public String toString() {
		return "query: ";
	}
}
