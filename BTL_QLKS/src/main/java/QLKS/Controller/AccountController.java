package QLKS.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import QLKS.Entity.Account;
import QLKS.Entity.Booking;
import QLKS.Entity.Client;
import QLKS.Entity.User;
import QLKS.Repository.AccountRepository;
import QLKS.Repository.BookingRepository;
import QLKS.Repository.ClientRepository;
import QLKS.Repository.UserRepository;


@Controller
@RequestMapping("/account") 
@SessionAttributes({ "addedUser", "addedClient" })
public class AccountController {
	boolean changeInfo = false;

	@Autowired 
	private AccountRepository accountRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private BookingRepository bookingRepo;
	
	@ModelAttribute("addedUser") 
	public User addedUser() {
		return new User();
	}
	
	@ModelAttribute("addedClient")
	public Client addedClient() {
		return new Client();
	}
	
	@ModelAttribute("addedAccount")
	public Account addedAccount() {
		return new Account();
	}

//	lấy thông tin tài khoản người dùng
	@GetMapping // xử lý yêu cầu HTTP trên đường dẫn "/account"
	public String account(Model model, HttpSession session) {
		if (session.getAttribute("currentAccount") != null) { // nếu tồn tại
			Account account = (Account) session.getAttribute("currentAccount");
			model.addAttribute("account", account); 
		}
		if(changeInfo) {
			model.addAttribute("message", "Thông tin tài khoản đã được thay đổi");
			changeInfo=false;
		}
	    
		return "account";
	}

	@GetMapping("/submitInfo") // xử lý yêu cầu HTTP trên đường dẫn "/account/submitInfo"
	public String submitInfo(Model model) {
		model.addAttribute("user", new User()); 
		return "submitInfo"; 
	}
	
	// sử dụng để nhận thông tin của trang đăng ký tài khoản
	@PostMapping("/submitInfo") // nhận data từ đường dẫn "account/submitInfo" 
	public String submitClientInfo(Model model, 
			@Valid User user, Errors errors, @SessionAttribute("addedUser") User addedUser) {
		if(errors.hasErrors()) {
			return "submitInfo";
		}
		addedUser.setFullname(user.getFullname());
		addedUser.setIdCard(user.getIdCard());
		addedUser.setPhoneNumber(user.getPhoneNumber());
		addedUser.setAddress(user.getAddress());
		addedUser.setEmail(user.getEmail());
		addedUser.setNote(user.getNote());
		addedUser.setRole("Khách hàng");
		
		model.addAttribute("client", new Client());
		return "submitClient"; 
	}
	
	//sử dụng để nhận thông tin thẻ ngân hàng
	@PostMapping("/submitClient") // nhận data từ đường dẫn "account/submitClient"
	public String createAccount(Model model, 
			@Valid Client client, Errors errors, 
			@SessionAttribute("addedClient") Client addedClient) {
		if(errors.hasErrors()) {
			return "submitClient";
		}
		
		addedClient.setBankAccount(client.getBankAccount());
		addedClient.setNote(client.getNote());
		model.addAttribute("account", new Account());
		return "createAccount"; 
	}

	//sử dụng để nhận thông tin đăng nhập
	@PostMapping("/create")// nhận data từ đường dẫn "account/create"
	public String registerAccount(@Valid Account account, Errors errors, HttpSession session) {
		if(errors.hasErrors()) { 
			return "createAccount";
		}
		User addedUser = (User) session.getAttribute("addedUser");
		Client addedClient = (Client) session.getAttribute("addedClient");

		// lưu data vào csdl gồm user, account và client
		userRepo.save(addedUser);
		
		account.setActive(true);
		account.setRoles("ROLE_USER");		
		account.setUser(addedUser);
		accountRepo.save(account);
		
		addedClient.setUser(addedUser);
		clientRepo.save(addedClient);
		return "redirect:/"; 
	}

	@PostMapping("/change") // tiếp nhận data thay đổi tài khoản trong SessionAttribute("currentAccount")
	public String confirmChange(Model model, Account account
			, @SessionAttribute("currentAccount") Account currentAccount) {

		currentAccount.setUsername(account.getUsername());
		currentAccount.getUser().setFullname(account.getUser().getFullname());
		currentAccount.getUser().setEmail(account.getUser().getEmail());
		currentAccount.getUser().setPhoneNumber(account.getUser().getPhoneNumber());
		currentAccount.getUser().setAddress(account.getUser().getAddress());
		    
		// Lưu updatedAccount vào cơ sở dữ liệu
		accountRepo.save(currentAccount);
		// Cập nhật đối tượng user tương ứng trong userRepo
		userRepo.save(currentAccount.getUser());
		
		changeInfo=true;
		
		return "redirect:/account";
	}
	
	// hiển thị danh sách tài khoản KH cho phép admin có quyền truy cập đến trang accountList
	@GetMapping("/list")
	public String viewAccounts(Model model) {
		List<Account> accounts = filterByRole("ROLE_ADMIN", (List<Account>) accountRepo.findAll());
		model.addAttribute("accounts", accounts);
		return "accountList";
	}

	// trả kết quả 1 danh sách các tài khoản không phải admin
	private List<Account> filterByRole(String role, List<Account> accounts) {
		List<Account> list = new ArrayList<>();
		for (Account account : accounts) {
			if (!account.getRoles().equals(role)) { // nếu ko phải tài khoản admin
				list.add(account);
			}
		}
		return list;
	}

	//  2 hàm dưới là tắt và bật tài khoản người dùng thông qua id
	@GetMapping("/disable/{id}")
	public String disableAccount(@PathVariable("id") Long id) {
		Account account = accountRepo.findById(id).orElse(null);
		if (account.isActive()) {
			account.setActive(false);
			accountRepo.save(account);
		}
		return "redirect:/account/list"; 
	}

	@GetMapping("/enable/{id}")
	public String enableAccount(@PathVariable("id") Long id) {
		Account account = accountRepo.findById(id).orElse(null);
		if (!account.isActive()) {
			account.setActive(true);
			accountRepo.save(account);
		}
		return "redirect:/account/list";
	}
	@GetMapping("/delete/{id}")
	public String deleteAccount(@PathVariable("id") Long id) {
		Account account = accountRepo.findById(id).orElse(null);
		accountRepo.deleteById(id);
		
		Client client = clientRepo.findByUser(account.getUser()).orElse(null);
		Booking booking = bookingRepo.findByClient(client).orElse(null);
		bookingRepo.deleteById(booking.getId());
		clientRepo.deleteById(client.getId());
		
		Long userId = account.getUser().getId();
		userRepo.deleteById(userId);
		return "redirect:/account/list";
	}
	@GetMapping("/role/{id}")
	public String roleAccount(@PathVariable("id") Long id) {
		Account account = accountRepo.findById(id).orElse(null);
		if (account.getRoles().equals("ROLE_MANAGER")==true) {
			account.setRoles("ROLE_USER");
			accountRepo.save(account);
		}
		else {
			account.setRoles("ROLE_MANAGER");
			accountRepo.save(account);
		}
		return "redirect:/account/list";
	}

}
