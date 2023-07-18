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
@Table(name = "complaint_reply")
public class complaint_reply implements Serializable {

	private static final Logger logger = LogManager.getLogger(complaint_reply.class);

	// SELECT `ID`, `BelongsTo`, `created`, `Created_by`, `text`
	@Id
	@Column(name = "ID")
	private int Id;

	@Column(name = "BelongsTo") // relates to complaint id, not a custiomer or employee
	private int BelongsTo;

	@Column(name = "created")
	private LocalDateTime date_created;

	@Column(name = "Created_by")
	private int created_by;

	@Column(name = "text")
	private String BodyText;

	public complaint_reply() {

	}

	public complaint_reply(
			int Id,
			int BelongsTo,
			LocalDateTime date_created,
			int created_by,
			String BodyText) {
		super();
		this.Id = Id;
		this.BelongsTo = BelongsTo;
		this.date_created = date_created;
		this.created_by = created_by;
		this.BodyText = BodyText;
	}

	// Copy Constructor
	public complaint_reply(complaint_reply trivial) {
		this.Id = trivial.Id;
		this.BelongsTo = trivial.BelongsTo;
		this.date_created = trivial.date_created;
		this.created_by = trivial.created_by;
		this.BodyText = trivial.BodyText;
	}

	public void create(complaint_reply payload) {
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

	public void update(complaint_reply payload) {
		try {
			Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			complaint_reply transactinator = (complaint_reply) session.get(complaint_reply.class, payload.Id);

			transactinator.BelongsTo = payload.BelongsTo;
			transactinator.date_created = payload.date_created;
			transactinator.created_by = payload.created_by;
			transactinator.BodyText = payload.BodyText;

			session.update(transactinator);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
		}

	}

	public List<complaint_reply> readAll() {// Return a list of this class from the Database

		List<complaint_reply> PayloadList = new ArrayList<>();
		try {
			Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			PayloadList = (List<complaint_reply>) session.createQuery("FROM complaint_reply").getResultList();
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

	public void delete(String ID) {// Remove Via ID
		try {

			Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();

			complaint_reply s = (complaint_reply) session.get(complaint_reply.class, ID);
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

	public int getBelongsTo() {
		return BelongsTo;
	}

	public String getBodyText() {
		return BodyText;
	}

	public int getCreated_by() {
		return created_by;
	}

	public void setBelongsTo(int belongsTo) {
		BelongsTo = belongsTo;
	}

	public void setBodyText(String bodyText) {
		BodyText = bodyText;
	}

	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public void setDate_created(LocalDateTime date_created) {
		this.date_created = date_created;
	}

	@Override
	public String toString() {
		return "Complaint's reply: ";
	}
}
