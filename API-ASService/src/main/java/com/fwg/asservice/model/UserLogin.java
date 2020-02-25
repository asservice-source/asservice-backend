package com.fwg.asservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "UserLogin")
public class UserLogin {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "UserName")
	private String userName;
	
	@Column(name = "UserPassword")
	private String password;
	
	@JsonProperty(value="isActive")
	@Column(name = "IsActive")
	private boolean isActive;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ID", insertable = false, updatable = false)
	private Responsibility responsibility;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Responsibility getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Responsibility responsibility) {
		this.responsibility = responsibility;
	}
}