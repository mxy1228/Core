package com.changyou.mgp.sdk.mbi.image;

import java.io.File;

import android.content.Context;
import android.widget.ImageView;

import com.changyou.mgp.sdk.mbi.config.SDKConfig;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * 
 * 功能描述：SDK加载图片
 *
 * @author 徐萌阳(xumengyang)
 *
 * @date 2014-1-22
 */
public class MGPImageLoader{

	private volatile static MGPImageLoader mInstance;
	
	public static MGPImageLoader getInstance(Context context){
		if(mInstance == null){
			synchronized (MGPImageLoader.class) {
				if(mInstance == null){
					mInstance = new MGPImageLoader(context);
				}
			}
		}
		return mInstance;
	}
	
	private MGPImageLoader(Context context){
		//Returns application cache directory. 
		//Cache directory will be created on SD card ("/Android/data/[app_package_name]/cache") 
		//if card is mounted and app has appropriate permission. Else - Android defines cache directory on device's file system
		File diskCache = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.threadPoolSize(SDKConfig.IMAGE_LOADER_THREAD_COUNT)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.discCache(new UnlimitedDiscCache(diskCache))
		.imageDownloader(new BaseImageDownloader(context, SDKConfig.LOAD_IMAGE_CONNECT_TIMEOUT, SDKConfig.LOAD_IMAGE_READ_TIMEOUT))
		.build();
		ImageLoader.getInstance().init(config);
	}
	
	/**
	 * 
	 * 功能描述：下载图片
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param url 图片url
	 * @param options 图片下载参数
	 * @param listener 图片下载回调
	 * @return void
	 * @date 2014-1-22 下午7:45:02
	 *
	 */
	public void loadImage(String url,DisplayImageOptions options,ImageLoadingListener listener){
		ImageLoader.getInstance().loadImage(url, options, listener);
	}
	
	/**
	 * 
	 * 功能描述：加载图片并在ImageView中显示
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param url 图片url
	 * @param imageview 加载图片的ImageView
	 * @return void
	 * @date 2014-1-22 下午8:21:38
	 *
	 */
	public void displayImage(String url,ImageView imageview){
		ImageLoader.getInstance().displayImage(url, imageview);
	}
	
	/**
	 * 
	 * 功能描述：加载图片并在ImageView中显示
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param url 图片url
	 * @param @param imageview 加载图片的ImageView
	 * @param @param options 加载图片的参数
	 * @return void
	 * @date 2014-1-22 下午8:22:02
	 *
	 */
	public void displayImage(String url,ImageView imageview,DisplayImageOptions options){
		ImageLoader.getInstance().displayImage(url, imageview, options);
	}
	
	/**
	 * 
	 * 功能描述：加载图片并在ImageView中显示
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param url 图片url
	 * @param @param imageview 加载图片的ImageView
	 * @param @param listener 图片加载结果的Listener
	 * @return void
	 * @date 2014-1-22 下午8:22:37
	 *
	 */
	public void displayImage(String url,ImageView imageview,ImageLoadingListener listener){
		ImageLoader.getInstance().displayImage(url, imageview, listener);
	}
	
	/**
	 * 
	 * 功能描述：加载图片并在ImageView中显示
	 *
	 * @author 徐萌阳(xumengyang)
	 * @param @param url 图片url
	 * @param @param imageview 显示图片的ImageView
	 * @param @param options 图片加载的参数
	 * @param @param listener 图片加载结果回调监听器
	 * @return void
	 * @date 2014-1-22 下午8:23:10
	 *
	 */
	public void displayImage(String url,ImageView imageview,DisplayImageOptions options,ImageLoadingListener listener){
		ImageLoader.getInstance().displayImage(url, imageview, options, listener);
	}
	
	/**
	 * 
	 * 功能描述：清除内存中的图片缓存
	 *
	 * @author 严峥(yanzheng)
	 * @param 
	 * @return void
	 * @date 2014-5-28 下午4:20:02
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public void clearMemoryCache(){
		ImageLoader.getInstance().clearMemoryCache();
	}
	
	/**
	 * 
	 * 功能描述：清除sd卡中的图片缓存
	 *
	 * @author 严峥(yanzheng)
	 * @param 
	 * @return void
	 * @date 2014-5-28 下午4:20:47
	 *
	 * 修改历史 ：(修改人，修改时间，修改原因/内容)
	 */
	public void clearDiscCache(){
		ImageLoader.getInstance().clearDiscCache();
	}
}
