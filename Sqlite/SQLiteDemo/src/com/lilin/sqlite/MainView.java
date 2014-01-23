package com.lilin.sqlite;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.lilin.sqlite.db.DBHelp;
import com.lilin.sqlite.db.DBOpenHelp;

public class MainView extends Activity {

	private TextView init_txt;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init_txt = (TextView) findViewById(R.id.init_txt);
		DBInit();// 初始化数据库
	}

	private void DBInit() {
		// 判断数据库是否存在
		boolean isDbExist = DBHelp.isExistDB(getString(R.string.dbpath));
		if (isDbExist) {
			init_txt.setText("数据库已存在，正常启动！");// 系统正在启动
		} else {// 系统正在初始化

			new Thread() {
				public void run() {
					new Handler().post(new Runnable() {
						public void run() {
							// 删除原来数据库
							File dbfile = new File(getString(R.string.dbpath));
							if (dbfile.exists()) {
								dbfile.delete();
							}
							// 创建数据库文件夹
							File folder = new File(getString(R.string.dbfold));
							if (!folder.exists()) {
								folder.mkdirs();
							}
							// 创建一个新的数据库
							DBOpenHelp openHelper = new DBOpenHelp(
									MainView.this, getString(R.string.db_name),
									11);
							openHelper.getWritableDatabase();
							// 拷贝备份数据库
//							try {
//								FileHelp.copyFileFromAssetsToFolder(
//										MainView.this,
//										getString(R.string.db_backup_name),
//										getString(R.string.dbfold));
//							} catch (IOException e) {
//								e.printStackTrace();
//								LogHelp.Log2SDErr(e,
//										getString(R.string.logpath),
//										getString(R.string.app_name));
//							}
//							ToastHelp.showToast(MainView.this, "初始化成功");
//							LogHelp.LogI("数据库初始化成功！");
						}
					});
				}
			}.start();
		}

	}

}