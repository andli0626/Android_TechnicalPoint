package com.lilin.sqlite.db;

/**  
 * @Project: SQLiteDemo
 * @Title: DBHelp.java
 * @Package com.lilin.sqlite.db
 * @Description: deal db
 * @author lilin andlil@163.com
 * @date 2012-3-22 下午07:58:03
 * @Copyright: 2012 andli.iteye.com Inc. All rights reserved.
 * @version V1.0  
 */
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHelp {

	// SQLiteDatabase的常用方法

	// openDatabase (String path, SQLiteDatabase.CursorFactory factory, int
	// flags)

	// 打开或创建数据库
	// openOrCreateDatabase(String path,SQLiteDatabase.CursorFactory factory)

	// insert(String table,String nullColumnHack,ContentValues values)// 插入一条记录
	// 参数1 表名称，
	// 参数2 空列的默认值
	// 参数3 ContentValues类型的一个封装了列名称和列值的Map；

	// delete(String table,String whereClause,String[] whereArgs)// 删除一条记录
	// 参数1 表名称
	// 参数2 删除条件
	// 参数3 删除条件值数组

	// query(String table,String[] columns,String selection,String[]
	// selectionArgs,String groupBy,String having,String orderBy)// 查询一条记录
	// 在Android中查询数据是通过Cursor类来实现的，当我们使用SQLiteDatabase.query()方法时，会得到一个Cursor对象，Cursor指向的就是每一条数据。它提供了很多有关查询的方法，具体方法如下：
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

	// update(String table,ContentValues values,String whereClause,String[]
	// whereArgs)// 修改记录
	// 参数1 表名称
	// 参数2 跟行列ContentValues类型的键值对Key-Value
	// 参数3 更新条件（where字句）
	// 参数4 更新条件数组

	// execSQL(String sql)// 执行一条SQL语句

	// close()// 关闭数据库

	/**
	 * 
	 * @author lilin
	 * @date 2012-3-22 下午07:46:55
	 * @annotation 判断数据库是否存在
	 */
	public static boolean isExistDB(String dbpath) {
		SQLiteDatabase checkDB = null;
		try {
			checkDB = SQLiteDatabase.openDatabase(dbpath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	// 关闭数据库操作
	public static void close(DBOpenHelp dbhelper, SQLiteDatabase db,
			Cursor cursor) {
		if (cursor != null) {
			cursor.close();
		}
		if (db != null) {
			db.close();
		}
		if (dbhelper != null) {
			dbhelper.close();
		}
	}

}
