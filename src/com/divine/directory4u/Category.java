package com.divine.directory4u;

/**
 * <p>This class holds all the data on a category, from name and icon to id.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class Category {
	
	/**
	 * An integer for identifying individual categories.
	 */
	private int ID;
	/**
	 * The category's name.
	 */
	private String category_name;
	/**
	 * The category's icon (image).
	 */
	private String category_icon;
	
	/** 
	 * Empty category for instantiating the class
	 */
	public Category(){
	}
	
	/**
	 * Instantiates a version the class with the passed id, name and icon
	 * 
	 * @param ID unique identifier for this instance
	 * @param cat_name a name for the category
	 * @param cat_icon an icon relating to this category
	 */
	public Category(int ID, String cat_name, String cat_icon){
		//sets the category's name to the passed in value
		this.category_name = cat_name;
		//sets the category's icon
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
