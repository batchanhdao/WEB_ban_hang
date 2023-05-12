package QLKS.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import QLKS.Entity.Account;
import QLKS.Entity.MyUserDetails;
import QLKS.Repository.AccountRepository;

@Service 
// UserDetailsService được sử dụng bởi Spring Security 
// để load thông tin người dùng khi họ đăng nhập.
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired 
	private AccountRepository accountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		// tìm kiếm tài khoản của người dùng dựa trên username
		Optional<Account> account = accountRepo.findByUsername(username);
		account.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
		
		return account.map(MyUserDetails::new).get();
	}

}

