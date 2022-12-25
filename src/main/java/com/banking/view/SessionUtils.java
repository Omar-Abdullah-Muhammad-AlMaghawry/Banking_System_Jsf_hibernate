package com.banking.view;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

	public static HttpSession getSession() {

		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

	}

	public static HttpServletRequest getRequest() {

		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

	}

	public static String getUserName() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if (session != null)
			return session.getAttribute("userName").toString();
		else
			return null;
	}

	public static String getUserId() {
		HttpSession session = getSession();
		
		if (session != null)
			return session.getAttribute("userId").toString();
		else
			return null;
	}

}
