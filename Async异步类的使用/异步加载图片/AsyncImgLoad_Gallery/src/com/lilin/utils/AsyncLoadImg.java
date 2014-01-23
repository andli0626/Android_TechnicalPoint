package com.lilin.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;

public class AsyncLoadImg {
	// 为了加快速度，在内存中开启缓存（主要应用于重复图片较多时，或者同一个图片要多次被访问，比如在ListView时来回滚动）
	public Map<String, SoftReference<Drawable>> imgCache = new HashMap<String, SoftReference<Drawable>>();

	private ExecutorService executorService = Executors.newFixedThreadPool(3);
	private final Handler handler = new Handler();

	// 加载图片
	public Drawable loadDrawable(final Context context, final String imgURL,
			final IPictureCallback imgCallback) {

		// 如果缓存过就从缓存中取出数据
		if (imgCache.containsKey(imgURL)) {

			SoftReference<Drawable> softReference = imgCache.get(imgURL);
			if (softReference.get() != null) {
				System.out.println("使用缓存！");
				return softReference.get();
			}

		}
		// 网络可用
		if (HttpUtils.isNetwork(context)) {
			executorService.submit(new Runnable() {
				public void run() {
					try {
						final Drawable drawable = HttpUtils.loadImgFromURL(imgURL);
						if (drawable != null) {
							imgCache.put(imgURL, new SoftReference<Drawable>(drawable));
							handler.post(new Runnable() {
								public void run() {
									imgCallback.imageLoaded(drawable, imgURL);
								}
							});
						}

					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
		return null;
	}
}
