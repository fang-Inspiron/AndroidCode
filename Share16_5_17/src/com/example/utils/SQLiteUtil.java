package com.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.bean.Essay;

public class SQLiteUtil extends SQLiteOpenHelper {

	public SQLiteDatabase db;
	//数据库版本  
    private static final int VERSION = 1; 
	// 新建一个表
    String s = "drop table essayData";
	String sql = "create table if not exists essayData"
			+ "(essayid integer, title varchar, author varchar, time varchar, zanNum varchar," +
			" careNum varchar, shareNum varchar, content varchar, classify varchar)";

	public SQLiteUtil(Context context, String name, CursorFactory factory,	int version) {
		super(context, name, factory, VERSION);
	}
	
	public SQLiteUtil(Context context, String name,	int version) {
		this(context, name, null, VERSION);
	}

	public SQLiteUtil(Context context, String name) {
		
		this(context, name, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	public boolean update(List<Essay> list) {

		 ContentValues values = new ContentValues(); 
		 db = this.getWritableDatabase();	
		 db.execSQL(s);
		 db.execSQL(sql);
		 for (int i=0; i<list.size(); i++) {
			 values.put("essayid", list.get(i).getEssayId());
			 values.put("title", list.get(i).getTitle());
			 values.put("author", list.get(i).getAuthor());
			 values.put("time", list.get(i).getTime());
			 values.put("zanNum", list.get(i).getZanNum().toString());
			 values.put("careNum", list.get(i).getCareNum().toString());
			 values.put("shareNum", list.get(i).getShareNum().toString());
			 values.put("content", list.get(i).getContent());
			 values.put("classify", list.get(i).getClassify());
			 db.insert("essayData", null, values);	 
		 }
		return true;
	}
	
	public List<Essay> findItem() {
		List<Essay> list = new ArrayList<Essay>();
		db = this.getReadableDatabase();
		db.execSQL(sql);
		Cursor cursor = db.query("essayData", new String[] {"essayid", "title",  "author", "time", "zanNum", "careNum", "shareNum", "content", "classify"},
				null, null, null, null, null);
		cursor.moveToFirst();
		do {
			Essay essay = new Essay();
			essay.setEssayId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("essayid"))));
			essay.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			essay.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
			essay.setTime(cursor.getString(cursor.getColumnIndex("time")));
			essay.setZanNum(Integer.valueOf(cursor.getString(cursor.getColumnIndex("zanNum"))));
			essay.setCareNum(Integer.valueOf(cursor.getString(cursor.getColumnIndex("careNum"))));
			essay.setShareNum(Integer.valueOf(cursor.getString(cursor.getColumnIndex("shareNum"))));
			essay.setContent(cursor.getString(cursor.getColumnIndex("content")));
			essay.setClassify(cursor.getString(cursor.getColumnIndex("classify")));
			
			list.add(essay);
		} while (cursor.moveToNext());
		
		return list;
	}
	
	public Map<String, Object> find(int essayid) {
		Map<String, Object> map = new HashMap<String, Object>();
		db = this.getReadableDatabase();
		db.execSQL(sql);
		Cursor cursor = db.query("essayData", new String[] { "title",  "author", "time", "zanNum", "careNum", "shareNum", "content", "classify"},
				"essayid=?", new String[]{String.valueOf(essayid)}, null, null, null);
		cursor.moveToFirst();
		do {
			map.put("title", cursor.getString(cursor.getColumnIndex("title")));
			map.put("author", cursor.getString(cursor.getColumnIndex("author")));
			map.put("time", cursor.getString(cursor.getColumnIndex("time")));
			map.put("zanNum", cursor.getString(cursor.getColumnIndex("zanNum")));
			map.put("careNum", cursor.getString(cursor.getColumnIndex("careNum")));
			map.put("shareNum", cursor.getString(cursor.getColumnIndex("shareNum")));
			map.put("content", cursor.getString(cursor.getColumnIndex("content")));
			map.put("classify", cursor.getString(cursor.getColumnIndex("classify")));
		} while(cursor.moveToNext());
		return map;
	}
}
