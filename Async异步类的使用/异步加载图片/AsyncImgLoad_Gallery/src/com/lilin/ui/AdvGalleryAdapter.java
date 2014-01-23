package com.lilin.ui;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Gallery.LayoutParams;

import com.lilin.utils.AsyncLoadImg;
import com.lilin.utils.IPictureCallback;

/**
 * AdvGallery是适配器
 * 
 * @author lilin
 * @date 2011-12-22 上午08:27:25
 * @ClassName: AdvGalleryAdapter
 */
public class AdvGalleryAdapter extends BaseAdapter {
	private Context context = null;
	private Gallery gallery = null;
	public List<String> imgURL = null;
	private AsyncLoadImg asyncLoadImg = null;

	public AdvGalleryAdapter(Context context, List<String> imgURL,
			Gallery gallery) {
		this.context = context;
		this.imgURL = imgURL;
		this.gallery = gallery;
		asyncLoadImg = new AsyncLoadImg();
	}

	public int getCount() {
		// 可以循环滚动
		return Integer.MAX_VALUE;
	}

	public Object getItem(int position) {
		return imgURL.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imgView = new ImageView(context);
		int i = position % imgURL.size();
		String curURL = imgURL.get(i);
		System.out.println(i + " URL=" + curURL);
		imgView.setTag(curURL);
		// 异步加载缓存图片
		Drawable cachedImg = asyncLoadImg.loadDrawable(context, curURL,
				new IPictureCallback() {
					public void imageLoaded(Drawable drawable, String imagURL) {
						// 根据Tag获取ImageView对象
						ImageView imgViewByTag = (ImageView) gallery
								.findViewWithTag(imagURL);
						if (imgViewByTag != null && drawable != null) {
							imgViewByTag.setImageDrawable(drawable);
							notifyDataSetChanged();
						}

					}
				});
		if (cachedImg != null) {
			imgView.setImageDrawable(cachedImg);
		} else {
			// imgView.setImageResource(R.drawable.ic_launcher);
		}
		// 设置边界对齐
		imgView.setAdjustViewBounds(true);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		imgView.setLayoutParams(new Gallery.LayoutParams(params));
		// 设置比例类型
		imgView.setScaleType(ImageView.ScaleType.FIT_XY);
		return imgView;
	}

}
