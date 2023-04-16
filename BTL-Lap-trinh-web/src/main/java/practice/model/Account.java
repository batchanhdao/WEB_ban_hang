package practice.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
//sử dụng @Entity, để lưu trữ và truy xuất dữ liệu từ csdl
@Entity
@Table(name = "Account")//chỉ định tên của bảng dữ liệu "Account" trong csdl
public class Account {

	@Id //chỉ định trường trong đối tượng là khóa chính của bảng
//	Thẻ @GeneratedValue auto tạo giá trị cho trường khóa chính 
	@GeneratedValue(strategy = GenerationType.IDENTITY)// tự động tăng dần
	private Long id;
	
	@Size(min=5, message = "Tên đăng nhập ít nhất 5 kí tự")
	private String username;
	@Size(min=5, message = "Mật khẩu ít nhất 5 kí tự")
	private String password;
	private boolean active;
	private String roles;

	private Date createdAt;
	
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE)
	private User user;

	public void setup(Account account2) {
		this.id = account2.id;
		this.username = account2.username;
		this.password = account2.password;
		this.active = account2.active;
		this.roles = account2.roles;
		this.createdAt = account2.getCreatedAt();
		this.user = account2.getUser();
	}

	@PrePersist
	private void createdAt() {
		this.createdAt = new Date();
	}
}
