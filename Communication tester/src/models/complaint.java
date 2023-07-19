package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

//import factory.SessionFactoryBuilder;

@Entity
@Table(name = "complaint")
public class complaint implements Serializable {
	// SELECT `Service_type`, `id`, `date_created`, `created_by`, `Title`,
	// `Description`, `assigned_to` FROM `complaint` WHERE 1
	@Id
	@Column(name = "id")
	private int Id;

	@Column(name = "Service_type")
	private String Service_type;

	@Column(name = "date_created")
	private LocalDateTime date_created;

	@Column(name = "created_by")
	private String created_by;

	@Column(name = "Title")
	private String Title;

	@Column(name = "Description")
	private String Description;

	@Column(name = "assigned_to")
	@JoinColumn(name = "assigned_to", nullable = false, foreignKey = @ForeignKey(name = "ID_number", foreignKeyDefinition = "FOREIGN KEY (assigned_to) REFERENCES Employee(ID_number) ON DELETE CASCADE"))
	private int assigned_to;

	// @OneToMany(mappedBy = "Employee", cascade = CascadeType.REMOVE)
	// @ManyToOne
	//
	public complaint() {

	}

	public complaint(
			int Id,
			String Service_type,
			LocalDateTime date_created,
			String created_by,
			String Title,
			String Description,
			int assigned_to) {
		super();
		this.Id = Id;
		this.Service_type = Service_type;
		this.date_created = date_created;
		this.created_by = created_by;
		this.Title = Title;
		this.Description = Description;
		this.assigned_to = assigned_to;
	}

	// Copy Constructor
	public complaint(complaint trivial) {
		this.Id = trivial.Id;
		this.Service_type = trivial.Service_type;
		this.date_created = trivial.date_created;
		this.created_by = trivial.created_by;
		this.Title = trivial.Title;
		this.Description = trivial.Description;
		this.assigned_to = trivial.assigned_to;
	}

	public void create(complaint payload) {
		/*try {
			Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();

			Transaction transaction = session.beginTransaction();
			session.save(payload);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}*/
	}

	public void update(complaint payload) {
		/*Session session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		complaint transactinator = (complaint) session.get(complaint.class, payload.Id);

		//transactinator.Description = payload.Description;
		transactinator.Service_type = payload.Service_type;
		transactinator.date_created = payload.date_created;
		transactinator.created_by = payload.created_by;
		transactinator.Title = payload.Title;
		transactinator.Description = payload.Description;
		transactinator.assigned_to = payload.assigned_to;
		session.update(transactinator);
		transaction.commit();
		session.close();*/
	}

	public List<complaint> readAll() {// Return a list of this class from the Database
		List<complaint> PayloadList = new ArrayList<>();
		return PayloadList;
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
	public int getAssigned_to() {
		return assigned_to;
	}
	public String getCreated_by() {
		return created_by;
	}
	public LocalDateTime getDate_created() {
		return date_created;
	}
	public String getDescription() {
		return Description;
	}
	public String getService_type() {
		return Service_type;
	}
	public String getTitle() {
		return Title;
	}
	public void setAssigned_to(int assigned_to) {
		this.assigned_to = assigned_to;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public void setDate_created(LocalDateTime date_created) {
		this.date_created = date_created;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public void setService_type(String service_type) {
		Service_type = service_type;
	}
	public void setTitle(String title) {
		Title = title;
	}

	@Override
	public String toString() {
		return "Complaint: ";
	}
}
