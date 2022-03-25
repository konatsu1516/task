package com.dmm.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.form.TasksForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class CreateController {

	@Autowired // @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	private TasksRepository taskRepository;

	@GetMapping("/main/create/{date}")
	public String create(
			@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) java.time.LocalDate date) {
		return "create";
	}

	@PostMapping("/main/create")
	public String mainCreate(TasksForm tasksForm, @AuthenticationPrincipal AccountUserDetails user) {
		Tasks myTask = new Tasks();
		myTask.setName(user.getName());
		myTask.setTitle(tasksForm.getTitle());
		myTask.setDate(tasksForm.getDate());
		myTask.setText(tasksForm.getText());
		myTask.setDone(false);

		taskRepository.save(myTask);

		return "redirect:/main";
	}
}
