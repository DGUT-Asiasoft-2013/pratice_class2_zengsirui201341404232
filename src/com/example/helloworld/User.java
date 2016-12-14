package com.example.helloworld;

import java.io.Serializable;

public class User implements Serializable{
	String account;
	String passwordHash;
	String name;
	String avatar;
	String email;
	private Integer id;

	public String getAccount() {
		return account;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public String getName() {
		return name;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getEmail() {
		return email;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
