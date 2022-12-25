package com.banking.enums;

import java.util.HashMap;
import java.util.Map;

public enum TransactionStatusEnum {
	APPROVED("Approved","مقبول"),
	PENDING("Pending","قيد الانتظار"),
	REJECTED("Rejected","مرفوض");
	
	private static final Map<String, TransactionStatusEnum> BY_ENGLISH = new HashMap<>();
	private static final Map<String, TransactionStatusEnum> BY_ARABIC = new HashMap<>();
	
	static {
		for (TransactionStatusEnum e :values()) {
			BY_ENGLISH.put(e.english, e);
			BY_ARABIC.put(e.arabic, e);
		}
		
	}
	
	private String english;
	private String arabic;
	TransactionStatusEnum(String english, String arabic) {
		// TODO Auto-generated constructor stub
		this.english = english;
		this.arabic = arabic;
	}
	public String getEnglish() {
		return english;
	}

	public String getArabic() {
		return arabic;
	}

	
	public static TransactionStatusEnum valueOfEnglish(String englishVal) {
		return BY_ENGLISH.get(englishVal);
	}
	public static TransactionStatusEnum valueOfArabic(String arabicVal) {
		return BY_ARABIC.get(arabicVal);
	}
	
	
}
