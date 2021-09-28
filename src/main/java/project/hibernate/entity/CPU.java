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
@ToString(exclude = "cart")
public class CPU {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique = true)
	private String description;
	@Column(nullable = false)
	@ManyToMany(mappedBy = "cpus")
	private List<Cart> cart;
	@ManyToMany(mappedBy = "cpus")
	private List<PlacedOrder> placedOrders = new ArrayList<>();
	public CPU(String description) {
		super();
		this.description = description;
	}
	
}
