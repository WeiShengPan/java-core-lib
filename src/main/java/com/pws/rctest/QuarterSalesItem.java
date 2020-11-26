package com.pws.rctest;

public class QuarterSalesItem {

	private int quarter;

	private double total;

	public QuarterSalesItem() {
	}

	public QuarterSalesItem(int quarter, double total) {
		this.quarter = quarter;
		this.total = total;
	}

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
