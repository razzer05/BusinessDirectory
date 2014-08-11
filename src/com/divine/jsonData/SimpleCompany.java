package com.divine.jsonData;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SimpleCompany{
   	private Geometry geometry;
   	private String icon;
   	private String id;
   	private String name;
   	private Opening_hours opening_hours;
   	private List photos;
   	private String place_id;
   	private Number rating;
   	private String reference;
   	private String scope;
   	private List types;
   	private String vicinity;
   	private String formattedAddress;
   	
   	/* This class needs tiding up
   	 * remove unused and format the rest to the other classes.. */
   	public SimpleCompany(JSONObject obj) {
   		JSONObject geo = obj.optJSONObject("geometry");
		JSONObject location = geo.optJSONObject("location");
		String lat = location.optString("lat");
		String lng = location.optString("lng");
		icon = obj.optString("icon");
		id = obj.optString("id");
		name = obj.optString("name");
		JSONObject opening_hours = obj.optJSONObject("opening_hours");
		if(opening_hours != null){
			boolean open_now = opening_hours.optBoolean("open_now");
		}
		JSONArray photos = obj.optJSONArray("photos");
		if(photos != null){
		String height = photos.optJSONObject(0).optString("height");
		//JSONArray html_attributions = photos.optJSONArray("html_attributions");
		String photo_reference = photos.optJSONObject(0).optString("photo_reference");
		String width = photos.optJSONObject(0).optString("width");
		}
		place_id = obj.optString("place_id");
		String rating = obj.optString("rating");
		String reference = obj.optString("reference");
		String scope = obj.optString("scope");
		//types is an array
		//String types = obj.optString("types");
		vicinity = obj.optString("vicinity");
		formattedAddress = obj.optString("formatted_address");
   	}
   	
   	public void setFormattedAddress(String formattedAddress){
   		this.formattedAddress = formattedAddress;
   	}
   	
   	public String getFormattedAddress(){
   		return this.formattedAddress;
   	}
   	
 	public Geometry getGeometry(){
		return this.geometry;
	}
	public void setGeometry(Geometry geometry){
		this.geometry = geometry;
	}
 	public String getIcon(){
		return this.icon;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public Opening_hours getOpening_hours(){
		return this.opening_hours;
	}
	public void setOpening_hours(Opening_hours opening_hours){
		this.opening_hours = opening_hours;
	}
 	public List getPhotos(){
		return this.photos;
	}
	public void setPhotos(List photos){
		this.photos = photos;
	}
 	public String getPlace_id(){
		return this.place_id;
	}
	public void setPlace_id(String place_id){
		this.place_id = place_id;
	}
 	public Number getRating(){
		return this.rating;
	}
	public void setRating(Number rating){
		this.rating = rating;
	}
 	public String getReference(){
		return this.reference;
	}
	public void setReference(String reference){
		this.reference = reference;
	}
 	public String getScope(){
		return this.scope;
	}
	public void setScope(String scope){
		this.scope = scope;
	}
 	public List getTypes(){
		return this.types;
	}
	public void setTypes(List types){
		this.types = types;
	}
 	public String getVicinity(){
		return this.vicinity;
	}
	public void setVicinity(String vicinity){
		this.vicinity = vicinity;
	}
}
