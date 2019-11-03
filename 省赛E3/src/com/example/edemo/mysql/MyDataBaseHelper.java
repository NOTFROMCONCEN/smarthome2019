package com.example.edemo.mysql;

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
		db.execSQL("create table user (_id integer primary key autoincrement,username text,passward text,twopass text)");
		db.execSQL("insert into user (username,passward,twopass)values(?,?,?)",
				new String[] { "bizideal", "123456", "456852" });
		db.execSQL("create table link_mode (_id integer primary key autoincrement,"
				+ "link_name text,"
				+ "link_chuanganqi text,"
				+ "link_big_small text,"
				+ "link_number text,"
				+ "link_shebei text)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
