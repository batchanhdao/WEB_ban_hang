package QLKS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import QLKS.Entity.Account;
import QLKS.Repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

@Controller 
@RequestMapping("/authen") 
@SessionAttributes("currentAccount") 
public class AuthenController {

	@Autowired // tự động tiêm các đối tượng để sử dụng các phương thức và thuộc tính
	private AccountRepository accountRepo;

	@ModelAttribute("currentAccount") 
	public Account account() {
		return new Account();
	}

	@GetMapping
	public String getUser(@ModelAttribute("currentAccount") Account currentAccount) {
		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = accountRepo.findByUsername(currentUsername).orElse(null);
		if (account != null) { // nếu có thì thêm vào model với tên là currentAccount
			currentAccount.setup(account);
		}
		return "redirect:/"; 
	}

}
