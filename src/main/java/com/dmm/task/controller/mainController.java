package com.dmm.task.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;

@Controller
public class mainController {
	@Autowired //@Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	  private TasksRepository task;
	
	@GetMapping("/main")
	 public String main(Model model) {
		
//		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
//		model.addAttribute("thisYear", thisYear);
//		int thisMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
//		model.addAttribute("thisMonth", thisMonth);
//		
//		new MyCalendar(thisYear, thisMonth);
		
		
		// 1. MultiMapインスタンスの作成
		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();
		// 2. 2次元のリストインスタンス作成
		List<List<LocalDate>> matrix = new ArrayList<>();
		// 1週間分のLocalDateを格納するListを用意する
		List<LocalDate> week = new ArrayList<>();
		//カレンダーインスタンス
		Calendar calendar = Calendar.getInstance();
		//曜日を表すDayOfWeekを取得
		int myDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		//上で取得したLocalDateに曜日の値（DayOfWeek#getValue)をマイナスして前月分のLocalDateを求める
		week.add(myDayOfWeek.getValue());
		
        return "main";
    }
	

}
