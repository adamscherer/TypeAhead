package com.typeahead.entity;

import cleo.search.SimpleElement;

public class City extends SimpleElement {

	private static final long serialVersionUID = 1L;

	private String city;
	private String state;
	private int pop;

	private double[] loc;

	public City() {
		super(0);
	}

	public City(int id) {
		super(id);
	}

	public String getName() {
		return city + ", " + state;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPop() {
		return pop;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public double[] getLoc() {
		return loc;
	}

	public void setLoc(double[] loc) {
		this.loc = loc;
	}

}
