package com.dmm.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class editController {
	@GetMapping("/main/edit/{id}")
    public String edit(@PathVariable("id") Integer id) {
        return "edit";
    }

}
