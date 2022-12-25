package com.banking.orm;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("employee")
@NamedQueries({ 
	@NamedQuery(name = "getPositionByUserName",
			query = "select position from BankEmployee where userName = :value"),
})
public class BankEmployee extends UserDetail {

	private String position;

	public BankEmployee() {
		super();
	}

	public BankEmployee(String userName, String password, String position, Date birthDate, double netSalary,
			String address, String mobile, String mail, String name) {
		super(userName, birthDate, netSalary, address, mobile, mail, password,name);
		this.position = position;
	}

	///////////////////SETTER AND GETTER/////////////////////
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
