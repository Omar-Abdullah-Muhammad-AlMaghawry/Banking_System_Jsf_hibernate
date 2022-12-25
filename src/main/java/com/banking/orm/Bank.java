package com.banking.orm;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Bank {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long bankId;
	@Column(name = "BRANCH_ADDRESS")
	private String branchAddress;
	
	@OneToMany(mappedBy = "bank",cascade = CascadeType.ALL)
	private Collection<Client> clientList = new ArrayList<Client>();
	@OneToMany(mappedBy = "bank",cascade = CascadeType.ALL)
	private Collection<Account> accountList = new ArrayList<Account>();


	public Bank() {
		super();
	}

	public Bank(String branchAddress) {
		super();
		this.branchAddress = branchAddress;
	}	

///////////////////SETTER AND GETTER///////////////////////
	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public Collection<Client> getClientList() {
		return clientList;
	}

	public void setClientList(Collection<Client> clientList) {
		this.clientList = clientList;
	}

	public Collection<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(Collection<Account> accountList) {
		this.accountList = accountList;
	}

}
