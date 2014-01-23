package com.betterman.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class SDCardHelp {
	// 得到图片名称
	public static String getName(String path) {
		int lastSeparatorIndex = path.lastIndexOf("/");
		return path.substring(lastSeparatorIndex + 1, path.length());
	}

	// 获得SDCard下所有图片的路径
	public static List<String> getImagePathFromSDCard(String path) {
		List<String> imagePaths = new ArrayList<String>();
		try {
			File file = new File(path);
			File[] files = file.listFiles();
			for (File theFile : files) {
				if (isImageFile(theFile.getPath())) {
					imagePaths.add(theFile.getPath());
				}
			}
		} catch (Exception e) {
			return imagePaths;
		}
		return imagePaths;
	}

	// 获得SDCard下所有JPG格式图片的路径：加密后的图片
	// public static List<String> getJpgImagePathFromSDCard(String path) {
	// List<String> imagePaths = new ArrayList<String>();
	// try {
	// File file = new File(path);
	// File[] files = file.listFiles();
	// for (File theFile : files) {
	// if (isJPGImageFile(theFile.getPath())) {
	// if (isJaMiImage(theFile)) {// 判断是不是加密的图片
	// imagePaths.add(theFile.getPath());
	// }
	// }
	// }
	// } catch (Exception e) {
	// return imagePaths;
	// }
	// return imagePaths;
	// }

	/**
	 * 
	 * @author lilin
	 * @date 2012-5-8 下午2:45:37
	 * @annotation 获取图片列表：屏蔽已选中的
	 */
	// public static List<String> getPicListFromSDCard(Context con, String path,
	// int tableName) {
	// List<String> imagePaths = new ArrayList<String>();
	// try {
	// File file = new File(path);
	// File[] files = file.listFiles();
	// for (File theFile : files) {
	// if (isJPGImageFile(theFile.getPath())) {
	// if (isJaMiImage(theFile)) {// 判断是不是加密的图片
	// switch (tableName) {
	// case R.string.qwt_rcglsb:// 商户日常管理上报
	// if (!ShangHuCheckInfoDBHelp.isSelectPic(con,
	// theFile.getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.wthc:// 问题核查
	// if (!WentisbDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.wtsb:// 问题上报
	// if (!WentisbDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.cpgl:// 抄牌管理
	// if (!CopyLicenseplateDBHelp.isSelectPic(con,
	// theFile.getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.tcyw:// 拖车业务
	// if (!TowCarDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.xcjc:// 现场纠处
	// if (!LacalePunishDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.dccf:// 当场处罚
	// if (!XcchufaDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.shgl:// 商户管理
	// if (!ShangHuSBDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.gdjg:// 工地监管
	// if (!SiteSupervisionDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.cgxc:// 城管宣传
	// if (!CityManagementAdDBHelp.isSelectPic(con,
	// theFile.getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.lmzq:// 路面勤务
	// if (!RoadAttendanceDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.pdcl_detail:// 派单处理
	// if (!PaiDanDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.zkwp:// 暂扣物品
	// if (!ZankouWPDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.tpsb:// 图片上报
	// if (!XianChangqzDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// case R.string.zxrwfk:// 中心任务反馈
	// if (!CsQwZxrwfkDBHelp.isSelectPic(con, theFile
	// .getPath().toString())) {
	// imagePaths.add(theFile.getPath());
	// }
	// break;
	// default:
	// imagePaths.add(theFile.getPath());
	// break;
	// }
	// }
	// }
	// }
	// } catch (Exception e) {
	// return imagePaths;
	// }
	// return imagePaths;
	// }

	// 获得SDCard下所有JPG格式图片的路径
	// /sdcard/mobileoa/attachdownload/a8cbae6e-7bce-43dc-9308-10cd2cfdfcf7/
	public static List<String> getPicFromSDCard(String path) {
		List<String> imagePaths = new ArrayList<String>();
		try {
			File file = new File(path);
			File[] files = file.listFiles();
			for (File theFile : files) {
				if (isJPGImageFile(theFile.getPath())) {
					imagePaths.add(theFile.getPath());
				}
			}
		} catch (Exception e) {
			return imagePaths;
		}
		return imagePaths;
	}

	// 判断是否有内存卡
	public static boolean hasSDcard() {
		if (Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) { // sd卡存在
			return true;
		}
		return false;
	}

	// 获得地图文件的路径
	public static String getMapFilePath(String foldername) {
		// 获取根目录
		File sdDir = Environment.getExternalStorageDirectory();
		// return sdDir.toString() + "/" + foldername + "/" + foldername +
		// ".map";
		return sdDir.toString() + "/cscgtmap/" + foldername + ".map";
	}

	// 判断地图文件是否存在
	public static boolean hasMapFile(String foldername) {
		boolean has = false;
		File sdDir = Environment.getExternalStorageDirectory();
		File file = new File(sdDir.toString() + "/cscgtmap/" + foldername
				+ ".map");
		if (file.isFile()) {
			has = true;

		}
		return has;
	}

	// 获取文件保存路径
	public static File getFileSavePath(String savepath) {
		File filePath = null;
		if (hasSDcard()) {
			filePath = new File(savepath);
			if (!filePath.isDirectory()) {// 判断文件存在与否，不存在就创建
				filePath.mkdirs();
			}
			return filePath;
		} else {
			return null;
		}
	}

	// 判断是否是图片文件
	public static boolean isImageFile(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length()).toLowerCase();
		if (extension.equals("jpg") || extension.equals("png")
				|| extension.equals("gif") || extension.equals("jpeg")
				|| extension.equals("bmp")) {
			return true;
		}
		return false;
	}

	// 判断是否是JPG图片文件
	public static boolean isJPGImageFile(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length()).toLowerCase();
		if (extension.equals("jpg") || extension.equals("png")
				|| extension.equals("jpeg")) {
			return true;
		}
		return false;
	}

	public static boolean deleteFile(String path) {
		File file = new File(path);
		return file.delete();
	}

	public static boolean deleteMutiFile(String photopath) {
		// 将保存成功的照片删除
		String[] photopaths = photopath.split(";");
		int i = 0;
		for (String s : photopaths) {
			File file = new File(s);
			file.delete();
			i++;
		}
		if (i == photopaths.length) {
			return true;
		}
		return false;
	}

	/**
	 * 递归删除文件和文件夹
	 * 
	 * @param file
	 *            要删除的根目录
	 */
	public static void RecursionDeleteFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				RecursionDeleteFile(f);
			}
			file.delete();
		}
	}

}
