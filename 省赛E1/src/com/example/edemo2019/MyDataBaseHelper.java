package com.example.edemo2019;

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
		db.execSQL("create table usedr (_id integer primary key autoincrrement,username text,passward text,twopasss text)");
		db.execSQL("insert into user (username,passward,twopass)values(?,?,?)",
				new String[] { "bizideal", "123456", "111" });
		db.execSQL("create table link_mode (_id integer primary key autoincrement,"
				+ "link_name text,"
				+ "link_mode text,"
				+ "link_shebei text,"
				+ "link_number text,"
				+ "link_big_small text,"
				+ "link_guanganqi text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
