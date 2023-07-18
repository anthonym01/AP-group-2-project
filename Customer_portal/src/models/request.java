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


@Entity
@Table(name = "request")
public class request implements Serializable {

	private static final Logger logger = LogManager.getLogger(request.class);

	// SELECT `ID`, `Service_type`, `description`, `created`, `created_by`,
	// `assigned_to`, `resolution_target`, `resolution_date`
	@Id
	@Column(name = "ID")
	private int Id;

	@Column(name = "Service_type")
	private String Service_type;
	@Column(name = "description")
	private String description;

	@Column(name = "created")
	private LocalDateTime date_created;

	@Column(name = "Created_by")
	private String created_by;

	@Column(name = "assigned_to")
	private int assigned_to_staff;

	@Column(name = "staff_Reply")
	private String staff_Reply;

	@Column(name = "resolution_target")
	private LocalDateTime resolution_target;

	@Column(name = "resolution_date")
	private LocalDateTime resolution_date;

	public request() {

	}

	public request(
			int Id,
			String Service_type,
			LocalDateTime date_created,
			String created_by,
			int assigned_to_staff,
			String staff_Reply, LocalDateTime resolution_date, LocalDateTime resolution_target) {
		super();
		this.Id = Id;
		this.Service_type = Service_type;
		this.date_created = date_created;
		this.assigned_to_staff = assigned_to_staff;
		this.staff_Reply = staff_Reply;
		this.resolution_date = resolution_date;
		this.resolution_target = resolution_target;

	}

	// Copy Constructor
	public request(request trivial) {
		this.Id = trivial.Id;
		this.Service_type = trivial.Service_type;
		this.date_created = trivial.date_created;
		this.assigned_to_staff = trivial.assigned_to_staff;
		this.staff_Reply = trivial.staff_Reply;
		this.resolution_date = trivial.resolution_date;
		this.resolution_target = trivial.resolution_target;
	}

	public void create(request payload) {

	}

	public void update(request payload) {


	}

	public List<request> readAll() {// Return a list of this class from the Database

		List<request> PayloadList = new ArrayList<>();
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

	public LocalDateTime getDate_created() {
		return date_created;
	}

	public void setAssigned_to_staff(int assigned_to_staff) {
		this.assigned_to_staff = assigned_to_staff;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
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

	public String getStaff_Reply() {
		return staff_Reply;
	}

	public void setDate_created(LocalDateTime date_created) {
		this.date_created = date_created;
	}

	@Override
	public String toString() {
		return "request: ";
	}
}
