package biz.pinedesk.slimtimer;

import java.util.Date;
import java.util.List;

public class Task {
    private int id;
    private String name;

    private Role role;

    // TODO convert the 'tags' list into a list.
    private String tags;
    private double hours;
    private List<Person> owners;
    private List<Person> reporters;
    private List<Person> coworkers;
    private Date completedOn;
    private Date updatedAt;
    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public List<Person> getOwners() {
        return owners;
    }

    public void setOwners(List<Person> owners) {
        this.owners = owners;
    }

    public List<Person> getReporters() {
        return reporters;
    }

    public void setReporters(List<Person> reporters) {
        this.reporters = reporters;
    }

    public List<Person> getCoworkers() {
        return coworkers;
    }

    public void setCoworkers(List<Person> coworkers) {
        this.coworkers = coworkers;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
