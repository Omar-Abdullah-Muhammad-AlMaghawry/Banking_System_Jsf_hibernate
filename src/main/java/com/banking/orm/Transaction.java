package com.banking.orm;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.banking.enums.TransactionStatusEnum;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "getTransactionByTransactionId",query = "from Transaction where transactionId = :value"),
	@NamedQuery(name = "getAllTransactions", query = "from Transaction")
		
})
@NamedNativeQuery(name = "getTransactionByFromAccountId", query = "select * from TRANSACTION where FROMACCOUNT_ID = :value", resultClass = Transaction.class)
@NamedNativeQuery(name = "getTransactionByUserId", query = "select * "
		+ " FROM ACCOUNT INNER JOIN TRANSACTION ON ACCOUNT.ID = TRANSACTION.FROMACCOUNT_ID "
		+ "INNER JOIN BANK_USER ON ACCOUNT.CLIENT_ID = BANK_USER.ID "
		+ "WHERE ACCOUNT.CLIENT_ID = :value", resultClass = Transaction.class)
public class Transaction {
//	public enum Status {APPROVED,PENDING,REJECTED};
	
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long transactionId;
	@Column(name = "TRANSACTION_DATE")
	private Date transactionDate;
	private String description;
	private double amount;
	@Column(name = "TRANSACTION_STATUS")
	private TransactionStatusEnum transactionStatus = TransactionStatusEnum.PENDING;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TOACCOUNT_ID")
	private Account toAccount ;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FROMACCOUNT_ID")
	private Account fromAccount;

	
	public Transaction() {
		super();}
	

	public Transaction(Date transactionDate, String description, double amount, TransactionStatusEnum transactionStatus, Account fromAccount,
		Account toAccount) {
	super();
	this.transactionDate = transactionDate;
	this.description = description;
	this.amount = amount;
	this.transactionStatus = transactionStatus;
	this.fromAccount = fromAccount;
	this.toAccount = toAccount;
}

/////////////////////////SETTER AND GETTER///////////////////////////////
	public TransactionStatusEnum getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatusEnum transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Account getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Account getToAccount() {
		return toAccount;
	}
	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}
	
}
