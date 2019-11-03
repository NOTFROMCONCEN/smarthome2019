package com.example.ademo.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {

	public MyDataBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		for (int i = 1; i < 5; i++) {
			db.execSQL("create table room810" + String.valueOf(i)
					+ "(_id integer primary key autoincrement,roomstate text)");
		}
		for (int i = 1; i < 5; i++) {
			db.execSQL("create table room820" + String.valueOf(i)
					+ "(_id integer primary key autoincrement,roomstate text)");
		}
		for (int i = 1; i < 5; i++) {
			db.execSQL("create table room830" + String.valueOf(i)
					+ "(_id integer primary key autoincrement,roomstate text)");
		}
		for (int i = 1; i < 5; i++) {
			db.execSQL("create table room840" + String.valueOf(i)
					+ "(_id integer primary key autoincrement,roomstate text)");
		}
		for (int i = 1; i < 5; i++) {
			db.execSQL("insert into room810" + String.valueOf(i)
					+ "(roomstate)values(?)", new String[] { "1" });
		}
		for (int i = 1; i < 5; i++) {
			db.execSQL("insert into room820" + String.valueOf(i)
					+ "(roomstate)values(?)", new String[] { "1" });
		}
		for (int i = 1; i < 5; i++) {
			db.execSQL("insert into room830" + String.valueOf(i)
					+ "(roomstate)values(?)", new String[] { "1" });
		}
		for (int i = 1; i < 5; i++) {
			db.execSQL("insert into room840" + String.valueOf(i)
					+ "(roomstate)values(?)", new String[] { "1" });
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
