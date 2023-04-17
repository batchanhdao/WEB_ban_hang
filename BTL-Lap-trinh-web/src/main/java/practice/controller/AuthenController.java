package practice.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import practice.model.Account;
import practice.repository.AccountRepository;
import practice.repository.UserRepository;

@Slf4j // tự động tạo ra một logger giúp đọc và kiểm tra lại những sự kiện đã xảy ra
@Controller // lấy thông tin tài khoản người dùng
@RequestMapping("/authen") // xử lý yêu cầu HTTP trên đường dẫn "/authen" mức class
@SessionAttributes("currentAccount") // tạo thuộc tính currentAccount trong session
public class AuthenController {
	
	@Autowired // tự động tiêm các đối tượng để sử dụng các phương thức và thuộc tính
	private AccountRepository accountRepo;
	
	@ModelAttribute("currentAccount") // tạo 1 lớp Account rỗng rồi cho vào model 
	public Account account() {
		return new Account();
	}
	
	@GetMapping
	public String getUser(
			@ModelAttribute("currentAccount") Account currentAccount ) {
		
		String currentUsername = SecurityContextHolder
				.getContext().getAuthentication().getName();
		// tìm tài khoản có tên currentUsername trong csdl 
		Account account = accountRepo.findByUsername(currentUsername).orElse(null);
		if(account!=null) { // nếu có thì thêm vào model với tên là currentAccount
			currentAccount.setup(account);
		}
		return "redirect:/"; // chuyển đến hàm getMapping("/") 
	}
}
