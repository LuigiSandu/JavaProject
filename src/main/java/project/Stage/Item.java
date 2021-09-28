package project.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.hibernate.Session;
import org.hibernate.Transaction;


import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.hibernate.entity.Account;
import project.hibernate.entity.CPU;
import project.hibernate.entity.GPU;
import project.hibernate.entity.HDD;
import project.hibernate.entity.PowerSupply;
import project.hibernate.entity.Cart;
import project.hibernate.util.HibernateUtil;

public class Item {
Session session;

//adds an image item to a Gridpane and returns it
public GridPane addItem(String URL, String description, Account account, Cart cart)  {
	GridPane gridPane = new GridPane();
	Text text = new Text(description);
	gridPane.setMinSize(100, 100);
	try {
	gridPane.add(addImagetoGroup(URL, account, cart), 0, 0);
	}catch(Exception e) {
		e.printStackTrace();
	}
	gridPane.add(text,0,1);
	return gridPane;
}
//Adds an image to a group and sets action
public Group addImagetoGroup(String imageURL, Account account, Cart cart) throws FileNotFoundException{
	
	InputStream stream = new FileInputStream(imageURL);
	
	Image image = new Image(stream);
	ImageView imageView = new ImageView();
	imageView.setImage(image);
	imageView.setX(50);
	imageView.setY(50);
	imageView.setFitWidth(400);
	imageView.setFitHeight(400);
	imageView.setPreserveRatio(false);
	Group root = new Group(imageView);
	imageView.setOnMouseClicked(value->{
		getTypeOfItem(imageURL, account, cart);
		
	});
	return root;
}
public void openCPUCartStage(long id, Account account, Cart cart) {
	session = HibernateUtil.getSessionFactory().openSession();
	CPU cpu = session.get(CPU.class, id);
	session.close();
	Stage stage = new Stage();
	GridPane gridPane = new GridPane();
	gridPane.setAlignment(Pos.CENTER);
	Button addToCart = new Button("Add to Cart.");
	
	addToCart.setOnAction(v ->{
		 session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(cart);
		cart.addCPUToList(cpu);
	
		transaction.commit();
		
		session.close();
	});
	gridPane.setMinSize(200, 200);
	gridPane.add(addToCart,0,0);
	Scene scene = new Scene(gridPane);
	stage.setTitle("Generic");

	stage.setScene(scene);

	stage.show();
	
}
public void openGPUCartStage(long id, Account account, Cart cart) {
	session = HibernateUtil.getSessionFactory().openSession();
	GPU gpu = session.get(GPU.class, id);
	session.close();
	Stage stage = new Stage();
	GridPane gridPane = new GridPane();
	gridPane.setAlignment(Pos.CENTER);
	Button addToCart = new Button("Add to Cart.");
	addToCart.setOnAction(v ->{
		session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(cart);
		cart.addGPUToList(gpu);
		
		transaction.commit();
		session.close();
	});
	gridPane.setMinSize(200, 200);
	gridPane.add(addToCart,0,0);
	Scene scene = new Scene(gridPane);
	stage.setTitle("Generic");

	stage.setScene(scene);

	stage.show();
	
}
public void openHDDCartStage(long id, Account account, Cart cart) {
	session = HibernateUtil.getSessionFactory().openSession();
	HDD hdd = session.get(HDD.class, id);
	session.close();
	Stage stage = new Stage();
	GridPane gridPane = new GridPane();
	gridPane.setAlignment(Pos.CENTER);
	Button addToCart = new Button("Add to Cart.");
	addToCart.setOnAction(v ->{
		session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(cart);
		cart.addHDDToList(hdd);
		
		transaction.commit();
		session.close();
	});
	gridPane.setMinSize(200, 200);
	gridPane.add(addToCart,0,0);
	Scene scene = new Scene(gridPane);
	stage.setTitle("Generic");

	stage.setScene(scene);

	stage.show();
	
}
public void openPSUCartStage(long id, Account account, Cart cart) {
	session = HibernateUtil.getSessionFactory().openSession();
	PowerSupply psu = session.get(PowerSupply.class, id);
	session.close();
	Stage stage = new Stage();
	GridPane gridPane = new GridPane();
	gridPane.setAlignment(Pos.CENTER);
	Button addToCart = new Button("Add to Cart.");
	addToCart.setOnAction(v ->{
		session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.update(cart);
		cart.addPowerSupplyToList(psu);
		
		transaction.commit();
		session.close();
	});
	gridPane.setMinSize(200, 200);
	gridPane.add(addToCart,0,0);
	Scene scene = new Scene(gridPane);
	stage.setTitle("Generic");

	stage.setScene(scene);

	stage.show();
	
}
public void getTypeOfItem(String imageURL, Account account, Cart cart) {
	int index = imageURL.indexOf("cpuitem");
	if(index != -1) {
	String number = imageURL.substring(index);
	number =number.substring(7);
	 number =number.replaceAll(".jpg", "");
	System.out.println(number);
	openCPUCartStage(Long.parseLong(number),account, cart);
	}
	index = imageURL.indexOf("gpuitem");
	if(index != -1) {
		String number = imageURL.substring(index);
		number =number.substring(7);
		 number =number.replaceAll(".jpg", "");
		System.out.println(number);
		openGPUCartStage(Long.parseLong(number),account, cart);
		}
	index = imageURL.indexOf("hdditem");
	if(index != -1) {
		String number = imageURL.substring(index);
		number =number.substring(7);
		 number =number.replaceAll(".jpg", "");
		System.out.println(number);
		openHDDCartStage(Long.parseLong(number),account, cart);
		}
	index = imageURL.indexOf("psuitem");
	if(index != -1) {
		String number = imageURL.substring(index);
		number =number.substring(7);
		 number =number.replaceAll(".jpg", "");
		System.out.println(number);
		openPSUCartStage(Long.parseLong(number),account, cart);
		}
}

}
