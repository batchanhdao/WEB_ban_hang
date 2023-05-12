package QLKS.Controller;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	@GetMapping("/") 
	public String home(Model model, HttpSession session) {
		if (session.getAttribute("currentAccount") != null) {
			model.addAttribute("account", session.getAttribute("currentAccount"));
		}
		return "homepage";
	}

	@GetMapping("/login") 
	public String login(HttpSession session) {
		if (session.getAttribute("currentAccount") != null) {
			return "logout";
		}
		return "login";
	}

	@GetMapping("/logout") 
	public String logout() {
		return "logout";
	}

	
}
