package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.TasksForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class editController {
	
	@Autowired //@Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	  private TasksRepository taskRepository;
	
	@GetMapping("/main/edit/{id}")
    public String edit(@PathVariable("id") Integer id,Model model) {
		//idを使ってデータを取る
		//取ってきたタスクの内容を渡す
		Tasks myTask = taskRepository.findById(id).get();
		model.addAttribute("task", myTask);
        return "edit";
    }
	
	@PostMapping("/main/edit/{id}")
    public String mainCreate(Model model,@PathVariable("id") Integer id,TasksForm tasksForm, @AuthenticationPrincipal AccountUserDetails user) {
		Tasks myTask = taskRepository.findById(id).get();
		 myTask.setName(user.getName());
	     myTask.setTitle(tasksForm.getTitle());
	     myTask.setDate(tasksForm.getDate());
	     myTask.setText(tasksForm.getText());
	     myTask.setDone(tasksForm.isDone());
		taskRepository.save(myTask);

      return "redirect:/main";

    }

	 @PostMapping("/main/delete/{id}")
     public String deleteTask(@PathVariable("id") Integer id) {
 		// 指定したIDのユーザーを削除
		 taskRepository.deleteById(id);
 		return "redirect:/main";
 	}
}
