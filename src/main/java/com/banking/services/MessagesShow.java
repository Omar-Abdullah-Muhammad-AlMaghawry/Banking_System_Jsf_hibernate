package com.banking.services;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesShow {
	 public static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
	        FacesContext.getCurrentInstance().
	                addMessage(null, new FacesMessage(severity, summary, detail));
	    }

	    public static  void showInfo(String headMessage, String detailMessage) {
	        addMessage(FacesMessage.SEVERITY_INFO, headMessage, detailMessage);
	    }

	    public static void showWarn(String headMessage, String detailMessage) {
	        addMessage(FacesMessage.SEVERITY_WARN, headMessage, detailMessage);
	    }
	    public static void showError(String headMessage, String detailMessage) {
	        addMessage(FacesMessage.SEVERITY_ERROR, headMessage, detailMessage);
	    }
	    public static void showLocaleErrorMessage(String titleKey,String contentKey) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Locale locale = facesContext.getViewRoot().getLocale();
			ResourceBundle msgsBundle = ResourceBundle.getBundle("com.messages.messages", locale);

			String approveTransactionT = msgsBundle.getString(titleKey);
			String approveTransactionD = msgsBundle.getString(contentKey);

			MessagesShow.showError(approveTransactionT, approveTransactionD);
	    }
}
