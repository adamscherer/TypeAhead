package com.typeahead.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import cleo.search.Element;

public class Hometown implements Element {

	@JsonIgnore
	private String id;
	private String city;
	private String state;
	private int zip;
	private int pop;

	private double[] loc;

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

	@Override
	public int compareTo(Element o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getElementId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getTerms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTimestamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setElementId(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScore(float f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTerms(String... as) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimestamp(long l) {
		// TODO Auto-generated method stub
		
	}

}
