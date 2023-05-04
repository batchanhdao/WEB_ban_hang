package QLKS.Entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;


@Data // tạo get set, lớp rỗng
@Entity //sử dụng @Entity, để lưu trữ và truy xuất dữ liệu từ csdl
@Table(name = "Account")//chỉ định tên của bảng dữ liệu "Account" trong csdl
public class Account {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min = 5, message = "Tên đăng nhập ít nhất 5 ký tự")
	private String username;
	
	@Size(min = 5, message = "Tên đăng nhập ít nhất 5 ký tự")
	private String password;
	
	// tài khoản còn hoạt động ko
	private boolean active;
	// vai trò của user
	private String roles;
	
	private Date createdAt;
	
	// quan hệ account với user là n-1
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE)
	private User user;
	
	@PrePersist
	private void createdAt() {
		this.createdAt = new Date();
	}
	
	public void setup(Account account2) {
		this.id = account2.id;
		this.username = account2.username;
		this.password = account2.password;
		this.active = account2.active;
		this.roles = account2.roles;
		this.createdAt = account2.getCreatedAt();
		this.user = account2.getUser();
	}

}
