package project.hibernate.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class GPU {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true)
	private String description;
	@ManyToMany(mappedBy = "gpus")
	private List<Cart> cart;
	@ManyToMany(mappedBy = "gpus")
	private List<PlacedOrder> placedOrders = new ArrayList<>();
	public  GPU(String description) {
		super();
		this.description = description;
	}
	
}
