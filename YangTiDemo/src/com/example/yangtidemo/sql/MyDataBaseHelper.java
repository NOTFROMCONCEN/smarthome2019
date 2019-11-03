package com.example.yangtidemo.sql;

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
		db.execSQL("create table user (_id integer primary key autoincrement,username text,passward text)");
		db.execSQL("create table user_login_number (_id integer primary key autoincrement,login_number text,login_time text)");
		db.execSQL(
				"insert into user_login_number (login_number,login_time)values(?,?)",
				new String[] { "0", "0" });
		db.execSQL("create table log(_id integer primary key autoincrement,number textr,shebei text,state text,time text)");
		db.execSQL("create table op_save (_id integer primary key autoincrement,shebei text,dongzuo text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
