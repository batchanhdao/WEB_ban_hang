package QLKS.Controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	@GetMapping("/") // tiếp nhận yêu cầu từ trang /
	public String home(Model model, HttpSession session) {
		// dùng model để lưu data từ session
		if (session.getAttribute("currentAccount") != null) {
			model.addAttribute("account", session.getAttribute("currentAccount"));
		}
		// tìm đến trang giao diện homepage.html
		return "homepage";
	}

	@GetMapping("/login") // tiếp nhận yêu cầu từ trang /login
	public String login(HttpSession session) {
		if (session.getAttribute("currentAccount") != null) {
			return "logout";
		}
		return "login";
	}

	@GetMapping("/logout") // tiếp nhận yêu cầu từ trang /logout
	public String logout(HttpSession session) {
		return "logout";
	}

	
}
