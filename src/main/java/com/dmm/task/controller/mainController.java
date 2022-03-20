package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
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
	  private TasksRepository taskRepository;
	
	@GetMapping("/main")
	 public String main(Model model) {
		
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
		
		//d(2022-03-01)とstart(2022-02-27)の日数の差を求める。結果はP-2D
		Period period = Period.between(d, start);
		//periodをint型に変換する。結果は2
		int days = Math.abs(period.getDays());
		
			//int iにstartとdの差、つまり-1となるように入れる
			for(int i=1-days;i<lastDay;i+=7) {
				for(int j=0;j<7;j++) {
					week.add(start);
					start=start.plusDays(1);
				}
				matrix.add(week);
				week = new ArrayList<>();
			}
		model.addAttribute("matrix", matrix);
		model.addAttribute("week", week);
		
		
		Tasks a=new Tasks();
		
		tasks.add(start, a);		
		model.addAttribute("tasks", tasks);
		
        return "main";
        
    }
	

}
