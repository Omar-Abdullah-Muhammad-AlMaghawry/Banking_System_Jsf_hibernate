package com.banking.orm;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "BANK_USER")
@DiscriminatorColumn(name = "ROLE",discriminatorType = DiscriminatorType.STRING)
@NamedQueries({ 
				@NamedQuery(name = "getUserNameByAttr",query = "select userName from UserDetail where :attr = :value"),
				@NamedQuery(name = "getPasswordByUserName",query = "select password from UserDetail where userName = :value"),
				@NamedQuery(name = "getEmailByUserName",query = "select mail from UserDetail where userName = :value"),
				@NamedQuery(name = "getNetSalaryByUserName", query = "select netSalary from UserDetail where userName = :value"),
				@NamedQuery(name = "getUserDetailByUserName", query = "from UserDetail where userName = :value"),
				@NamedQuery(name = "getAllUserNames", query = "select userName from UserDetail"),
				@NamedQuery(name = "getAllUserMails", query = "select mail from UserDetail"),
				@NamedQuery(name = "getAllUserDetails", query = "from UserDetail")
				
})
@NamedNativeQuery(name = "getTypeByUserName", 
					query = "select BANK_USER.ROLE from BANK_USER where USERNAME = :value")
public class UserDetail {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)	
	@Column(name = "ID")
	private long userId;
	private String userName;
	@Column(name = "BIRTH_DATE")
	private Date birthDate;
	@Column(name = "NET_SALARY")
	private double netSalary;
	private String address;
	private String mobile;
	private String mail;
	private String password;
	private String name;

	/////////////////CONSTRACTORS///////////////////////
	public UserDetail() {
		super();
	}

	public UserDetail(String userName, Date birthDate, double netSalary, String address, String mobile, String mail,
			String password, String name) {
		super();
		this.userName = userName;
		this.birthDate = birthDate;
		this.netSalary = netSalary;
		this.address = address;
		this.mobile = mobile;
		this.mail = mail;
		this.password = password;
		this.name = name;
	}

	//////////////////SETTER AND GETTER//////////////////
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public double getNetSalary() {
		return netSalary;
	}
	
	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
}
