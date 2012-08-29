package com.typeahead.entity;

import cleo.search.SimpleElement;

public class County extends SimpleElement {

	private static final long serialVersionUID = 1L;

	private String county;
	private String state;
	private int pop;

	private double[] loc;

	public County() {
		super(0);
	}

	public County(int id) {
		super(id);
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
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
