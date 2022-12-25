package com.banking.orm;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
  
@NamedQueries(  { @NamedQuery(name = "getAccountByAccountId",query = "from Account where accountId= :value"),
		@NamedQuery(name = "getAmountByAccountId",query = "select amount from Account where accountId = :value"),
		@NamedQuery(name = "getAllAccounts", query = "from Account")
		
})
@NamedNativeQuery(name = "getAccountByClientId", query = "select * from ACCOUNT where CLIENT_ID = :value", resultClass = Account.class)

public class Account {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long accountId;
	private double amount;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CLIENT_ID")
	private Client client;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BANK_ID")
	private Bank bank;
	
	public Account() {
		super();
	}

	public Account( double amount) {
		super();
		this.amount = amount;
	}	
	/////////////SETTER AND GETTER////////////////
	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public long getAccountId() {
		return accountId;
	}


	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	} 
		
}
