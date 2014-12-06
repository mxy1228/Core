package com.changyou.mgp.sdk.mbi.utils;

import android.content.Context;

import com.changyou.mgp.sdk.mbi.log.CYLog;

public class ResourceUtil {

	private static CYLog log = CYLog.getInstance();
	private static ResourceUtil mInstance;
	private String mPackageName;//接入游戏的包名
	
	private ResourceUtil(Context context){
		this.mPackageName = context.getPackageName();
	}
	
	public static ResourceUtil getInstance(Context context){
		if(mInstance == null){
			synchronized(ResourceUtil.class){
				if(mInstance == null){
					mInstance = new ResourceUtil(context);
				}
			}
		}
		return mInstance;
	}
	
	private int getResourceIDByName(String packageName,String resType,String resName){
		Class clsR = null;
		int id = 0;
		try {
			clsR = Class.forName(packageName + ".R");
			Class[] classes = clsR.getClasses();
			Class desClass = null;
			for(int i=0;i<classes.length;i++){
				String[] temp = classes[i].getName().split("\\$"); 
				if(temp.length >= 2){
					if(temp[1].equals(resType)){
						desClass = classes[i];
						break;
					}
				}
			}
			if(desClass != null){
				id = desClass.getField(resName).getInt(desClass);
			}
		} catch (Exception e) {
			log.e(e);
		}
		return id;
	}
	
	/**
	 * 获取int数组格式的资源ID，例如styleable
	 * @param packageName
	 * @param resType
	 * @param resName
	 * @return
	 */
	private int[] getResourceIDsByName(String packageName,String resType,String resName){
		Class clsR = null;
		int[] ids = null;
		try {
			clsR = Class.forName(packageName + ".R");
			Class[] classes = clsR.getClasses();
			Class desClass = null;
			for(int i=0;i<classes.length;i++){
				String[] temp = classes[i].getName().split("\\$"); 
				if(temp.length >= 2){
					if(temp[1].equals(resType)){
						desClass = classes[i];
						break;
					}
				}
			}
			if(desClass != null){
				ids = (int[])desClass.getField(resName).get(resName);
			}
		} catch (Exception e) {
			log.e(e);
		}
		return ids;
	}
	
	
	/**
	 * 获取layout资源ID
	 * @param resName
	 * @return
	 */
	public int getLayoutId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "layout", resName);
	}
	
	/**
	 * 获取drawable资源ID
	 * @param resName
	 * @return
	 */
	public int getDrawableId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "drawable", resName);
	}
	
	/**
	 * 获取String资源ID
	 * @param resName
	 * @return
	 */
	public int getStringId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "string", resName);
	}
	
	/**
	 * 获取控件资源ID
	 * @param resName
	 * @return
	 */
	public int getId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "id", resName);
	}
	
	/**
	 * 获取Style资源ID
	 * @param resName
	 * @return
	 */
	public int getStyleId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "style", resName);
	}
	
	/**
	 * 获取Array资源ID
	 * @param resName
	 * @return
	 */
	public int getArrayId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "array", resName);
	}
	
	/**
	 * 获取Color资源ID
	 * @param resName
	 * @return
	 */
	public int getColorId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "color", resName);
	}
	
	/**
	 * 获取Dimen资源ID
	 * @param resName
	 * @return
	 */
	public int getDimenId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "dimen", resName);
	}
	
	/**
	 * 获取Styleable资源ID
	 * @param resName
	 * @return
	 */
	public int getStyleableId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "styleable", resName);
	}
	
	/**
	 * 获取Styleable资源ID
	 * @param resName
	 * @return
	 */
	public int[] getStyleableIds(String resName){
		return mInstance.getResourceIDsByName(mPackageName, "styleable", resName);
	}
	
	/**
	 * 获取anim资源ID
	 * @param resName
	 * @return
	 */
	public int getAnimId(String resName){
		return mInstance.getResourceIDByName(mPackageName, "anim", resName);
	}
}
