package com.lilin.sqlite.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lilin.sqlite.R;
import com.lilin.sqlite.model.Config;

//表名：config
public class ConfigDBHelp {

	private static final String TABLENAME = "config";

	// 参数2 空列的默认值
	// 参数3 ContentValues类型的一个封装了列名称和列值的Map；
	public static void insert(Context con, String key, String value) {
		DBOpenHelp dbhelper = new DBOpenHelp(con, con
				.getString(R.string.db_name));
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		ContentValues cValue = new ContentValues();// 实例化常量值
		cValue.put("key", key);// 添加用户名
		cValue.put("value", value);
		db.insert(TABLENAME, null, cValue); // 调用insert()方法插入数据
		DBHelp.close(dbhelper, db, null);
	}

	// 通过SQL语句插入
	public static void insertSQL(Context con, String key, String value) {
		DBOpenHelp dbhelper = new DBOpenHelp(con, con
				.getString(R.string.db_name));
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		try {
			String delSQL = "delete from config where key = '" + key + "'";
			db.execSQL(delSQL);
		} catch (Exception e) {
			e.printStackTrace();
//			LogHelp.Log2SDErr(e, con.getString(R.string.logpath), con
//					.getString(R.string.app_name));
		}
		// 方法一：
		String str[] = new String[] { key, value };
		db.execSQL("insert into config values(?,?)", str);
		// 方法二：
		// String insertSQL = "insert into config(key,value) values('" + key
		// + "','" + value + "')";
		// db.execSQL(insertSQL);
		DBHelp.close(dbhelper, db, null);
	}

	// update(String table,ContentValues values,String whereClause, String[])
	// whereArgs)
	// 参数1 表名称
	// 参数2 跟行列ContentValues类型的键值对Key-Value
	// 参数3 更新条件（where字句）
	// 参数4 更新条件数组
	public static void update(Context con, String value, String key) {
		DBOpenHelp dbhelper = new DBOpenHelp(con, con
				.getString(R.string.db_name));
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("value", value);// 参数2 跟行列ContentValues类型的键值对Key-Value
		String whereClause = "key=?";
		String[] whereArgs = { key };
		db.update("usertable", values, whereClause, whereArgs);
		DBHelp.close(dbhelper, db, null);
	}

	public static void updateSQL(Context con, String value, String key) {
		DBOpenHelp dbhelper = new DBOpenHelp(con, con
				.getString(R.string.db_name));
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String updateSQL = "update config set value='" + value
				+ "' where key='" + key + "'";
		db.execSQL(updateSQL);
		DBHelp.close(dbhelper, db, null);
	}

	// delete(String table,String whereClause,String[] whereArgs)
	// 参数1 表名称
	// 参数2 删除条件
	// 参数3 删除条件值数组
	public static void delete(Context con, String key) {
		DBOpenHelp dbhelper = new DBOpenHelp(con, con
				.getString(R.string.db_name));
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String whereClause = "key=?";// 删除条件
		String[] whereArgs = { key }; // 删除条件参数
		db.delete(TABLENAME, whereClause, whereArgs);// 执行删除
		DBHelp.close(dbhelper, db, null);
	}

	public static void deleteSQL(Context con, String key) {
		DBOpenHelp dbhelper = new DBOpenHelp(con, con
				.getString(R.string.db_name));
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String delSQL = "delete from config where key = '" + key + "'";
		db.execSQL(delSQL);
		DBHelp.close(dbhelper, db, null);
	}

	// 根据key获得对应的Value
	public static String getValueByKey(Context con, String key) {
		DBOpenHelp dbhelper = new DBOpenHelp(con, con
				.getString(R.string.db_name));
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select value from config where key='"
				+ key + "'", null);
		String value = "";
		while (cursor.moveToNext()) {
			value = cursor.getString(0);
		}
		DBHelp.close(dbhelper, db, null);
		return value;
	}

	// 在Android中查询数据是通过Cursor类来实现的，当我们使用SQLiteDatabase.query()方法时，会得到一个Cursor对象，Cursor指向的就是每一条数据。它提供了很多有关查询的方法，具体方法如下：
	// public Cursor query(String table,String[] columns,String
	// selection,String[] selectionArgs,String groupBy,String having,String
	// orderBy,String limit);
	// 各个参数的意义说明：
	// 参数table:表名称
	// 参数columns:列名称数组
	// 参数selection:条件字句，相当于where
	// 参数selectionArgs:条件字句，参数数组
	// 参数groupBy:分组列
	// 参数having:分组条件
	// 参数orderBy:排序列
	// 参数limit:分页查询限制
	// 参数Cursor:返回值，相当于结果集ResultSet
	// Cursor是一个游标接口，提供了遍历查询结果的方法，如移动指针方法move()，获得列值方法getString()等.
	// Cursor游标常用方法
	// getCount() // 获得总的数据项数
	// isFirst() // 判断是否第一条记录
	// isLast() // 判断是否最后一条记录
	// moveToFirst() // 移动到第一条记录
	// moveToLast() // 移动到最后一条记录
	// move(int offset) // 移动到指定记录
	// moveToNext()// 移动到下一条记录
	// moveToPrevious() // 移动到上一条记录
	// getColumnIndexOrThrow(String columnName) // 根据列名称获得列索引
	// getInt(int columnIndex) // 获得指定列索引的int类型值
	// getString(int columnIndex)// 获得指定列缩影的String类型值

	public static List<Config> query(Context con) {
		DBOpenHelp dbhelper = new DBOpenHelp(con, con
				.getString(R.string.db_name));
		SQLiteDatabase db = dbhelper.getReadableDatabase();

		String columns[] = null;// 参数columns:列名称数组
		String selection = "";// 参数selection:条件字句:"ROWGUID=?"
		String selectionArgs[] = null;// 参数selectionArgs:条件字句，参数数组
		String groupBy = null;// 参数groupBy:分组列
		String having = null;// 参数having:分组条件
		String orderBy = null;// 参数orderBy:排序列
		// 参数limit:分页查询限制
		Cursor cursor = db.query(TABLENAME, columns, selection, selectionArgs,
				groupBy, having, orderBy);// 查询获得游标
		List<Config> list = new ArrayList<Config>();
		if (cursor.moveToFirst()) {// 判断游标是否为空
			// 遍历游标
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.move(i);
				Config mdate = new Config();
				mdate.setKey(cursor.getString(0).toString());
				mdate.setValue(cursor.getString(1).toString());
				list.add(mdate);
			}
		}
		DBHelp.close(dbhelper, db, cursor);
		return list;
	}

}
