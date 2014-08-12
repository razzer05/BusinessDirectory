package com.divine.directory4u;

/**
 * <p>This class sets and gets all the relevant data for the subcategory's</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class SubCategory {
	
	private int ID;
	private int belongs_to;
	private String category_name;
	private String category_icon;
	
	public SubCategory(){
		
	}
	
	public SubCategory(int ID, int parent, String cat_name, String cat_icon) {
		this.ID = ID;
		this.belongs_to = parent;
		this.category_name = cat_name;
		this.category_icon = cat_icon;
	}
	
	/* Getters and Setters*/
	
	public String getCatName() {
		return this.category_name;
	}
	
	public String getCatIcon() {
		return this.category_icon;
	}
	
	public int getParent() {
		return this.belongs_to;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setCatName(String catname) {
		this.category_name = catname;
	}
	
	public void setCatIcon(String caticon) {
		this.category_icon = caticon;
	}
	
	public void setParent(int parentid) {
		this.belongs_to = parentid;
	}
	
	public void setID(int id) {
		this.ID = id;
	}

}
