package com.betterman.util;


public class MiMa {
	// 加密方法：
	public static String jiami(String a1, int mimalength) {
		// long is 3
		String str = a1.toLowerCase();
		String str1 = "";
		if (str == null || str == "")
			return null;
		int length = str.length();
		int tlen = mimalength - length;
		for (int i = 0; i < tlen; i++) {
			str1 = str1 + "D";
		}
		if (tlen >= 0) {
			for (int j = 0; j < length; j++) {
				if (checkInteger(str.substring(j, j + 1))) {
					int integer = Integer.parseInt(str.substring(j, j + 1)) + 3;
					if (integer == 10) {
						str1 = str1 + String.valueOf("X");
					} else if (integer == 11) {
						str1 = str1 + String.valueOf("Y");
					} else if (integer == 12) {
						str1 = str1 + String.valueOf("Z");
					} else {
						str1 = str1 + String.valueOf(integer);
					}
				} else {//
					if (str.substring(j, j + 1).equals("x")) {
						str1 = str1 + "0";
					} else if (str.substring(j, j + 1).equals("y")) {
						str1 = str1 + "1";
					} else if (str.substring(j, j + 1).equals("z")) {
						str1 = str1 + "2";
					} else {
						int str_integer = (int) str.charAt(j) + 3;
						char c_str = (char) str_integer;
						str1 = str1 + c_str;
					}
				}
			}
		} else {
			// this.bulidError("encrypt", "long is not over 12!");
			return null;
		}
		return str1;
	}

	// 解密方法：
	public static String jiemi(String str) {
		String str1 = "";
		if (str == null || str == "")
			return null;
		int length = str.length();
		for (int i = 0; i < length; i++) {
			String str2 = str.substring(i, i + 1);
			if (checkInteger(str2)) {
				if (str2.equals("0")) {
					str1 = str1 + "x";
				} else if (str2.equals("1")) {
					str1 = str1 + "y";
				} else if (str2.equals("2")) {
					str1 = str1 + "z";
				} else {
					int integer = Integer.parseInt(str2) - 3;
					str1 = str1 + String.valueOf(integer);
				}
			} else {
				if (str2.equals("D")) {
				} else if (str2.equals("X")) {
					int integer = 10 - 3;
					str1 = str1 + String.valueOf(integer);
				} else if (str2.equals("Y")) {
					int integer = 11 - 3;
					str1 = str1 + String.valueOf(integer);
				} else if (str2.equals("Z")) {
					int integer = 12 - 3;
					str1 = str1 + String.valueOf(integer);
				} else {
					int i_str = (int) str2.charAt(0) - 3;
					char c_str = (char) i_str;
					str1 = str1 + c_str;
				}
			}
		}
		return str1;
	}

	// 整数校验方法
	private static boolean checkInteger(String string) {
		// mark information
		boolean mark = false;
		if (string.equals("0") || string.equals("1") || string.equals("2")
				|| string.equals("3") || string.equals("4")
				|| string.equals("5") || string.equals("6")
				|| string.equals("7") || string.equals("8")
				|| string.equals("9"))
			mark = true;
		return mark;
	}

}
