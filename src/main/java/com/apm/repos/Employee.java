package com.apm.repos;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
@NamedEntityGraph(name = "Task.detail",
attributeNodes = @NamedAttributeNode("tasks"))
public class Employee {
	
	List<Task> tasks = new ArrayList<Task>();
	
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "joining_date")
	private Date joiningDate;

	@Column(name = "salary")
	private Double salary;

	@Column(name = "ssn")
	private String ssn;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	@ManyToMany(mappedBy = "employees")
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", Joining Date=" + joiningDate + ", salary=" + salary
				+ ",ssn=" + ssn + "]";
	}

}
