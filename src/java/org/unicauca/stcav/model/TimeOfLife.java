package org.unicauca.stcav.model;

public class TimeOfLife {
	private Long homeTime;
	private Long endTime;
	public TimeOfLife() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long get_home_time() {
		return homeTime;
	}
	public void set_home_time() {
		this.homeTime = System.currentTimeMillis();
	}
	public Long get_end_time() {
		return endTime;
	}
	public void set_end_time() {
		this.endTime = System.currentTimeMillis();
	}
	public Long get_tot_(){
		return endTime-homeTime;
	}
	
}
