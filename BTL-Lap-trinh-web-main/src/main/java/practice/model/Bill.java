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

import lombok.Data;

@Data
@Entity
@Table(name = "Bill")
public class Bill {
	//thuộc tính id là khóa chính và được tự động sinh ra và tự động tăng bởi hqtcsdl 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(targetEntity = Booking.class, cascade = CascadeType.MERGE)
	private Booking booking;//thuộc tính booking là khóa ngoại, 1 booking có thể có nhiều bill
	
	@ManyToOne(targetEntity = Account.class, cascade = CascadeType.MERGE)
	private Account receptionist;//thuộc tính account là khóa ngoại, 1 account có thể có nhiều bill
	
	private Date paymentDate;
	private Long paymentAmount;
	private String paymentType;
	
	//thời gian tạo bil sẽ được lấy bằng thời gian thực, và được thực hiện trước khi đưa vào csdl
	@PrePersist
	private void paidAt() {
		this.paymentDate = new Date();
	}
}
