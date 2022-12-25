package com.banking.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;

import com.banking.enums.TransactionStatusEnum;
import com.banking.orm.Account;
import com.banking.orm.Transaction;
import com.banking.services.JasperService;
import com.banking.services.MessagesShow;
import com.banking.services.Services;

@ManagedBean
@SessionScoped
public class AccountBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	enum Status {
		APPROVED, PENDING, REJECTED
	};

	private long fromAccountId;
	private String toAccount;
	private double amount;
	private double amountFromAccount;
	private String description;
	Collection<Transaction> transactions = new ArrayList<Transaction>();
	private Services services = new Services();
	private String userName = SessionUtils.getUserName();
	private String type;
	private Boolean isClient;
	private List<Transaction> filteredTransaction;
	private List<FilterMeta> filterBy;
	private Transaction selectedTransaction;
	private Comparator<Transaction> compareByTransactionDate = (Transaction o1, Transaction o2) -> o2
			.getTransactionDate().compareTo(o1.getTransactionDate());
	private String clientId = SessionUtils.getUserId();
	private FacesContext facesContext = FacesContext.getCurrentInstance();
	private Locale locale = facesContext.getViewRoot().getLocale();
	ResourceBundle msgsBundle = ResourceBundle.getBundle("com.messages.messages", locale);
	Collection<Account> fromAccountOL;

	@PostConstruct
	public void init() {
		filterBy = new ArrayList<>();
		filterBy.add(FilterMeta.builder().field("date")
				.filterValue(
						new ArrayList<>(Arrays.asList(LocalDate.now().minusDays(28), LocalDate.now().plusDays(28))))
				.matchMode(MatchMode.RANGE).build());
		try {
			fromAccountOL = (List) services.readNamedQueryList("getAccountByClientId", clientId, true);

		} catch (Exception e) {
			MessagesShow.showLocaleErrorMessage(e.getMessage(), "error");
		}
		try {
			type = (String) services.readNamedQueryObject("getTypeByUserName", userName, true);
			isClient = (type.equals("client"));
		} catch (Exception e) {
			MessagesShow.showLocaleErrorMessage(e.getMessage(), "error");
		}
		
	};

	public Collection<Transaction> getTransactionsAll() {

		try {
			if (isClient) {
				transactions.clear();
				amountFromAccount = 0;

				for (Account fromAccount : fromAccountOL) {
					Collection<Transaction> transactionsOneAccount = (List) services.readNamedQueryList(
							"getTransactionByFromAccountId", Long.toString(fromAccount.getAccountId()), true);
					if (transactionsOneAccount != null) {
						transactions.addAll((List) transactionsOneAccount);
					}
					amountFromAccount += fromAccount.getAmount();
				}
				if (!transactions.isEmpty())
					Collections.sort((List) transactions, compareByTransactionDate);
				return transactions;
			} else
				return null;
		} catch (Exception e) {
			MessagesShow.showLocaleErrorMessage(e.getMessage(), "error");
			return null;
		}
	}

	public String createTransaction() {
		try {
			Account fromAccountO = (Account) services.readObjectById(Account.class, Long.toString(fromAccountId));

			Account toAccountO = (Account) services.readObjectById(Account.class, toAccount);
			if (toAccountO != null) {
				if (fromAccountO.getAmount() > amount) {
					Transaction transaction = new Transaction(new Date(), description, amount,
							TransactionStatusEnum.PENDING, fromAccountO, toAccountO);
					services.save(transaction);
					return "account?faces-redirect=true";
				} else {
					MessagesShow.showLocaleErrorMessage("createTransactionT", "createTransactionD");
					return null;
				}

			} else {
				MessagesShow.showLocaleErrorMessage("toAccountO", "correctToAccountO");
				return null;

			}

		} catch (Exception e) {
			MessagesShow.showLocaleErrorMessage(e.getMessage(), "correctAccountO");
			return null;

		}

	}

	public String isClientRedirectHome() {
		if (isClient)
			return "account?faces-redirect=true";
		else
			return "transaction?faces-redirect=true";

	}

	public void onRowSelect(SelectEvent<Transaction> event) {
		FacesMessage msg = new FacesMessage("Transaction Selected",
				String.valueOf(event.getObject().getTransactionId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	// TODO print method
	public String printWithParamInput() {
		List<Transaction> transactionsList = (List) transactions;
		Map parm = new HashMap<>();
		long clientIdInt = Integer.parseInt(clientId);

		parm.put("pSelectTranscation", Long.toString(clientIdInt));

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();
		if (locale.equals(Locale.ENGLISH))
			parm.put("pLanguage", false);
		else
			parm.put("pLanguage", true);

		try {
			JasperService.runReport("transactionParam.jasper", parm, transactionsList);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	public void reset(ActionEvent event) {
		PrimeFaces.current().resetInputs("@form:panalForm");
	}

	////////////// SETTER AND GETTER//////////////////////

	public Collection<Account> getFromAccountOL() {
		return fromAccountOL;
	}

	public void setFromAccountOL(Collection<Account> fromAccountOL) {
		this.fromAccountOL = fromAccountOL;
	}

	public Boolean getIsClient() {
		return isClient;
	}

	public void setIsClient(Boolean isClient) {
		this.isClient = isClient;
	}

	public long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public double getAmountFromAccount() {
		return amountFromAccount;
	}

	public void setAmountFromAccount(double amountFromAccount) {
		this.amountFromAccount = amountFromAccount;
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

	public Transaction getSelectedTransaction() {
		return selectedTransaction;
	}

	public void setSelectedTransaction(Transaction selectedTransaction) {
		this.selectedTransaction = selectedTransaction;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

}
