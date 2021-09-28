package project.hibernate.entity;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String account_password;
	@Column(nullable = false, unique = true)
	private String account_username;
	@Column ( unique = true)
	private String account_email;
	@OneToOne
	private Cart cart; 
//	Credential credentials;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
	private List<Credential> credentials = new ArrayList<>();
	@OneToMany(fetch = FetchType.EAGER)
	private List<PlacedOrder> placedOrders = new ArrayList<>();
	public Account(String account_password, String account_username) {
		super();
		this.account_password = account_password;
		this.account_username = account_username;
	}
	public Account(String account_password, String account_username, String account_email) {
		super();
		this.account_password = account_password;
		this.account_username = account_username;
		this.account_email = account_email;
	}
	public void addCredentialToAccount(Credential credential) {
		this.credentials.add(credential);
	}
	public void addOrderToAccount(PlacedOrder order) {
		this.placedOrders.add(order);
	}
	
	
}
