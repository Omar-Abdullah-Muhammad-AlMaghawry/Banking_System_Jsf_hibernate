package com.banking.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("client")
public class Client extends UserDetail {

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private Collection<Account> accountList = new ArrayList<Account>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BANK_ID")
	private Bank bank;

	public Client() {
		super();
	}

	public Client(String userName, String password, Date birthDate, double netSalary, String address, String mobile,
			String mail, String name) {
		super(userName, birthDate, netSalary, address, mobile, mail, password,name);
	}

	//////////////////STTER AND GETTER/////////////////
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Collection<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(Collection<Account> accountList) {
		this.accountList = accountList;
	}

}
