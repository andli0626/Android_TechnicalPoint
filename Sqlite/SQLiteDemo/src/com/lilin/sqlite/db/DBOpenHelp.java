package com.lilin.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * 
 * @author lilin
 * @date 2012-3-22 下午07:40:26
 * @annotation 数据库创建帮助类
 */
public class DBOpenHelp extends SQLiteOpenHelper {
	// SQLite采用的是动态数据类型，会根据存入值自动判断。
	// 五种常用的数据类型：
	// NULL: 这个值为空值
	// varchar(n)：长度不固定且其最大长度为 n 的字串，n不能超过 4000。
	// char(n)：长度固定为n的字串，n不能超过 254。
	// integer: 值被标识为整数,依据值的大小可以依次被存储为1,2,3,4,5,6,7,8.
	// real: 所有值都是浮动的数值,被存储为8字节的IEEE浮动标记序号.
	// text: 值为文本字符串,使用数据库编码存储(TUTF-8, UTF-16BE or UTF-16-LE).
	// blob: 值是BLOB数据块，以输入的数据格式进行存储。如何输入就如何存储,不改变格式。
	// data ：包含了 年份、月份、日期。
	// time： 包含了 小时、分钟、秒。

	public static final int VERSION = 1;

	public DBOpenHelp(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBOpenHelp(Context context, String name) {
		this(context, name, VERSION);
	}

	public DBOpenHelp(Context context, String name, int version) {
		this(context, name, null, version);
	}

	// 在数据库第一次生成的时候会调用这个方法，也就是说，只有在创建数据库的时候才会调用，
	// 当然也有一些其它的情况，一般我们在这个方法里边生成数据库表。
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 二进制资源表
		db.execSQL("create table res(" //
				+ "key nvarchar,"//
				+ "value blob" //
				+ ")");

		// 配置表:用于保存一些全局变量
		db.execSQL("create table config(" //
				+ "key nvarchar," //
				+ "value nvarchar"//
				+ ")");

		// 类别表
		db.execSQL("create table category("//
				+ "id integer primary key autoincrement,"// 主键
				+ "name nvarchar,"// 名称
				+ "value nvarchar,"// 排序字段:权值
				+ "uptime nvarchar,"// 更新时间
				+ "decribe nvarchar"// 描述字段
				+ ")");
	}

	// 当数据库需要升级的时候，Android系统会主动的调用这个方法。
	// 一般我们在这个方法里边删除数据表，并建立新的数据表，当然是否还需要做其他的操作，完全取决于应用的需求。
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
