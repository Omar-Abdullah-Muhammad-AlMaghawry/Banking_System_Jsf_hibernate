package com.banking.enums;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public enum UserTypeEnum {
	CLIENT("Client","عميل",1),
	EMPLOYEE("Employee","موظف",2);
	
	private static final Map<String, UserTypeEnum> BY_ENGLISH = new HashMap<>();
	private static final Map<String, UserTypeEnum> BY_ARABIC = new HashMap<>();
	
	static {
		for (UserTypeEnum e :values()) {
			BY_ENGLISH.put(e.english, e);
			BY_ARABIC.put(e.arabic, e);
		}
		
	}
	
	private String english;
	private String arabic;
	private long value;
	UserTypeEnum(String english, String arabic,long value) {
		this.english = english;
		this.arabic = arabic;
		this.value = value;
	}
	public String getEnglish() {
		return english;
	}

	public String getArabic() {
		return arabic;
	}

	public static UserTypeEnum valueOfEnglish(String englishVal) {
		return BY_ENGLISH.get(englishVal);
	}
	public static UserTypeEnum valueOfArabic(String arabicVal) {
		return BY_ARABIC.get(arabicVal);
	}
	public long getValue() {
		return value;
	}
	
	
}
