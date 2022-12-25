package com.banking.view;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;

@SuppressWarnings("serial")
@ManagedBean(name = "language")
@SessionScoped
public class LocaleLanguage implements Serializable {

	private String localeCode;
	static FacesContext facesContext = FacesContext.getCurrentInstance();

	static Locale locale = facesContext.getViewRoot().getLocale();
	static ResourceBundle msgsBundle = ResourceBundle.getBundle("com.messages.messages", locale);

	static String english = msgsBundle.getString("english");
	static String arabic = msgsBundle.getString("arabic");

	
	private static SelectItem[] countriesAll = { new SelectItem(Locale.ENGLISH, english),
			new SelectItem(new Locale("ar"), arabic),

	};
	
	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public SelectItem[] getCountriesAll() {
		return countriesAll;
	}


	public void countryLocaleCodeEnglish(ActionEvent e) {
		this.localeCode = "en";

		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(localeCode));
		FacesContext.getCurrentInstance().renderResponse();

	}

	public void countryLocaleCodeArabic(ActionEvent e) {
		this.localeCode = "ar";
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(localeCode));
		FacesContext.getCurrentInstance().renderResponse();

	}

	public void countryLocaleCode(ComponentSystemEvent e) {
		if (localeCode != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
			FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(localeCode));
			FacesContext.getCurrentInstance().renderResponse();
		}
	}
}
