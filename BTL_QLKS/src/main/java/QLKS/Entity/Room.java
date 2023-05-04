package QLKS.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Room")
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String type;
	private Long price;
	private String description;
	
//	private Date addedAt;
	
//	@ManyToOne(targetEntity = Manager.class, cascade = CascadeType.MERGE)
//	private Manager addedBy;
	
}
