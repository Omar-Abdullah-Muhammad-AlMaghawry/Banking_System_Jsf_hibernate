package com.banking.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;

import com.banking.enums.TransactionStatusEnum;
import com.banking.orm.Account;
import com.banking.orm.Transaction;
import com.banking.services.MessagesShow;
import com.banking.services.Services;

@ManagedBean
@SessionScoped
public class TransactionBean implements Serializable {

	private String fromAccount;
	private String toAccount;
	private double amount;
	private TransactionStatusEnum status;
	Collection<Transaction> transactions = new ArrayList<Transaction>();
	private Services services = new Services();
	private String userName = SessionUtils.getUserName();
	private String type;
	private Boolean isEmployee;
	private List<Transaction> filteredTransaction;
	private List<FilterMeta> filterBy;
	private Transaction selectedTransaction;
	FacesContext facesContext = FacesContext.getCurrentInstance();
	Locale locale = facesContext.getViewRoot().getLocale();
	ResourceBundle msgsBundle = ResourceBundle.getBundle("com.messages.messages", locale);
	String alreadyExistUserName = msgsBundle.getString("alreadyExistUserName");
	private SelectItem[] statusItems = {
			new SelectItem(TransactionStatusEnum.APPROVED, msgsBundle.getString("approved")),
			new SelectItem(TransactionStatusEnum.PENDING, msgsBundle.getString("pending")),
			new SelectItem(TransactionStatusEnum.REJECTED, msgsBundle.getString("rejected")),

	};

	@PostConstruct
	public void init() {
		filterBy = new ArrayList<>();
		filterBy.add(FilterMeta.builder().field("date")
				.filterValue(
						new ArrayList<>(Arrays.asList(LocalDate.now().minusDays(28), LocalDate.now().plusDays(28))))
				.matchMode(MatchMode.RANGE).build());
		try {
			type = (String) services.readNamedQueryObject("getTypeByUserName", userName, true);
			isEmployee = (type.equals("employee"));
		} catch (Exception e) {
			MessagesShow.showLocaleErrorMessage(e.getMessage(), "error");

		}
	};

	public Collection<Transaction> getTransactionsAll() {
		try {
			if (isEmployee) {

				Comparator<Transaction> compareByTransactionDate = (Transaction o1, Transaction o2) -> o2
						.getTransactionDate().compareTo(o1.getTransactionDate());
				transactions = (List) services.readNamedQueryList("getAllTransactions", fromAccount, false);
				if (transactions != null)
					Collections.sort((List) transactions, compareByTransactionDate);
				return transactions;
			} else
				return null;
		} catch (Exception e) {
			MessagesShow.showLocaleErrorMessage(e.getMessage(), "error");
			return null;
		}
	}

	public void onRowSelect(SelectEvent<Transaction> event) {
		FacesMessage msg = new FacesMessage("Transaction Selected",
				String.valueOf(event.getObject().getTransactionId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void changeStatus(ActionEvent e) {
		try {
			Account fromAccountO = selectedTransaction.getFromAccount();
			selectedTransaction.setTransactionStatus(selectedTransaction.getTransactionStatus());

			if (selectedTransaction.getTransactionStatus().equals(TransactionStatusEnum.APPROVED)) {
				if (fromAccountO.getAmount() > selectedTransaction.getAmount()) {
					Account toAccountO = selectedTransaction.getToAccount();
					fromAccountO.setAmount(fromAccountO.getAmount() - selectedTransaction.getAmount());
					toAccountO.setAmount(toAccountO.getAmount() + selectedTransaction.getAmount());
					services.update(fromAccountO);
					services.update(toAccountO);
					services.update(selectedTransaction);
				} else {
					MessagesShow.showLocaleErrorMessage("approveTransactionT", "approveTransactionD");
				}
			} else if (selectedTransaction.getTransactionStatus().equals(TransactionStatusEnum.REJECTED)) {
				services.update(selectedTransaction);
			}
		} catch (Exception error) {
			MessagesShow.showLocaleErrorMessage(error.getMessage(), "error");
		}
	}

	public void filterTransaction(ActionEvent e) {
	}

	/////////// SETTER AND GETTER////////////
	public Boolean getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(Boolean isEmployee) {
		this.isEmployee = isEmployee;
	}

	public Transaction getSelectedTransaction() {
		return selectedTransaction;
	}

	public void setSelectedTransaction(Transaction selectedTransaction) {
		this.selectedTransaction = selectedTransaction;
	}

	public List<Transaction> getFilteredTransaction() {
		return filteredTransaction;
	}

	public void setFilteredTransaction(List<Transaction> filteredTransaction) {
		this.filteredTransaction = filteredTransaction;
	}

	public List<FilterMeta> getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(List<FilterMeta> filterBy) {
		this.filterBy = filterBy;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public TransactionStatusEnum getStatus() {
		return status;
	}

	public void setStatus(TransactionStatusEnum status) {
		this.status = status;
	}

	public Collection<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Collection<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public SelectItem[] getStatusItems() {
		return statusItems;
	}

	public void setStatusItems(SelectItem[] statusItems) {
		this.statusItems = statusItems;
	}
}
