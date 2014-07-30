package com.divine.directory4u;

public class Category {
	
	private int ID;
	private String category_name;
	private String category_icon;
	
	public Category(int ID, String cat_name, String cat_icon){
		this.category_name = cat_name;
		this.category_icon = cat_icon;
	}
	
	/* Getters and Setters */
	

	public String getCatName(){
		return this.category_name;
	}
	
	public String getCatIcon(){
		return this.category_icon;
	}
	
	public int getID(){
		return this.ID;
	}
	
	public void setCatName(String catname){
		this.category_name = catname;
	}
	
	public void setCatIcon(String caticon){
		this.category_icon = caticon;
	}
	
	public void setID(int catid){
		this.ID = catid;
	}

}
