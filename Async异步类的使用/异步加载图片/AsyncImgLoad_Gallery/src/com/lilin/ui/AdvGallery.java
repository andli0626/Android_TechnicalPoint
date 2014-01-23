package com.lilin.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * 自定义的Gallery
 * 
 * @author lilin
 * @date 2011-12-22 上午08:20:50
 * @ClassName: AdvGallery
 */
public class AdvGallery extends Gallery {

	public AdvGallery(Context context) {
		super(context);

	}

	public AdvGallery(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// 返回false 解决Gallery拖拽滑动过快
		return false;
	}

	public void setUnselectedAlpha(float unselectedAlpha) {
		unselectedAlpha = 1.0f;
		super.setUnselectedAlpha(unselectedAlpha);
	}

}
