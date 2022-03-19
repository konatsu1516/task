package com.dmm.task.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.MyCalendar;
import com.dmm.task.data.repository.TasksRepository;

@Controller
public class mainController {
	@Autowired //@Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	  private TasksRepository task;
	
	@GetMapping("/main")
	 public String main(Model model) {
		
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		model.addAttribute("thisYear", thisYear);
		int thisMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
		model.addAttribute("thisMonth", thisMonth);
		
		new MyCalendar(thisYear, thisMonth);
		
        return "main";
    }
	

}
