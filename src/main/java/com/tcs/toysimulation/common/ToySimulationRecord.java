package com.tcs.toysimulation.common;

public class ToySimulationRecord {
	
	private String location;
	private String position;
	private String localtime;
	private String conditions;
	private double temperature;
	private double pressure;
	private int humidity;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public String getLocaltime() {
		return localtime;
	}
	public void setLocaltime(String isoDate) {
		this.localtime = isoDate;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
}

