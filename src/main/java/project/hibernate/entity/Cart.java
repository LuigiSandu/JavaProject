package project.hibernate.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne
	private Account account;
	@ManyToMany
	private List<CPU> cpus = new ArrayList<>();
	@ManyToMany
	private List<GPU> gpus = new ArrayList<>();
	@ManyToMany
	private List<HDD> hdds = new ArrayList<>();
	@ManyToMany
	private List<PowerSupply> powerSupply = new ArrayList<>();

	public Cart(Account account) {
		super();
		this.account = account;
	}

	public void addCPUToList(CPU cpu) {

		this.cpus.add(cpu);

	}

	public void addGPUToList(GPU gpu) {
		this.gpus.add(gpu);

	}
	public void addHDDToList(HDD hdd) {
		this.hdds.add(hdd);

	}
	public void addPowerSupplyToList(PowerSupply ps) {
		this.powerSupply.add(ps);

	}
	}


