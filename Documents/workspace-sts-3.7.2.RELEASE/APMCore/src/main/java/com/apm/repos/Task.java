package com.apm.repos;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "task_list")
public class Task {

	@Column(name = "task_name")
	private String taskName;
	
	@Column(name = "task_description")
	private String taskDescription;

	@Column(name = "task_priority")
	private String taskPriority;

	@Column(name = "task_status")
	private String taskStatus;

	@Column(name = "task_archived")
	private int taskArchived = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private int taskId;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public int isTaskArchived() {
		return taskArchived;
	}

	public void setTaskArchived(int taskArchived) {
		this.taskArchived = taskArchived;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "employee_tasks_xref", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "task_id"), inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
	private Set<Employee> employees;
	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Task [TaskId=" + taskId + ", taskName=" + taskName + ", taskDescription=" + taskDescription + ", taskPriority="
				+ taskPriority + ",taskStatus=" + taskStatus + "]";
	}

}
