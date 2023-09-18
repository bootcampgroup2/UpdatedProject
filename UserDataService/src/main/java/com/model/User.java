package com.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User {

	private String userName;
	private String password;
	private String email;
	private String dOB;

	private Boolean notificationsAllowed;

	public User(){}
	public User(String userName, String password, String email, String dOB, Boolean notifications) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.dOB = dOB;
		this.notificationsAllowed = notifications;
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
	public String getEmail() {
		return email;
	}

	public Boolean getNotificationsAllowed() {
		return notificationsAllowed;
	}

	public void setNotificationsAllowed(Boolean notifications) {
		this.notificationsAllowed = notifications;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getdOB() {
		return dOB;
	}
	public void setdOB(String dOB) {
		this.dOB = dOB;
	}

	@Override
	public String toString() {
		return "User{" +
				"userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				", dOB='" + dOB + '\'' +
				", notifications=" + notificationsAllowed +
				'}';
	}


}
