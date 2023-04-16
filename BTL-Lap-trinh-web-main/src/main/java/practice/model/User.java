package practice.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "User")
public class User {
	//thuộc tính id là khóa chính và được tự động sinh ra và tự động tăng bởi hqtcsdl 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//thuộc tính fullname idCard đều không để giá trị null
	@NotBlank(message = "Họ tên không để trống")
	private String fullname;
	@NotBlank(message = "Mã số CCCD không để trống")
	private String idCard;
	@NotBlank(message = "SĐT không để trống")
	private String phoneNumber;
	
	private String email;
	private String address;
	private String role;
	private String note;
	private Date registeredAt;
	
	//thời gian tạo người dùng sẽ được lấy bằng thời gian thực, và được thực hiện trước khi đưa vào csdl
	@PrePersist
	public void register() {
		this.registeredAt = new Date();
	}
}
