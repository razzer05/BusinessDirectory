package com.divine.jsonData;

import org.json.JSONObject;

/**
 * <p>This class sets and gets all the relevant data from the JSON data for a simple company object.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class SimpleCompany {
	
	/* Holder variables */
   	private String name;
   	private String place_id;
   	private String formattedAddress;
   	
   	/**
   	 * Sets up the simpleCompany object from the JSONObject.
   	 * @param obj contains one result from the download data.
   	 */
   	public SimpleCompany(JSONObject obj) {
   		// Gets the name if it exits otherwise null
		name = obj.optString("name");
		// Gets the unique place id for later use with its details if selected.
		place_id = obj.optString("place_id");
		// Gets the formatted address from the object otherwise null.
		formattedAddress = obj.optString("formatted_address");
   	}
   	
   	/* Setters and Getters */
   	
   	public void setFormattedAddress(String formattedAddress) {
   		this.formattedAddress = formattedAddress;
   	}
   	
   	public String getFormattedAddress() {
   		return this.formattedAddress;
   	}
	
 	public String getName() {
		return this.name;
	}
 	
	public void setName(String name) {
		this.name = name;
	}
	
 	public String getPlace_id() {
		return this.place_id;
	}
 	
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
}
