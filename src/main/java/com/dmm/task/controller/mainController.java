package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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
		// 本日のLocalDateインスタンスを取得
		LocalDate d = LocalDate.now();//(2022/03/19)
		// 今月の1日を表現するLocalDateインスタンスを求める
		d = LocalDate.of(d.getYear(), d.getMonthValue(), 1);//(2022/03/01)
		// 今月の1日の曜日を求める
		DayOfWeek w = d.getDayOfWeek();//(火曜つまり2)
		// 前月分の最後の日曜日（つまり今月の1日がはじまる日曜日）のLocalDateを求める
		LocalDate start = d.minusDays(w.getValue());
		//月末を求める
		int lastDay=d.lengthOfMonth();
		
		for(int j=0;j<lastDay;j++) {
			for(int i=0;i<7;i++) {
				week.add(start);
				start=start.plusDays(1);
				
			}
			matrix.add(week);
			System.out.println(matrix);
		}
		
        return "main";
    }
	

}
