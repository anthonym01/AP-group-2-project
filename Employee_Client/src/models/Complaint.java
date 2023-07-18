package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Complaint implements Serializable {
    private int id;
    private String serviceType;
    private LocalDateTime date_created;
    private String createdBy;
    private String title;
    private String description;
    private int assigned_to;

    public Complaint() {
        this.id = 1;
        this.serviceType = "Broadband";
        this.date_created = LocalDateTime.now();
        this.createdBy = "Xavier Kirlew";
        this.title = "No Wi-Fi";
        this.description = "There no wifi";
        this.assigned_to = 4;
    }

    public Complaint(int id, String serviceType, LocalDateTime date_created, String createdBy, String title, String description, int assigned_to) {
        this.id = id;
        this.serviceType = serviceType;
        this.date_created = date_created;
        this.createdBy = createdBy;
        this.title = title;
        this.description = description;
        this.assigned_to = assigned_to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(int assigned_to) {
        this.assigned_to = assigned_to;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", serviceType='" + serviceType + '\'' +
                ", date_created=" + date_created +
                ", createdBy='" + createdBy + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", assigned_to=" + assigned_to +
                '}';
    }
}
