package de.brightstorm.config;

public class group {
	private String name;
	private short order;
	private int time;
	private double amount;
	private float interest;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getOrder() {
		return order;
	}
	public void setOrder(short order) {
		this.order = order;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public float getInterest() {
		return interest;
	}
	public void setInterest(float interest) {
		this.interest = interest;
	}
}
