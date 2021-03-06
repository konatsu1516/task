package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.data.repository.TasksRepository;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class MainController {
	@Autowired // @Autowiredアノテーションを付けると、Spring Bootが自動でインスタンスをInjectします。
	private TasksRepository taskRepository;

	@GetMapping("/main")
	public String main(Model model, String userName, @AuthenticationPrincipal AccountUserDetails user) {

		// カレンダー表示処理ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
		// 2次元のリストインスタンス作成
		List<List<LocalDate>> matrix = new ArrayList<>();
		// 1週間分のLocalDateを格納するListを用意する
		List<LocalDate> week = new ArrayList<>();
		// 本日のLocalDateインスタンスを取得
		LocalDate d = LocalDate.now();//
		// 今月の1日を表現するLocalDateインスタンスを求める(2022/03/01)
		d = LocalDate.of(d.getYear(), d.getMonthValue(), 1);
		// 今月の1日の曜日を求める(火曜つまり2)
		DayOfWeek w = d.getDayOfWeek();
		// 前月分の最後の日曜日（つまり今月の1日がはじまる日曜日）のLocalDateを求める
		LocalDate start = d.minusDays(w.getValue());
		// 月末を求める
		int lastDay = d.lengthOfMonth();

		// d(2022-03-01)とstart(2022-02-27)の日数の差を求める。結果はP-2D
		Period period = Period.between(d, start);
		// periodをint型に変換する。結果は2
		int days = Math.abs(period.getDays());

		// int iにstartとdの差、つまり-1となるように入れる
		for (int i = 1 - days; i < lastDay; i += 7) {
			for (int j = 0; j < 7; j++) {
				week.add(start);
				start = start.plusDays(1);
			}
			matrix.add(week);
			week = new ArrayList<>();
		}
		model.addAttribute("matrix", matrix);
		model.addAttribute("week", week);

		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		int thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		model.addAttribute("thisYear", thisYear);
		model.addAttribute("thisMonth", thisMonth);

		// タスク表示処理ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
		// タスク表示開始日のLocalDateを求めるーーーーーーーーーーーーーーーーー
		// (前月分の最後の日曜日（つまり今月の1日がはじまる日曜日）)
		LocalDate taskDisplayStart = d.minusDays(w.getValue());

		// タスク表示終了日のLocalDateを求めるーーーーーーーーーーーーーーーー
		// 本日のLocalDateインスタンスを取得
		LocalDate n = LocalDate.now();
		// 来月の1日を表現するLocalDateインスタンスを求める(2022/04/1)
		n = LocalDate.of(n.getYear(), n.getMonthValue() + 1, 1);
		// 来月の1日の曜日を求める(金曜つまり5)
		DayOfWeek e = n.getDayOfWeek();
		// 今月のカレンダーに食い込んだ来月分の日数を取得()
		int plusDay = 7 - e.getValue();
		// 本日のLocalDateインスタンスを取得
		LocalDate t = LocalDate.now();
		// 今月の末日を表現するLocalDateインスタンスを求める(2022/03/31)
		t = LocalDate.of(t.getYear(), t.getMonthValue(), lastDay);
		// タスク表示終了日のLocalDateを求める(2022/04/02)
		LocalDate taskDisplayEnd = t.plusDays(plusDay);

		// MultiMapインスタンスの作成
		MultiValueMap<LocalDate, Tasks> tasks = new LinkedMultiValueMap<LocalDate, Tasks>();
		// タスク表示の絞り込み
		if (user.getName().equals("admin-name")) {
			List<Tasks> allTasks = taskRepository.findByAdminDateBetween(taskDisplayStart, taskDisplayEnd);
			for (int i = 0; i < allTasks.size(); i++) {
				tasks.add(allTasks.get(i).getDate(), allTasks.get(i));
			}
			model.addAttribute("tasks", tasks);
			return "main";
		} else {
			List<Tasks> allTasks = taskRepository.findByDateBetween(taskDisplayStart, taskDisplayEnd, user.getName());
			for (int i = 0; i < allTasks.size(); i++) {
				tasks.add(allTasks.get(i).getDate(), allTasks.get(i));
			}
			model.addAttribute("tasks", tasks);
			return "main";
		}

		// ユーザーごとのデータ絞り込み処理
		// データベースからアカウント情報を取得する
		// Users user = usersRepository.findById(userName).get();
		// allTasksリストをストリームにして新たなnotAdminTasksリストにする
		// List<Tasks> notAdminTasks = allTasks.stream()
		// タスクのユーザーネームとアカウントのユーザーネームが同じものをフィルターかける
		// .filter(t -> t.getName() .equals (user.getName()))
		// フィルターをかけたものを新たなリストとして生成
		// .collect(Collectors.toList());

		// for(int i=0;i<notAdminTasks.size();i++) {
		// tasks.add(notAdminTasks.get(i).getDate(), notAdminTasks.get(i));
		// }
	}

}
