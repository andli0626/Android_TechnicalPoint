package com.lilin.utils;

import android.graphics.drawable.Drawable;

//对外界开放的回调接口
public interface IPictureCallback {
	// 注意 此方法是用来设置目标对象的图像资源
	public void imageLoaded(Drawable imageDrawable, String imgURL);
}
