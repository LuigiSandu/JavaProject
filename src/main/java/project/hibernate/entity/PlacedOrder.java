package project.hibernate.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class PlacedOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Account account;
	@ManyToOne
	private Credential credential;
	@ManyToMany
	private List<CPU> cpus = new ArrayList<>();
	@ManyToMany
	private List<GPU> gpus = new ArrayList<>();
	@ManyToMany
	private List<HDD> hdds = new ArrayList<>();
	@ManyToMany
	private List<PowerSupply> powerSupply = new ArrayList<>();
	
	public void addCPUs(Set<CPU> cpuList) {
		this.cpus.addAll(cpuList);
	}
	public void addGPUs(List<GPU> gpuList) {
		this.gpus.addAll(gpuList);
	}
	
}
