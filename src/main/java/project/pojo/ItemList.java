package project.pojo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import lombok.Getter;
import lombok.Setter;
import project.hibernate.entity.CPU;
import project.hibernate.entity.Cart;
import project.hibernate.entity.GPU;
import project.hibernate.entity.HDD;
import project.hibernate.entity.PowerSupply;
import project.hibernate.util.HibernateUtil;

@Setter
@Getter
public class ItemList {
	private Cart cart = new Cart();
	private List<String> listDescriptions = new ArrayList<>();
	Session session;
	private List<String> description = new ArrayList<>();

	public void getCPUFromCart(Cart cart, long id) {
		session = HibernateUtil.getSessionFactory().openSession();
		List<CPU> cpus = session.load(Cart.class, id).getCpus();
		for (CPU cpu : cpus) {
			listDescriptions.add(cpu.getDescription());
		}
		session.close();
	}

	public void getGPUFromCart(Cart cart, long id) {
		session = HibernateUtil.getSessionFactory().openSession();
		List<GPU> gpus = session.load(Cart.class, id).getGpus();
		for (GPU gpu : gpus) {
			listDescriptions.add(gpu.getDescription());
		}
		session.close();
	}

	public void getHDDFromCart(Cart cart, long id) {
		session = HibernateUtil.getSessionFactory().openSession();
		List<HDD> hdds = session.load(Cart.class, id).getHdds();
		for (HDD hdd : hdds) {
			listDescriptions.add(hdd.getDescription());
		}
		session.close();
	}

	public void getPSUFromCart(Cart cart, long id) {
		session = HibernateUtil.getSessionFactory().openSession();
		List<PowerSupply> psus = session.load(Cart.class, id).getPowerSupply();
		for (PowerSupply psu : psus) {
			listDescriptions.add(psu.getDescription());
		}
		session.close();
	}

	public void getAllDescriptions(long id) {
		getCPUFromCart(cart, id);
		getGPUFromCart(cart, id);
		getHDDFromCart(cart, id);
		getPSUFromCart(cart, id);
	}

}
