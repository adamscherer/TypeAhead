package com.typeahead.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import cleo.search.SimpleElement;

public class City extends SimpleElement {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private String id;
	private String city;
	private String state;
	private int zip;
	private int pop;

	private double[] loc;

	public City() {
		super(0);
	}

	public City(int id) {
		super(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

}
