 package org.CafeManageMent.CafeManageSys;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

<<<<<<< HEAD
=======
import com.dataBases.EmpDAO;
import com.dataBases.PerformOperations;
>>>>>>> 2b044c68cd36c2c3415ff11a970a0c6eda41f588
import com.effects.EffectsOnButtons;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
<<<<<<< HEAD
import javafx.scene.Node;
=======
>>>>>>> 2b044c68cd36c2c3415ff11a970a0c6eda41f588
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
<<<<<<< HEAD
import javafx.stage.Stage;
=======
>>>>>>> 2b044c68cd36c2c3415ff11a970a0c6eda41f588

public class PrimaryController implements Initializable {
	@FXML
	private ImageView login;
	private Image imageLogin;
	@FXML
	TextField userName;
	@FXML
	TextField password;
	
	private EffectsOnButtons effects1, effects2,effect3;

	@FXML
	private Button loginButton;
	@FXML
	private Button quitButton;
<<<<<<< HEAD
	@FXML
	private Button directLogin;


	@FXML
	public void loginAsManager() throws IOException {
		// IF No table are created
		if (userName.getText().equals("manager") && password.getText().equals("manager")) {
			App.setRoot("secondary");
			Main.setRole("Manager");
		}else {
			new AlertClass().showAlert(Alert.AlertType.WARNING, "Invalid", "Invalid Username or Password");
=======
	private List<EmpDAO> empList;
	PerformOperations ope = new PerformOperations();
	private boolean found = false;

	@FXML
	public void switchToSecondary() throws IOException {
		// Getting data from db
		empList = ope.getEmployeeValues();
		ope.getAllBookings(); 
		ope.getValues();

		System.out.println("Hello : " + ope.login(userName.getText(), password.getText()));
		// IF No table are created
		if (empList.size() == 0) {
			System.out.println(empList.size());
			EmpDAO empTable = new EmpDAO("demo", "demo", "Manager", "0000", "demo", "demo", "manager", "manager");
			ope.saveData(empTable);
			PopupLayoutDisplayer p = new PopupLayoutDisplayer();
			p.setPopupLayout("AddFirstManager", new Image(App.class.getResource("/Images/cafe.png").toExternalForm()),
					"Add Manager");

			App.setRoot("secondary");
			found = true;
		}

		String user = ope.login(userName.getText(), password.getText());
		if (user != null && user.equals("Manager")) {
			App.setRoot("secondary");
			found = true;
		} else if (user != null && (user.equals("Cook") || user.equals("Cachier"))) {
			App.setRoot("employee");
			found = true;
		}
		
		
		if (!found) {
//			App.setRoot("secondary");
			new AlertClass().showAlert(Alert.AlertType.ERROR, "Invalid",
					"Invalid Username or PassWord" + " Or You Cannot Aceess this Application");
>>>>>>> 2b044c68cd36c2c3415ff11a970a0c6eda41f588
		}

	}
	@FXML public void directLogin() throws IOException {
		App.setRoot("secondary");
		Main.setRole("USER");
	}

	@FXML
	void quiutAction(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		imageLogin = new Image(App.class.getResource("/Images/login2.png").toExternalForm());
		login.setImage(imageLogin);

		effects1 = new EffectsOnButtons();
		effects2 = new EffectsOnButtons();
		effect3 = new EffectsOnButtons();
		effects1.setShadowEffectOnButton(loginButton);
		effects2.setShadowEffectOnButton(quitButton);
		effect3.setShadowEffectOnButton(directLogin);
		

	}


}
//<!-- created By instagram -> vishalbala_here -->
