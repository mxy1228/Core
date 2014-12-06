package com.changyou.mgp.sdk.mbi.db;

import java.util.List;

import android.content.Context;

public class CYMGDBMaster {

	public static final int STATE_LOGIN_SUCCESS = 1;
	public static final int STATE_LOGOUT = 0;
	
	private CYMGDBHelper mHelper;
	private CYMGAccDao mDao;
	
	public CYMGDBMaster(Context context){
		this.mHelper = new CYMGDBHelper(context);
		this.mDao = new CYMGAccDao(mHelper);
	}
	
	/**
	 * 增加新的账号记录
	 * @param bean
	 * @return
	 */
	public boolean save(CYMGAccBean bean){
		return this.mDao.insert(bean);
	}
	
	/**
	 * 获取最新登录的5个账号列表
	 * @return
	 */
	public List<CYMGAccBean> getAccList(int count){
		return this.mDao.selectList(count);
	}
	
	/**
	 * 删除账号
	 * @param acc
	 */
	public void delete(CYMGAccBean bean){
		this.mDao.delete(bean);
	}
	
	/**
	 * 账号成功登录
	 * @param acc
	 */
	public void login(CYMGAccBean bean){
		if(this.mDao.contain(bean.getmAcc())){
			this.mDao.updateState(STATE_LOGIN_SUCCESS, bean);
		}else{
			this.mDao.insert(bean);
		}
	}
	
	/**
	 * 注销账号
	 * @param acc
	 */
	public void logout(CYMGAccBean bean){
		this.mDao.updateState(STATE_LOGOUT, bean);
	}
}
