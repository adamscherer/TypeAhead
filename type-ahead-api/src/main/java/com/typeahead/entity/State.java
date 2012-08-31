package com.typeahead.entity;

import cleo.search.SimpleElement;

public class State extends SimpleElement {

	private static final long serialVersionUID = 1L;

	private String state;
	private int pop;

	private double[] loc;

	public State() {
		super(0);
	}

	public State(int id) {
		super(id);
	}

	public String getName() {
		return state;
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
