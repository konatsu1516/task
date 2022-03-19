package com.dmm.task.data;

import java.util.Calendar;

import lombok.Data;

@Data
public class MyCalendar {
	private int year;
	private int month;
	private int firstDay;
	private int lastDay;
	
	public MyCalendar(int year, int month) {
		this.year = year;
		this.month = month;
		calc();
	}
	
	private void calc() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month -1, 1);
		firstDay = calendar.get(Calendar.DAY_OF_WEEK);
		int day = 1 - (firstDay - 1) % 7;
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		lastDay = calendar.get(Calendar.DATE);
		
		while (day <= lastDay) {//dayが最終日より小さければ繰り返す
            for (int ii = 0; ii < 7 && day <= lastDay; ii++) {//iiが7以下かつ、dayが最終日より小さければ繰り返す
                day++;
            }
        }
	}
}
