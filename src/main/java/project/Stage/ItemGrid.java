package project.Stage;

import java.util.List;

import org.hibernate.Session;

import javafx.scene.layout.GridPane;
import project.FilePath.FilePath;
import project.hibernate.entity.Account;
import project.hibernate.entity.CPU;
import project.hibernate.entity.Cart;
import project.hibernate.entity.GPU;
import project.hibernate.entity.HDD;
import project.hibernate.entity.PowerSupply;
import project.hibernate.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class ItemGrid extends Item {
Session session;



public GridPane getCPUItemsGrid(Account account, Cart cart ) {
	GridPane gridPane = new GridPane();
	 session = HibernateUtil.getSessionFactory().openSession();
	int counter = 1;
	int column = 0;
	int row = 0;
	List<CPU> cpus = session.createQuery("FROM CPU").list();
	session.close();
	for(CPU cpu: cpus) {
	String desc = cpu.getDescription();
	gridPane.add(addItem(FilePath.path + "cpuitem"+ counter+".jpg",desc, account, cart), column, row);
	
	if(column == 2) {
		column = 0;
		row++;
	}
	else
		column++;
	counter++;
	}
	return gridPane;
}

public GridPane getGPUItemsGrid(Account account, Cart cart ) {
	GridPane gridPane = new GridPane();
	 session = HibernateUtil.getSessionFactory().openSession();
	int counter = 1;
	int column = 0;
	int row = 0;
	List<GPU> gpus = session.createQuery("FROM GPU").list();
	session.close();
	for(GPU gpu: gpus) {
	String desc = gpu.getDescription();
	gridPane.add(addItem(FilePath.path + "gpuitem"+ counter+".jpg",desc, account, cart), column, row);
	System.out.println(FilePath.path + "gpuitem");
	System.out.println(FilePath.path + "gpuitem");
	System.out.println(FilePath.path + "gpuitem");
	System.out.println(FilePath.path + "gpuitem");
	if(column == 2) {
		column = 0;
		row++;
	}
	else
		column++;
	counter++;
	}
	return gridPane;
}
public GridPane getHDDItemsGrid(Account account, Cart cart ) {
	GridPane gridPane = new GridPane();
	 session = HibernateUtil.getSessionFactory().openSession();
	int counter = 1;
	int column = 0;
	int row = 0;
	List<HDD> hdds = session.createQuery("FROM HDD").list();
	session.close();
	for(HDD hdd: hdds) {
	String desc = hdd.getDescription();
	gridPane.add(addItem(FilePath.path + "hdditem"+ counter+".jpg",desc, account, cart), column, row);
	
	if(column == 2) {
		column = 0;
		row++;
	}
	else
		column++;
	counter++;
	}
	return gridPane;
}
public GridPane getPSUItemsGrid(Account account, Cart cart ) {
	GridPane gridPane = new GridPane();
	 session = HibernateUtil.getSessionFactory().openSession();
	int counter = 1;
	int column = 0;
	int row = 0;
	List<PowerSupply> psus = session.createQuery("FROM PowerSupply").list();
	session.close();
	for(PowerSupply ps: psus) {
	String desc = ps.getDescription();
	gridPane.add(addItem(FilePath.path + "psuitem"+ counter+".jpg",desc, account, cart), column, row);
	
	if(column == 2) {
		column = 0;
		row++;
	}
	else
		column++;
	counter++;
	}
	return gridPane;
}
}
