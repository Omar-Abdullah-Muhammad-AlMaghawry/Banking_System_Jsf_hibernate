package com.banking.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import com.banking.orm.UserDetail;
import com.banking.services.HashPasswordBycrpt;
import com.banking.services.MessagesShow;
import com.banking.services.Services;

@ManagedBean
@SessionScoped
public class UserLogin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName = "";
	private String password;
	private Boolean isLogged = false;
	private Services services = new Services();

////////////////////LOGIN LOGOUT//////////////////////////////
	public String isLoggedRedirectHome() {

		return "login?faces-redirect=true\"";
	}

	public String loginRedirect() {
		try {
			String type = (String) services.readNamedQueryObject("getTypeByUserName", userName, true);
			UserDetail user = (UserDetail) services.readNamedQueryObject("getUserDetailByUserName", userName, true);

			if (user != null) {
				Boolean status = HashPasswordBycrpt.checkPass(password, user.getPassword());
				if (user.getUserName().equals(userName) && status) {

					HttpSession session = SessionUtils.getSession();
					session.setAttribute("userName", user.getUserName());
					session.setAttribute("userId", user.getUserId());
					isLogged = true;
					if (type.equals("client"))
						return "account?faces-redirect=true";
					else
						return "transaction?faces-redirect=true";
				} else {
					MessagesShow.showLocaleErrorMessage("validUserNamePasswordLogin", "corrrectUserNameAndPassword");
					return null;
				}
			} else {
				MessagesShow.showLocaleErrorMessage("validUserNamePasswordLogin", "corrrectUserNameAndPassword");
				return null;
			}
		} catch (Exception e) {
			MessagesShow.showLocaleErrorMessage(e.getMessage(), "corrrectUserNameAndPassword");
			return null;
		}
	}

	// logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login?faces-redirect=true";
	}

	///////////////////////// SETTER AND GETTER///////////////////////

	public Boolean getIsLogged() {
		return isLogged;
	}

	public void setIsLogged(Boolean isLogged) {
		this.isLogged = isLogged;
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
		this.password = password;
	}
}
