package com.kamikikai.timemeister;

import java.io.Serializable;

public class Alarm implements Serializable {
	private static final long serialVersionUID = 1082798934376118140L;

	private String name;
	private int time;
	private int music;
	private int icon;
	private int intentCode;

	private boolean flag;
	private int timeNow;

	public Alarm(String name, int time, int music, int icon, int intentCode) {
		this.name = name;
		this.time = time;
		this.music = music;
		this.icon = icon;
		this.intentCode = intentCode;
	}
	
	public int getHour(int time){
		return (int)(time/3600);
	}
	
	public int getMinute(int time){
		return (int)(time/60)%60;
	}
	
	public int getSecond(int time){
		return time%60;
	}

	public int getMusic() {
		return music;
	}

	public void setMusic(int music) {
		this.music = music;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIntentCode() {
		return intentCode;
	}

	public void setIntentCode(int intentCode) {
		this.intentCode = intentCode;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getTimeNow() {
		return timeNow;
	}

	public void setTimeNow(int timeSNow) {
		this.timeNow = timeSNow;
	}

	public int getIconId(int res) {
		if(Res.drawable.indexOf(res) != -1)
			return Res.drawable.indexOf(res);
		if(Res.drawableButton.indexOf(res) != -1)
			return Res.drawableButton.indexOf(res);
		return -1;
	}

}
