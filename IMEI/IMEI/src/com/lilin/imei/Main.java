package com.lilin.imei;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity {
	TextView network_text;
	EditText imei_et, imsi_et;
	TextView rom_tv, romremimd_tv, mac_tv, sdcard_tv, cpu_tv, version_tv,
			style_tv, number_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("设备信息");
		setTitleColor(Color.YELLOW);
		getView();

		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(TELEPHONY_SERVICE);
		// 区分手机网络
		String operator = tm.getSimOperator();
		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002")
					|| operator.equals("46007")) {
				network_text.setText("中国移动");
			} else if (operator.equals("46001")) {
				network_text.setText("中国联通");
			} else if (operator.equals("46003")) {
				network_text.setText("中国电信");
			}
		}
		imei_et.setText(tm.getDeviceId());// IMEI
		imsi_et.setText(tm.getSubscriberId());// IMSI
		String rom = formatSize(getRomMemroy()) + " / "
				+ formatSize(getRomRemind());
		rom_tv.setText(rom);// ROM
		mac_tv.setText(getMacInfo());// MAC地址
		String sdcard = formatSize(getSDCard()) + " / "
				+ formatSize(getSDCardMemory());
		sdcard_tv.setText(sdcard);// SDCard
		String a[] = getCpuInfo();
		cpu_tv.setText(a[1] + "MHz");// 处理器
		String b[] = getVersion();
		version_tv.setText(b[1]);// android版本
		style_tv.setText(b[2]);// 手机型号

		// NETWORK_TYPE_UNKNOWN 网络类型未知 0
		// NETWORK_TYPE_GPRS GPRS网络 1
		// NETWORK_TYPE_EDGE EDGE网络 2
		// NETWORK_TYPE_UMTS UMTS网络 3
		// NETWORK_TYPE_HSDPA HSDPA网络 8
		// NETWORK_TYPE_HSUPA HSUPA网络 9
		// NETWORK_TYPE_HSPA HSPA网络 10
		// NETWORK_TYPE_CDMA CDMA网络,IS95A 或 IS95B. 4
		// NETWORK_TYPE_EVDO_0 EVDO网络, revision 0. 5
		// NETWORK_TYPE_EVDO_A EVDO网络, revision A. 6
		// NETWORK_TYPE_1xRTT 1xRTT网络 7
		// System.out.println("获取SIM卡提供的移动国家码和移动网络码=" + tm.getNetworkType());
		// System.out.println("服务商名称=" + tm.getSimOperatorName());
		// System.out.println("SIM卡的序列号=" + tm.getSimSerialNumber());
		// System.out.println("手机号码=" + tm.getLine1Number());
		// System.out.println("设备的软件版本号=" + tm.getDeviceSoftwareVersion());

		//
		// /*
		// * 电话状态： 1.tm.CALL_STATE_IDLE=0 无活动 2.tm.CALL_STATE_RINGING=1 响铃
		// * 3.tm.CALL_STATE_OFFHOOK=2 摘机
		// */
		// tm.getCallState();// int
		//
		// /*
		// * 电话方位：
		// */
		// tm.getCellLocation();// CellLocation
		// /*
		// * 附近的电话的信息: 类型：List<NeighboringCellInfo>
		// * 需要权限：android.Manifest.permission#ACCESS_COARSE_UPDATES
		// */
		// tm.getNeighboringCellInfo();// List<NeighboringCellInfo>
		//
		// /*
		// * 获取ISO标准的国家码，即国际长途区号。 注意：仅当用户已在网络注册后有效。 在CDMA网络中结果也许不可靠。
		// */
		// tm.getNetworkCountryIso();// String
		//
		// /*
		// * MCC+MNC(mobile country code + mobile network code)
		// 注意：仅当用户已在网络注册时有效。
		// * 在CDMA网络中结果也许不可靠。
		// */
		// tm.getNetworkOperator();// String
		//
		// /*
		// * 按照字母次序的current registered operator(当前已注册的用户)的名字 注意：仅当用户已在网络注册时有效。
		// * 在CDMA网络中结果也许不可靠。
		// */
		// tm.getNetworkOperatorName();// String
		//
		// /*
		// * 手机类型： 例如： PHONE_TYPE_NONE 无信号 PHONE_TYPE_GSM GSM信号 PHONE_TYPE_CDMA
		// * CDMA信号
		// */
		// tm.getPhoneType();// int
		//
		// /*
		// * Returns the ISO country code equivalent for the SIM provider's
		// * country code. 获取ISO国家码，相当于提供SIM卡的国家码。
		// */
		// tm.getSimCountryIso();// String
		//

		// /*
		// * SIM的状态信息： SIM_STATE_UNKNOWN 未知状态 0 SIM_STATE_ABSENT 没插卡 1
		// * SIM_STATE_PIN_REQUIRED 锁定状态，需要用户的PIN码解锁 2 SIM_STATE_PUK_REQUIRED
		// * 锁定状态，需要用户的PUK码解锁 3 SIM_STATE_NETWORK_LOCKED 锁定状态，需要网络的PIN码解锁 4
		// * SIM_STATE_READY 就绪状态 5
		// */
		// tm.getSimState();// int
		//
		//
		// /*
		// * 取得和语音邮件相关的标签，即为识别符 需要权限：READ_PHONE_STATE
		// */
		// tm.getVoiceMailAlphaTag();// String
		//
		// /*
		// * 获取语音邮件号码： 需要权限：READ_PHONE_STATE
		// */
		// tm.getVoiceMailNumber();// String
		//
		// /*
		// * ICC卡是否存在
		// */
		// tm.hasIccCard();// boolean
		//
		// /*
		// * 是否漫游: (在GSM用途下)
		// */
		// tm.isNetworkRoaming();//

	}

	public void getView() {
		network_text = (TextView) findViewById(R.id.network_text);
		imei_et = (EditText) findViewById(R.id.imei_et);
		imsi_et = (EditText) findViewById(R.id.imsi_et);
		rom_tv = (TextView) findViewById(R.id.rom_et);
		mac_tv = (TextView) findViewById(R.id.mac_et);
		sdcard_tv = (TextView) findViewById(R.id.sdcard_et);
		cpu_tv = (TextView) findViewById(R.id.cpu_et);
		version_tv = (TextView) findViewById(R.id.version_et);
		style_tv = (TextView) findViewById(R.id.style_et);
		number_tv = (TextView) findViewById(R.id.number_et);
	}

	// 获得ROM的总大小
	public long getRomMemroy() {
		long romInfo;
		romInfo = getTotalInternalMemorySize();
		return romInfo;
	}

	// 获得ROM的剩余大小
	public long getRomRemind() {
		long romInfo;
		romInfo = getTotalInternalMemorySize();
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		romInfo = blockSize * availableBlocks;
		return romInfo;
	}

	// 获得ROM的总大小
	public long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	public String[] getVersion() {
		String[] version = { "null", "null", "null", "null" };
		String str1 = "/proc/version";
		String str2;
		String[] arrayOfString;
		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			version[0] = arrayOfString[2];// KernelVersion
			localBufferedReader.close();
		} catch (IOException e) {
		}
		version[1] = Build.VERSION.RELEASE;// firmware version
		version[2] = Build.MODEL;// model
		version[3] = Build.DISPLAY;// system version
		return version;
	}

	// 系统可用的内存大小
	public long getRAMMemory() {
		ActivityManager am = (ActivityManager) this
				.getSystemService(ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		return mi.availMem;
	}

	// CPU信息
	public String[] getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" };
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return cpuInfo;
	}

	// 开机时间
	private String getTimes() {
		long ut = SystemClock.elapsedRealtime() / 1000;
		if (ut == 0) {
			ut = 1;
		}
		int m = (int) ((ut / 60) % 60);
		int h = (int) ((ut / 3600));
		return h + "时" + m + "分";
	}

	// SDCard总大小
	public long getSDCard() {
		long sdCardInfo = 0;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize = sf.getBlockSize();
			long bCount = sf.getBlockCount();
			sdCardInfo = bSize * bCount;// 总大小
		}
		return sdCardInfo;
	}

	// SDCard可用大小
	public long getSDCardMemory() {
		long sdCardInfo = 0;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize = sf.getBlockSize();
			long availBlocks = sf.getAvailableBlocks();
			sdCardInfo = bSize * availBlocks;// 可用大小
		}
		return sdCardInfo;
	}

	// 获得MAC地址
	public String getMacInfo() {
		String mac;
		WifiManager wifiManager = (WifiManager) this
				.getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo.getMacAddress() != null) {
			mac = wifiInfo.getMacAddress();
		} else {
			mac = "Fail";
		}
		return mac;
	}

	// 格式化
	public String formatSize(long size) {
		String suffix = null;
		float fSize = 0;

		if (size >= 1024) {
			suffix = "KB";
			fSize = size / 1024;
			if (fSize >= 1024) {
				suffix = "MB";
				fSize /= 1024;
			}
			if (fSize >= 1024) {
				suffix = "GB";
				fSize /= 1024;
			}
		} else {
			fSize = size;
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
		StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
		if (suffix != null)
			resultBuffer.append(suffix);
		return resultBuffer.toString();
	}

}