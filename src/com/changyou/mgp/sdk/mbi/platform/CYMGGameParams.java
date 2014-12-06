package com.changyou.mgp.sdk.mbi.platform;

public class CYMGGameParams {

	private static CYMGGameParams mInstance;
	private CYMGGameParamsHandler mHandler;
	
	
	private CYMGGameParams(){
		
	}
	
	public static CYMGGameParams getInstance(){
		if(mInstance == null){
			synchronized (CYMGGameParams.class) {
				if(mInstance == null){
					mInstance = new CYMGGameParams();
				}
			}
		}
		return mInstance;
	}
	
	public void setHandler(CYMGGameParamsHandler handler){
		this.mHandler = handler;
	}
	
	public CYMGGameParamsHandler getHandler(){
		return this.mHandler;
	}
}
