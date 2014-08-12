package com.divine.directory4u;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * <p>This class sets up the database with categories and subcategories.
 * Allows for addition and deletion.</p>
 * 
 * <p>This program is part of ENTERPRISE PROJECT - ASSIGNMENT ELEMENT 1</p>
 * 
 * <p>Ryan Williamson m2150195@tees.ac.uk 11-Aug-2014</p>
 */
public class DatabaseHandler extends SQLiteOpenHelper {
	
	/*
	 * String definitions for the database name and tables.
	 */
	private final static String DATABASE_NAME = "cats.db";
	private final static String TABLE_NAME_CAT ="main_cat";
	private final static String TABLE_NAME_SUBCAT = "sub_cat";
	
	/*
	 * String definitions for column names
	 */
	private final static String CAT_ID = "_id";
	private final static String CAT_NAME = "category";
	private final static String CAT_ICON = "icon";
	
	private final static String SUBCAT_ID = "_id";
	private final static String SUBCAT_NAME = "sub_category";
	private final static String SUBCAT_ICON = "icon";
	private final static String REF_ID = "ref_id";
	
	public DatabaseHandler(Context context){
		super(context, DATABASE_NAME, null, 1);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CAT_TABLE = "CREATE TABLE "
				+ TABLE_NAME_CAT
				+ "("
				+ CAT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ CAT_NAME 
				+ " TEXT, "
				+ CAT_ICON + " TEXT" + ")";
		
		String CREATE_SUBCAT_TABLE = "CREATE TABLE "
				+ TABLE_NAME_SUBCAT
				+ "("
				+ SUBCAT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ REF_ID 
				+ " INTEGER, "
				+ SUBCAT_NAME
				+ " TEXT, "
				+ SUBCAT_ICON 
				+ " TEXT, "
				+ "FOREIGN KEY (" + REF_ID + ") REFERENCES " + TABLE_NAME_CAT + "(" + CAT_ID + "))";
		/*
		 * Populate tables with initial data
		 */
		db.execSQL(CREATE_CAT_TABLE);
		db.execSQL(CREATE_SUBCAT_TABLE);
		db.execSQL("INSERT INTO " + TABLE_NAME_CAT + " (" + CAT_NAME + ", " + CAT_ICON + ") VALUES ('Alcohol', 'R.drawable.ic_launcher')");
		db.execSQL("INSERT INTO " + TABLE_NAME_CAT + " (" + CAT_NAME + ", " + CAT_ICON + ") VALUES ('Accommodation', 'R.drawable.ic_launcher')");
		db.execSQL("INSERT INTO " + TABLE_NAME_CAT + " (" + CAT_NAME + ", " + CAT_ICON + ") VALUES ('Automobile', 'R.drawable.ic_launcher')");
		db.execSQL("INSERT INTO " + TABLE_NAME_SUBCAT + " (" + REF_ID + ", " + SUBCAT_NAME + ", " + SUBCAT_ICON + ") VALUES ('1', 'Pub', 'R.drawable.ic_launcher')");
		db.execSQL("INSERT INTO " + TABLE_NAME_SUBCAT + " (" + REF_ID + ", " + SUBCAT_NAME + ", " + SUBCAT_ICON + ") VALUES ('1', 'Restaurant', 'R.drawable.ic_launcher')");
		db.execSQL("INSERT INTO " + TABLE_NAME_SUBCAT + " (" + REF_ID + ", " + SUBCAT_NAME + ", " + SUBCAT_ICON + ") VALUES ('1', 'Bar', 'R.drawable.ic_launcher')");
		db.execSQL("INSERT INTO " + TABLE_NAME_SUBCAT + " (" + REF_ID + ", " + SUBCAT_NAME + ", " + SUBCAT_ICON + ") VALUES ('2', 'Hotel', 'R.drawable.ic_launcher')");
		db.execSQL("INSERT INTO " + TABLE_NAME_SUBCAT + " (" + REF_ID + ", " + SUBCAT_NAME + ", " + SUBCAT_ICON + ") VALUES ('2', 'Lodges', 'R.drawable.ic_launcher')");
		db.execSQL("INSERT INTO " + TABLE_NAME_SUBCAT + " (" + REF_ID + ", " + SUBCAT_NAME + ", " + SUBCAT_ICON + ") VALUES ('2', 'Campsites', 'R.drawable.ic_launcher')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CAT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUBCAT);
		onCreate(db);
	}
	
	/**
	 * From the id gets the category's name.
	 * @param id id of which to check.
	 * @return category's name.
	 */
	public String getCatName(int id){
		// 1. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        //SELECT CAT_NAME FROM TABLE_NAME_CAT WHERE CAT_ID = ?
        String selectQuery = "SELECT " + CAT_NAME +" FROM " + TABLE_NAME_CAT + " WHERE " + CAT_ID + " =?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) });
        if(cursor !=null)
        	cursor.moveToFirst();
        return cursor.getString(0);
	}
	
	public List<Category> getCatData(){
		List<Category> list = new ArrayList<Category>();
		String selectQuery = "SELECT * FROM " + TABLE_NAME_CAT;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do {
				Category category = new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
				list.add(category);
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	
	public List<SubCategory> getSubCatData(String statement){
		List<SubCategory> sublist = new ArrayList<SubCategory>();
		String selectQuery = statement;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()){
			do {
				SubCategory subcategory = new SubCategory(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3));
				sublist.add(subcategory);
			} while (cursor.moveToNext());
		}
		return sublist;
	}
	
	/**
	 * From the id gets the subcategory's name.
	 * @param id id of which to check.
	 * @return subcategory's name.
	 */
	public String getSubCatName(int id){
		// 1. get reference to writable DB
        SQLiteDatabase db = this.getReadableDatabase();
        //SELECT SUBCAT_NAME FROM TABLE_NAME_CAT WHERE CAT_ID = ?
        String selectQuery = "SELECT " + SUBCAT_NAME +" FROM " + TABLE_NAME_SUBCAT + " WHERE " + SUBCAT_ID + " =?";
        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) });
        if(cursor !=null)
        	cursor.moveToFirst();
        return cursor.getString(0);
	}
	
	/**
	 * Adds a category to database with a name and icon.
	 * @param category object which to add to database.
	 */
	public void addCategory(Category category){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CAT_NAME, category.getCatName());
		values.put(CAT_ICON, category.getCatIcon());
		db.insert(TABLE_NAME_CAT, null, values);
		db.close();
	}
	
	/**
	 * Adds a subcategory to database with a name and icon linked too a category.
	 * @param subcategory object which to add to database.
	 */
	public void addSubCategory(SubCategory subcategory){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(REF_ID, subcategory.getParent());
		values.put(SUBCAT_NAME, subcategory.getCatName());
		values.put(SUBCAT_ICON, subcategory.getCatIcon());
		db.insert(TABLE_NAME_SUBCAT, null, values);
		db.close();
	}
	
	public void removeAll(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CAT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUBCAT);
		onCreate(db);
	}
	
	public Cursor getCatCursor(){
		String selectQuery = "SELECT rowid _id, * FROM " + TABLE_NAME_CAT;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		return cursor;
	}
	
	public Cursor getSubCatCursor(int idforeign){
		String selectQuery = "SELECT * FROM " + TABLE_NAME_SUBCAT + " WHERE " + REF_ID + " = " + idforeign;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		return cursor;
	}
	
	/**
	 * Deletes a category from the database and all its children.
	 * @param cat which category object to remove
	 */
	public void deleteCat(Category cat) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete(TABLE_NAME_CAT, //table name
                CAT_ID+" = ?",  // selections
                new String[] { String.valueOf(cat.getID()) }); //selections args
        db.delete(TABLE_NAME_SUBCAT, //table name
                SUBCAT_ID+" = ?",  // selections
                new String[] { String.valueOf(cat.getID()) }); //selections args
        // 3. close
        db.close(); 
    }
	
	/**
	 * Deletes a subcategory from the database.
	 * @param subCat which subcategory object to remove.
	 */
	public void deleteSubCat(SubCategory subCat) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete(TABLE_NAME_SUBCAT, //table name
                SUBCAT_ID+" = ?",  // selections
                new String[] { String.valueOf(subCat.getID()) }); //selections args
        // 3. close
        db.close(); 
    }
	
	public static String getCatName(){
		return CAT_NAME;
	}
	
	public static String getCatIcon(){
		return CAT_ICON;
	}
	
	public static String getSubCatName(){
		return SUBCAT_NAME;
	}
	
	public static String getSubCatIcon(){
		return SUBCAT_ICON;
	}
	
	
}
