package com.changyou.mgp.sdk.mbi.entity;

public class FlowWinExpandAnimEvent {

	public static final int START = 1;
	public static final int END = 2;
	
	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
