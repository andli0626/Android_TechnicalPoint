package com.betterman.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author liyc
 * @time 2012-1-9 下午11:13:45
 * @annotation
 */

public class DateHelp {

	public static String convertDate(Date date, String format) {
		if (date != null) {
			DateFormat format1 = new SimpleDateFormat(format);
			String s = format1.format(date);
			return s;
		}
		return "";
	}

	public static String getCurTime() {
		return convertDate(new Date(), "HH:mm");
	}

	public static String getCurrentTime() {
		return convertDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	public static String getCurDate() {
		return convertDate(new Date(), "yyyy-MM-dd");
	}

	public static String getCurDate(Date date) {
		return convertDate(date, "yyyy-MM-dd");
	}

	@SuppressWarnings("static-access")
	public static String getFormateDate(int type, Date date) {
		// 获取系统时间，并格式化
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int mounth = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(calendar.MINUTE);
		int secound = calendar.get(calendar.SECOND);

		// 格式一：2011-11-1 12-12-66+mima 作为图片文件名
		if (type == 1) {
			String a[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j" };
			Random random = new Random();
			int i = random.nextInt(10);
			String extra = a[i];
			String mima = MiMa.jiami(extra + AddZero(min) + AddZero(secound)
					+ random.nextInt(10), 6);
			return String.valueOf(year) + "-" + AddZero(mounth) + "-"
					+ AddZero(day) + " " + AddZero(hour) + "-" + AddZero(min)
					+ "-" + AddZero(secound) + " " + mima;
		}
		// 格式二：2011年11月1日 12时12分45秒 写在照片上
		else if (type == 2) {
			return String.valueOf(year) + "年" + AddZero(mounth) + "月"
					+ AddZero(day) + "日  " + AddZero(hour) + "时" + AddZero(min)
					+ "分" + AddZero(secound) + "秒";
		}
		// 格式三：2011-11-1 12-12-11 作为录音文件名
		else if (type == 3) {
			return String.valueOf(year) + "-" + AddZero(mounth) + "-"
					+ AddZero(day) + " " + AddZero(hour) + "-" + AddZero(min)
					+ "-" + AddZero(secound) + " ";
		}
		return null;
	}

	/**
	 * @author lilin
	 * @date 2012-2-17 上午09:27:52
	 * @annotation 获取指定格式的日期
	 */
	@SuppressWarnings("static-access")
	public static String getFormateDate(int type) {
		// 获取系统时间，并格式化
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int mounth = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(calendar.MINUTE);
		int secound = calendar.get(calendar.SECOND);

		// 格式一：2011-11-1 12-12-66+mima 作为图片文件名
		if (type == 1) {
			String a[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j" };
			Random random = new Random();
			int i = random.nextInt(10);
			String extra = a[i];
			String mima = MiMa.jiami(extra + AddZero(min) + AddZero(secound)
					+ random.nextInt(10), 6);
			return String.valueOf(year) + "-" + AddZero(mounth) + "-"
					+ AddZero(day) + " " + AddZero(hour) + "-" + AddZero(min)
					+ "-" + AddZero(secound) + " " + mima;
		}
		// 格式二：2011年11月1日 12时12分45秒 写在照片上
		else if (type == 2) {
			return String.valueOf(year) + "年" + AddZero(mounth) + "月"
					+ AddZero(day) + "日  " + AddZero(hour) + "时" + AddZero(min)
					+ "分" + AddZero(secound) + "秒";
		}
		// 格式三：2011-11-1 12-12-11 作为录音文件名
		else if (type == 3) {
			return String.valueOf(year) + "-" + AddZero(mounth) + "-"
					+ AddZero(day) + " " + AddZero(hour) + "-" + AddZero(min)
					+ "-" + AddZero(secound) + " ";
		}
		return null;
	}

	public static String AddZero(int i) {
		if (i >= 0 && i <= 9) {
			return "0" + i;
		}
		return String.valueOf(i);

	}

	/**
	 * 
	 * @author lilin
	 * @date 2012-3-20 上午09:30:55
	 * @annotation string 转 日期类型
	 */
	public static Date stringToDate(String string) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date stringToDate2(String string) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * @author lilin
	 * @date 2012-3-20 上午09:34:32
	 * @annotation 日期比较：都是sring型:0相等，>0大于，<0 小于
	 */
	public static long compareDate(String date1, String date2) {
		Date beginTime = DateHelp.stringToDate(date1);
		Date endTime = DateHelp.stringToDate(date2);
		return beginTime.getTime() - endTime.getTime();
	}

	/**
	 * 
	 * @author lilin
	 * @date 2012-3-20 上午09:35:01
	 * @annotation 日期比较：都是Date型
	 */
	public static long compareDate(Date date1, Date date2) {
		return date1.getTime() - date2.getTime();
	}

	private static long lastClickTime;

	// 500毫秒里不起作用
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
