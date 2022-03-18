package com.dmm.task.data.entity;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Calendar {
	// カレンダーの年
	private int year;
	// カレンダーの月
	private int month;
	// カレンダーの日付を保持する配列
	private String[][] data;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
}
