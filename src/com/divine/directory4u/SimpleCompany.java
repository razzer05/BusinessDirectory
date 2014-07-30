package com.divine.directory4u;

import org.json.JSONException;
import org.json.JSONObject;

public class SimpleCompany {
	public static final String company_id = "company_id";
	public static final String company_name = "company_name";
	public static final String company_address_1 = "address_1";
	public static final String postcode = "postcode";
	
	String id;
	String name;
	String address_1;
	String postc;

	public SimpleCompany(JSONObject obj) {
		try {
			id = obj.getString(company_id);
			name = obj.getString(company_name);
			address_1 = obj.getString(company_address_1);
			postc = obj.getString(postcode);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return "SimpleCompany [id=" + id + ", name=" + name + ", address_1="
				+ address_1 + ", postc=" + postc + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress_1() {
		return address_1;
	}

	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}

	public String getPostc() {
		return postc;
	}

	public void setPostc(String postc) {
		this.postc = postc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
