package com.typeahead.entity;

import java.util.List;

import com.annconia.api.entity.AbstractEntity;

public class Account extends AbstractEntity {

	private String apiKey;
	private String name;
	private String[] users;
	private int totalCredits;
	private int usedCredits;
	private List<PurchaseLog> purchases;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String[] getUsers() {
		return users;
	}

	public void setUsers(String[] users) {
		this.users = users;
	}

	public int getTotalCredits() {
		return totalCredits;
	}

	public void setTotalCredits(int totalCredits) {
		this.totalCredits = totalCredits;
	}

	public int getUsedCredits() {
		return usedCredits;
	}

	public void setUsedCredits(int usedCredits) {
		this.usedCredits = usedCredits;
	}

	public List<PurchaseLog> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<PurchaseLog> purchases) {
		this.purchases = purchases;
	}

}