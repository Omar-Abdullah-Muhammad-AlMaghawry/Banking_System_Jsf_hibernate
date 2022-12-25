package com.banking.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.banking.enums.UserTypeEnum;
import com.banking.orm.Account;
import com.banking.orm.Bank;
import com.banking.orm.BankEmployee;
import com.banking.orm.Client;
import com.banking.orm.UserDetail;
import com.banking.services.HashPasswordBycrpt;
import com.banking.services.MessagesShow;
import com.banking.services.Services;

@ManagedBean
@SessionScoped
public class UserRegisterEdit implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userName = "";
	private String password;
	private String newPassword;
	private String mobile;
	private String mail;
	private double salary;
	private Date birthDate;
	private String address;
	private String name;
	private Date maxDate = new Date();
	private UserTypeEnum type = UserTypeEnum.CLIENT;
	private String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^+=]).{5,}$";
	private String encryptedPassword;
	private String encryptedNewPassword;
	private Services services = new Services();
	private FacesContext facesContext = FacesContext.getCurrentInstance();
	private Locale locale = facesContext.getViewRoot().getLocale();
	private ResourceBundle msgsBundle = ResourceBundle.getBundle("com.messages.messages", locale);
	private SelectItem[] typeItems = {
			new SelectItem(UserTypeEnum.CLIENT, msgsBundle.getString("client")),
			new SelectItem(UserTypeEnum.EMPLOYEE, msgsBundle.getString("employee")),
			};
	public static boolean isValidPassword(String password, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	
	///////////////////////////// CRUD Operations/////////////////////////////////
	public String create() {
		try {
		List<String> names = (List) services.readNamedQueryList("getAllUserNames", userName, false);
		List<String> mails = (List) services.readNamedQueryList("getAllUserMails", userName, false);

		encryptedPassword = HashPasswordBycrpt.hashPassword(password);
		if ((names == null) || (!names.contains(userName))) {
			if (!mails.contains(mail)) {
				if (type.equals(UserTypeEnum.CLIENT)) {
					Client client = new Client(userName, encryptedPassword, birthDate, salary, address, mobile, mail,
							name);
					Bank bank = new Bank();
					Account account = new Account();

					bank.setBranchAddress("bank address");

					bank.getAccountList().add(account);
					account.setBank(bank);

					client.setBank(bank);
					bank.getClientList().add(client);

					account.setClient(client);
					client.getAccountList().add(account);

					account.setAmount(salary);

					services.persist(client);

				} else {

					BankEmployee employee = new BankEmployee(userName, encryptedPassword, "manager", birthDate, salary,
							address, mobile, mail, name);
					services.persist(employee);
				}

				return "login?faces-redirect=true";
			} else {

				MessagesShow.showLocaleErrorMessage("alreadyExistMail", "correctAlreadyExistMail");

				return null;

			}

		} else {

			MessagesShow.showLocaleErrorMessage("alreadyExistUserName", "correctAlreadyExistUserName");

			return null;

		}
		}catch (Exception e) {
			MessagesShow.showLocaleErrorMessage(e.getMessage(), "error");
			return null;		}
	}

	public String update() {
		try {
		UserDetail user = (UserDetail) services.readNamedQueryObject("getUserDetailByUserName", userName, true);
		String type = (String) services.readNamedQueryObject("getTypeByUserName", userName, true);
		List<String> mails = (List) services.readNamedQueryList("getAllUserMails", userName, false);

		String currentUserName = SessionUtils.getUserName();
		if (user != null) {

			Boolean status = HashPasswordBycrpt.checkPass(password, user.getPassword());
			if (user.getUserName().equals(currentUserName) && user.getUserName().equals(userName) && status) {
				if (!mails.contains(mail)) {

					if (type.equals("client")) {
						Client client = (Client) user;
						client.setPassword(encryptedNewPassword);
						client.setBirthDate(birthDate);
						client.setNetSalary(salary);
						client.setAddress(address);
						client.setMobile(mobile);
						client.setMail(mail);
						client.setName(name);

						services.update(client);
					} else {

						BankEmployee employee = (BankEmployee) user;
						employee.setPassword(encryptedNewPassword);
						employee.setBirthDate(birthDate);
						employee.setNetSalary(salary);
						employee.setAddress(address);
						employee.setMobile(mobile);
						employee.setMail(mail);
						employee.setName(name);

						services.update(employee);
					}
					if (type.equals("client")) {
						return "account?faces-redirect=true";
					} else {
						return "transaction?faces-redirect=true";

					}
				} else {

					MessagesShow.showLocaleErrorMessage("alreadyExistMail", "correctAlreadyExistMail");

					return null;

				}

			} else {
				MessagesShow.showLocaleErrorMessage("validUserNamePasswordLogin", "corrrectUserNameAndPassword");
				return null;
			}
		} else {
			MessagesShow.showLocaleErrorMessage("validUserNamePasswordLogin", "corrrectUserNameAndPassword");
			return null;
		}
		}catch (Exception e) {
			MessagesShow.showLocaleErrorMessage("validUserNamePasswordLogin", "corrrectUserNameAndPassword");
			return null;	
		}
	}

	//////////////////// Setters and Getters ////////////////////////////
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public SelectItem[] getTypeItems() {
		return typeItems;
	}

	public void setTypeItems(SelectItem[] typeItems) {
		this.typeItems = typeItems;
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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserTypeEnum getType() {
		return type;
	}

	public void setType(UserTypeEnum type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (isValidPassword(password, regexPassword))
			this.password = password;
		else
			this.password = "admin";
	}
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		if (isValidPassword(password, regexPassword))
			this.newPassword = newPassword;
		else
			this.newPassword = "admin";
	}

}
