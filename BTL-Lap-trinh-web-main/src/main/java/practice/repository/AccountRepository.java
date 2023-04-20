package practice.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import practice.model.Account;

//AccountRepository được sử dụng trong việc tương tác với cơ sở dữ liệu để lấy và lưu thông tin tài khoản người dùng.
//kế thừa crudrepository thao tác CRUD (Create, Read, Update, Delete) với đối tượng Account.

//CrudRepository<Account, Long> AccountRepository sẽ hoạt động với các đối tượng Account và sử dụng kiểu dữ liệu Long 
//cho thuộc tính ID của Account.
public interface AccountRepository extends CrudRepository<Account, Long> {
	
	//phương thức findByUsername(String username) để tìm kiếm một đối tượng Account dựa trên username
	//Optional<Account> để tránh trả về null khi không tìm thấy đối tượng Account tương ứng với username.
	Optional<Account> findByUsername(String username);

}
