package project.Stage;

import project.FilePath.FilePath;
import project.hibernate.dao.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project.hibernate.entity.Account;
import project.hibernate.entity.CPU;
import project.hibernate.entity.Cart;
import project.hibernate.entity.Credential;
import project.hibernate.entity.GPU;
import project.hibernate.entity.HDD;
import project.hibernate.entity.PlacedOrder;
import project.hibernate.entity.PowerSupply;
import project.pojo.ItemList;

@SuppressWarnings("unchecked")
public class StageService extends Dao {
	public void startStage(Stage stage) {

		Text usernameText = new Text("Username");

		Text passwordText = new Text("Password");

		TextField usernameField = new TextField();

		TextField passwordField = new TextField();

		Button logInButton = new Button("Log in");
		Button signInButton = new Button("Sign in");
		signInButton.setOnAction(value -> {
			try {
				signInStage(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		GridPane gridPane = new GridPane();
		logInButton.setOnAction(value -> {

			Account account = new Account();
			account.setAccount_username(usernameField.getText());
			account.setAccount_password(passwordField.getText());
			if (checkUserAndPass(account.getAccount_username(), account.getAccount_password())) {
				Account acc = getAccountByUsername(usernameField.getText());
				session = openSession();
				Cart cart = session.load(Cart.class, acc.getId());
				closeSession(session);
				menuStage(acc, cart);
				stage.close();

			} else {
				if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty())
					popUpStage("Fields can't be empty");
				else
					popUpStage("Wrong username or password.");

			}
		});
		gridPane.setMinSize(400, 200);

		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);

		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(usernameText, 0, 0);
		gridPane.add(usernameField, 1, 0);
		gridPane.add(passwordText, 0, 1);
		gridPane.add(passwordField, 1, 1);
		gridPane.add(logInButton, 0, 2);
		gridPane.add(signInButton, 1, 2);

		Scene scene = new Scene(gridPane);

		stage.setTitle("Project");

		stage.setScene(scene);

		stage.show();
	}

	protected void signInStage(Stage startStage) {
		Stage stage = new Stage();
		Text emailText = new Text("Email");
		Text usernameText = new Text("Username");
		Text passwordText = new Text("Password");
		TextField emailField = new TextField();
		TextField usernameField = new TextField();
		TextField passwordField = new TextField();
		Button createNewAccount = new Button("Create new Account");
		Button logIn = new Button("Log in");
		logIn.setOnAction(value -> {
			stage.close();
			startStage(stage);
		});
		createNewAccount.setOnAction(value -> {
			if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()
					|| usernameField.getText().isEmpty()) {
				signInStage(stage);
				popUpStage("Fields can't be empty. Please try again.");
			} else {
				if (checkSignInUsername(usernameField.getText())) {
					signInStage(stage);
					popUpStage("This username is already in use.");

				} else if (checkSignInEmail(emailField.getText())) {
					signInStage(stage);
					popUpStage("This email is already in use.");
				} else {
					Account account = new Account();
					Cart cart = new Cart();
					account.setAccount_username(usernameField.getText());
					account.setAccount_password(passwordField.getText());
					account.setAccount_email(emailField.getText());
					session = openSessionWithTransaction();
					account.setCart(cart);
					cart.setAccount(account);
					session.save(account);
					session.save(cart);
					closeSessionWithTransaction(session);

					menuStage(account, account.getCart());
					stage.close();
				}
			}

		});

		GridPane gridPane = new GridPane();
		gridPane.setMinSize(400, 200);

		gridPane.setPadding(new Insets(10, 10, 10, 10));

		gridPane.setVgap(5);
		gridPane.setHgap(5);

		gridPane.setAlignment(Pos.CENTER);

		gridPane.add(usernameText, 0, 0);
		gridPane.add(emailText, 0, 1);
		gridPane.add(passwordText, 0, 2);
		gridPane.add(usernameField, 1, 0);
		gridPane.add(emailField, 1, 1);
		gridPane.add(passwordField, 1, 2);
		gridPane.add(createNewAccount, 0, 3);
		gridPane.add(logIn, 1, 3);

		Scene scene = new Scene(gridPane);

		stage.setTitle("Project");

		stage.setScene(scene);

		startStage.close();
		stage.show();
	}

	protected void menuStage(Account account, Cart cart) {
		Stage stage = new Stage();
		Button changeAccountButton = new Button("Change Account");
		Button cartButton = new Button("Cart");
		cartButton.setFont(Font.font(16));
		changeAccountButton.setOnAction(value -> {
			stage.close();
			startStage(stage);
		});
		GridPane gridPane = new GridPane();
		GridPane searchBarGrid = new GridPane();
		gridPane.setMinSize(800, 800);
		cartButton.setOnAction(value -> {
			cartStage(account, cart);
			stage.close();
		});
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setAlignment(Pos.BASELINE_LEFT);
		searchBarGrid.setPadding(new Insets(0, 10, 0, 10));
		searchBarGrid.setAlignment(Pos.BASELINE_CENTER);
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.add(changeAccountButton, 0, 0);
		gridPane.add(searchBarGrid, 1, 0);
		gridPane.add(cartButton, 2, 0);
		try {
			gridPane.add(addImagesToMenu("cpu.jpg", stage, account, cart), 1, 1);
			gridPane.add(addImagesToMenu("hdd.jpg", stage, account, cart), 2, 1);
			gridPane.add(addImagesToMenu("gpu.jpeg", stage, account, cart), 1, 2);
			gridPane.add(addImagesToMenu("powersupply.jpg", stage, account, cart), 2, 2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(gridPane);
		stage.setTitle("Project");

		stage.setScene(scene);

		stage.show();
	}

	protected Group addImagesToMenu(String s, Stage stage, Account account, Cart cart) throws FileNotFoundException {
		
		InputStream stream = new FileInputStream(FilePath.path + s);
		Image image = new Image(stream);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setX(50);
		imageView.setY(50);
		imageView.setFitWidth(400);
		imageView.setFitHeight(400);
		imageView.setPreserveRatio(false);
		Group root = new Group(imageView);
		imageView.setOnMouseClicked(value -> {
			if (s.equals("cpu.jpg")) {
				cpuStage(account, cart);
				stage.close();
			}
			if (s.equals("hdd.jpg")) {
				hddStage(account, cart);
				stage.close();
			}
			if (s.equals("gpu.jpeg")) {
				gpuStage(account, cart);
				stage.close();
			}
			if (s.equals("powersupply.jpg")) {
				powerSupplyStage(account, cart);
				stage.close();
			}
		});
		return root;

	}

	protected boolean checkUserAndPass(String username, String password) {
		session = openSession();
		List<Account> accounts = new ArrayList<>();
		accounts = session.createQuery("FROM Account").list();
		closeSession(session);
		for (Account index : accounts) {
			if (index.getAccount_username().equals(username) && index.getAccount_password().equals(password))
				return true;
		}
		return false;
	}

	protected void popUpStage(String str) {
		Stage stage = new Stage();
		Text wrongUserOrPass = new Text(str);
		GridPane grid = new GridPane();
		grid.add(wrongUserOrPass, 0, 0);
		grid.setMinSize(100, 100);
		Scene scene = new Scene(grid);
		stage.setTitle("");
		stage.setScene(scene);
		stage.show();
	}

	protected boolean checkSignInEmail(String email) {
		session = openSession();
		List<Account> accounts = new ArrayList<>();
		accounts = session.createQuery("FROM Account").list();
		closeSession(session);
		for (Account account : accounts) {
			if (account.getAccount_email().equals(email)) {
				return true;
			}
		}
		return false;
	}

	protected boolean checkSignInUsername(String username) {
		session = openSession();
		List<Account> accounts = new ArrayList<>();
		accounts = session.createQuery("FROM Account").list();
		closeSession(session);
		for (Account account : accounts) {
			if (account.getAccount_username().equals(username)) {
				return true;
			}
		}
		return false;
	}

	protected void cpuStage(Account account, Cart cart) {
		Stage stage = new Stage();
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(800, 800);
		gridPane.setMaxHeight(800);
		gridPane.setGridLinesVisible(true);
		Button homeButton = new Button("Home");
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		homeButton.setOnAction(value -> {
			menuStage(account, cart);

			stage.close();
		});
		gridPane.add(homeButton, 0, 0);
		ItemGrid itemGrid = new ItemGrid();
		// !!!!!!!!
		GridPane grid2 = itemGrid.getCPUItemsGrid(account, cart);

		ScrollPane sp = new ScrollPane();
		sp.setContent(grid2);
		gridPane.add(grid2, 1, 1);
		gridPane.add(sp, 2, 0);

		Scene scene = new Scene(gridPane);
		stage.setTitle("CPU");

		stage.setScene(scene);

		stage.show();
	}

	protected void hddStage(Account account, Cart cart) {
		Stage stage = new Stage();
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(800, 800);
		gridPane.setMaxHeight(800);
		gridPane.setGridLinesVisible(true);
		Button homeButton = new Button("HOME");
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		homeButton.setOnAction(value -> {
			menuStage(account, cart);

			stage.close();
		});
		gridPane.add(homeButton, 0, 0);
		ItemGrid itemGrid = new ItemGrid();
		// !!!!!!!!
		GridPane grid2 = itemGrid.getHDDItemsGrid(account, cart);

		ScrollPane sp = new ScrollPane();
		sp.setContent(grid2);
		gridPane.add(grid2, 1, 1);
		gridPane.add(sp, 2, 0);

		Scene scene = new Scene(gridPane);
		stage.setTitle("HDD");

		stage.setScene(scene);

		stage.show();
	}

	protected void gpuStage(Account account, Cart cart) {
		Stage stage = new Stage();
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(800, 800);
		gridPane.setMaxHeight(800);
		gridPane.setGridLinesVisible(true);
		Button homeButton = new Button("HOME");
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		homeButton.setOnAction(value -> {
			menuStage(account, cart);

			stage.close();
		});
		gridPane.add(homeButton, 0, 0);
		ItemGrid itemGrid = new ItemGrid();
		// !!!!!!!!
		GridPane grid2 = itemGrid.getGPUItemsGrid(account, cart);

		ScrollPane sp = new ScrollPane();
		sp.setContent(grid2);
		gridPane.add(grid2, 1, 1);
		gridPane.add(sp, 2, 0);

		Scene scene = new Scene(gridPane);
		stage.setTitle("CPU");

		stage.setScene(scene);

		stage.show();
	}

	protected void powerSupplyStage(Account account, Cart cart) {
		Stage stage = new Stage();
		GridPane gridPane = new GridPane();
		gridPane.setMinSize(800, 800);
		gridPane.setMaxHeight(800);
		gridPane.setGridLinesVisible(true);
		Button homeButton = new Button("HOME");
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		homeButton.setOnAction(value -> {
			menuStage(account, cart);

			stage.close();
		});
		gridPane.add(homeButton, 0, 0);
		ItemGrid itemGrid = new ItemGrid();
		// !!!!!!!!
		GridPane grid2 = itemGrid.getPSUItemsGrid(account, cart);

		ScrollPane sp = new ScrollPane();
		sp.setContent(grid2);
		gridPane.add(grid2, 1, 1);
		gridPane.add(sp, 2, 0);

		Scene scene = new Scene(gridPane);
		stage.setTitle("PSU");

		stage.setScene(scene);

		stage.show();
	}

	@SuppressWarnings("rawtypes")
	protected void cartStage(Account account, Cart cart) {
		Stage stage = new Stage();
		Button back = new Button("Back");

		ListView listView = new ListView();
		GridPane gridPane = new GridPane();
		Button toOrder = new Button("order");
		back.setOnAction(value -> {
			menuStage(account, cart);
			stage.close();
		});
		toOrder.setOnAction(v -> {
			credentialStage(account, cart);
			stage.close();
		});

		ItemList itemlist = new ItemList();
		itemlist.setCart(cart);
		itemlist.getAllDescriptions(cart.getId());
		int line = 1;
		for (String item : itemlist.getListDescriptions()) {

			listView.getItems().add(Integer.toString(line) + ". " + item);
			line++;
		}
		listView.setOnMouseClicked(v -> {
			String desc = (String) listView.getSelectionModel().getSelectedItem();
			desc = desc.substring(3);
			deleteItemFromCartStage(account, cart, desc, stage);
		});
		listView.setMinSize(600, 600);
		gridPane.setMinSize(800, 800);
		gridPane.add(back, 0, 0);
		gridPane.add(listView, 0, 1);
		gridPane.add(toOrder, 0, 2);
		Scene scene = new Scene(gridPane);
		stage.setTitle("Cart");

		stage.setScene(scene);

		stage.show();

	}

	public Account getAccountByUsername(String username) {
		session = openSession();
		List<Account> accounts = new ArrayList<>();
		accounts = session.createQuery("FROM Account").list();
		closeSession(session);
		Account account = new Account();
		for (Account acc : accounts) {
			if (acc.getAccount_username().equals(username))
				return acc;
		}
		return account;
	}

	public Cart getCartForAccount(Account account) {
		Cart cart = account.getCart();

		return cart;
	}

	public boolean checkIfAccountHasCart(Account account) {
		if (account.getCart().getAccount() == null)
			return false;
		return true;
	}

	public Cart getCartByAccountId(long id) {
		session = openSession();
		List<Cart> carts = new ArrayList<>();
		carts = session.createQuery("FROM Cart").list();
		closeSession(session);
		Cart cart = new Cart();
		for (Cart item : carts) {
			if (item.getId() == id)
				return cart;
		}
		return null;
	}

	public void credentialStage(Account account, Cart cart) {
		Stage stage = new Stage();
		Button back = new Button("Back");
		Button setOrder = new Button("Order");
		TextField cardNumberField = new TextField();
		TextField CVCField = new TextField();
		Text cardNumberText = new Text("Card Number");
		Text CVCText = new Text("CVC");
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setMinSize(400, 200);
		back.setOnAction(value -> {
			cartStage(account, cart);
			stage.close();
		});
		setOrder.setOnAction(v -> {
			if (checkCardNumberFormat(cardNumberField.getText()) && checkCVCFormat(CVCField.getText())) {

				if (checkIfCredentialExists(cardNumberField.getText(), Integer.parseInt(CVCField.getText()))) {
					Credential credential = getCredential(cardNumberField.getText(),
							Integer.parseInt(CVCField.getText()));
					if (checkIfCredentialExistsForAccount(account, credential)) {
						setCartToOrder(cart, account, credential);
						emptyCart(cart);
						menuStage(account, cart);
						stage.close();
					} else {
						popUpStage("Wrong credentials. Please try again.");
					}

				} else {
					Credential credential = addCredentialToAccount(account, cardNumberField.getText(),
							CVCField.getText());
					setCartToOrder(cart, account, credential);
					emptyCart(cart);
					menuStage(account, cart);
					stage.close();
				}
			} else {
				popUpStage("Wrong cardNumber or CVC format. Please try again");
			}

		});
		gridPane.add(back, 0, 2);
		gridPane.add(cardNumberText, 0, 0);
		gridPane.add(cardNumberField, 1, 0);
		gridPane.add(CVCField, 1, 1);
		gridPane.add(CVCText, 0, 1);
		gridPane.add(setOrder, 1, 2);
		Scene scene = new Scene(gridPane);
		stage.setTitle("Credential");

		stage.setScene(scene);

		stage.show();
	}

	// adds new Credential To account and saves it
	private Credential addCredentialToAccount(Account account, String cardNumber, String CVC) {
		// 1
		session = openSession();
		Credential credential = new Credential();
		credential.setCardNumber(cardNumber);
		credential.setCVC(Integer.parseInt(CVC));
		credential.setAccount(account);
		session.save(credential);
		closeSession(session);
		// 2
		session = openSessionWithTransaction();
//		List<Credential> credentials = new ArrayList<>();
//		credentials.addAll(account.getCredentials());
//		credentials.add(credential);
		session.update(account);
		account.addCredentialToAccount(credential);
		closeSessionWithTransaction(session);

		return credential;
	}

	// creates new order and sets the cpu, gpu lists + account and credential
	private void setCartToOrder(Cart cart, Account account, Credential credential) {
		// create new order
		// open
		// 1 set account on order and order on account
		session = openSession();
		PlacedOrder order = new PlacedOrder();

		order.setAccount(account);

		order.setCredential(credential);
		session.save(order);
		closeSession(session);
		// 2
		session = openSessionWithTransaction();
		order.setCpus(cart.getCpus());
		session.update(order);
		closeSessionWithTransaction(session);
		// 3
		session = openSessionWithTransaction();
		order.setPowerSupply(cart.getPowerSupply());
		session.update(order);
		closeSessionWithTransaction(session);
		//
		session = openSessionWithTransaction();
		order.setGpus(cart.getGpus());
		session.update(order);
		closeSessionWithTransaction(session);
		//
		session = openSessionWithTransaction();
		order.setHdds(cart.getHdds());
		session.update(order);
		closeSessionWithTransaction(session);
		// close
		// account placeorder
		// open
		session = openSessionWithTransaction();
		account.addOrderToAccount(order);
		session.update(account);
		// close
		closeSessionWithTransaction(session);
		// credential update
		// open
		session = openSessionWithTransaction();
		session.update(credential);
		credential.addCredentialToPlacedOrder(order);
		// close
		closeSessionWithTransaction(session);
	}

	private void emptyCart(Cart cart) {
		session = openSessionWithTransaction();
		List<CPU> emptyCPUList = new ArrayList<>();
		List<GPU> emptyGPUList = new ArrayList<>();
		List<HDD> emptyHDDList = new ArrayList<>();
		List<PowerSupply> emptyPSUList = new ArrayList<>();
		cart.setCpus(emptyCPUList);
		cart.setGpus(emptyGPUList);
		cart.setHdds(emptyHDDList);
		cart.setPowerSupply(emptyPSUList);
		session.update(cart);
		closeSessionWithTransaction(session);
	}

	private boolean checkIfCredentialExists(String cardNumber, int CVC) {
		List<Credential> credentials = new ArrayList<>();
		session = openSession();
		credentials = session.createQuery("From Credential").list();
		closeSession(session);
		for (Credential item : credentials) {
			if (item.getCardNumber().equals(cardNumber) && item.getCVC() == CVC)
				return true;
		}
		return false;
	}

	private boolean checkIfCredentialExistsForAccount(Account account, Credential credential) {
		if (credential.getAccount().getId() == account.getId())
			return true;
		return false;
	}

	private Credential getCredential(String cardNumber, int CVC) {
		List<Credential> credentials = new ArrayList<>();
		session = openSession();
		credentials = session.createQuery("From Credential").list();
		closeSession(session);
		for (Credential item : credentials) {
			if (item.getCardNumber().equals(cardNumber) && item.getCVC() == CVC)
				return item;
		}
		return null;
	}

	private boolean checkCardNumberFormat(String cardNumber) {
		if (cardNumber.length() != 19)
			return false;
		if (cardNumber.charAt(4) != '-' || cardNumber.charAt(9) != '-' || cardNumber.charAt(14) != '-')
			return false;
		return true;
	}

	private boolean checkCVCFormat(String CVC) {
		if (CVC.length() != 3)
			return false;
		else {
			if ((CVC.charAt(0) >= 48 && CVC.charAt(0) <= 57) && (CVC.charAt(1) >= 48 && CVC.charAt(1) <= 57)
					&& (CVC.charAt(0) >= 48 && CVC.charAt(0) <= 57))
				return true;
			return false;
		}
	}

	private void deleteItemFromCartStage(Account account, Cart cart, String itemDescription, Stage cartStage2) {
		Stage stage = new Stage();
		Button deleteItemButton = new Button("Delete Item");
		GridPane grid = new GridPane();
		deleteItemButton.setOnAction(v -> {
			deleteCPUFromList(cart, itemDescription);
			deleteGPUFromList(cart, itemDescription);
			deleteHDDFromList(cart, itemDescription);
			deletePSUFromList(cart, itemDescription);
			cartStage(account, cart);
			cartStage2.close();
			stage.close();
		});
		grid.setMinSize(100, 100);
		grid.add(deleteItemButton, 0, 0);
		Scene scene = new Scene(grid);
		stage.setTitle("");
		stage.setScene(scene);
		stage.show();
	}

	private void deleteCPUFromList(Cart cart, String itemDescription) {

		session = openSessionWithTransaction();
		session.update(cart);

		List<CPU> cpuList = cart.getCpus();
		List<CPU> cpuListCopy = new ArrayList<>();
		cpuListCopy.addAll(cpuList);
		boolean check = false;
		for (CPU cpu : cpuListCopy) {
			if (cpu.getDescription().equals(itemDescription)) {
				cpuList.remove(cpu);

				check = true;
				break;
			}
		}

		if (check) {
			cart.setCpus(cpuList);
			closeSessionWithTransaction(session);

		} else {
			closeSessionWithTransaction(session);
		}
	}

	private void deleteGPUFromList(Cart cart, String itemDescription) {

		session = openSessionWithTransaction();
		session.update(cart);

		List<GPU> gpuList = cart.getGpus();
		List<GPU> gpuListCopy = new ArrayList<>();
		gpuListCopy.addAll(gpuList);
		boolean check = false;
		for (GPU gpu : gpuListCopy) {
			if (gpu.getDescription().equals(itemDescription)) {
				gpuList.remove(gpu);
				check = true;
				break;
			}
		}

		if (check) {
			cart.setGpus(gpuList);
			closeSessionWithTransaction(session);

		} else {
			closeSessionWithTransaction(session);
		}
	}

	private void deleteHDDFromList(Cart cart, String itemDescription) {

		session = openSessionWithTransaction();
		session.update(cart);

		List<HDD> hddList = cart.getHdds();
		List<HDD> hddListCopy = new ArrayList<>();
		hddListCopy.addAll(hddList);
		boolean check = false;
		for (HDD hdd : hddListCopy) {
			if (hdd.getDescription().equals(itemDescription)) {
				hddList.remove(hdd);
				check = true;
				break;
			}
		}

		if (check) {
			cart.setHdds(hddList);
			closeSessionWithTransaction(session);

		} else {
			closeSessionWithTransaction(session);
		}
	}

	private void deletePSUFromList(Cart cart, String itemDescription) {

		session = openSessionWithTransaction();
		session.update(cart);

		List<PowerSupply> psuList = cart.getPowerSupply();
		List<PowerSupply> psuListCopy = new ArrayList<>();
		psuListCopy.addAll(psuList);
		boolean check = false;
		for (PowerSupply psu : psuListCopy) {
			if (psu.getDescription().equals(itemDescription)) {
				psuList.remove(psu);
				check = true;
				break;
			}
		}

		if (check) {
			cart.setPowerSupply(psuList);
			closeSessionWithTransaction(session);

		} else {
			closeSessionWithTransaction(session);
		}
	}
}
