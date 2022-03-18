package com.dmm.task.form;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TasksForm {
	private String title;
	private LocalDateTime date;
	private String text;
	private boolean done;
}
