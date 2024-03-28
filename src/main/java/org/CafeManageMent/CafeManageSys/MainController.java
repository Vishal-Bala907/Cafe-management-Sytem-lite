package org.CafeManageMent.CafeManageSys;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import javafx.event.Event;

public class MainController implements Initializable {
	@FXML
	private Tab menuTab;

	@FXML
	private Tab addItemsToInventoryTab;

	@FXML
	private Tab manageInventoryTab;

	@FXML
	private Tab addEmployeeTab;

	@FXML
	private Tab manageStaffTab;

	@FXML
	private Tab newBookingTab;

	@FXML
	private Tab manageBookingsTab;

	private ScrollPane pane;
	private ScrollPane errorPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			errorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UnautorizeAccessDenied.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method for handling menu and bill
	@FXML
	public void menuTabSelected() throws IOException {
		pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MenuTab.fxml")));
		menuTab.setContent(pane);
	}

	@FXML
	void addItemsToInventory() throws IOException {

		addItemsToInventoryTab.setContent(authorizeUserFirst("AddItemToInventory.fxml"));
	}

	@FXML
	public void managInventory() throws IOException {
		manageInventoryTab.setContent(authorizeUserFirst("ManageInventory.fxml"));
	}

	@FXML
	public void addEmployee() throws IOException {
		addEmployeeTab.setContent(authorizeUserFirst("AddNewEmployee.fxml"));
	}

	@FXML
	public void manageStaff() throws IOException {
		manageStaffTab.setContent(authorizeUserFirst("ManageStaffLayout.fxml"));
	}

	@FXML
	public void newBooking() throws IOException {
		newBookingTab.setContent(authorizeUserFirst("MakeNewBooking.fxml"));
	}

	@FXML
	public void manageBookings() throws IOException {
		manageBookingsTab.setContent(authorizeUserFirst("ManageBookings.fxml"));
	}

	@SuppressWarnings("exports")
	public ScrollPane authorizeUserFirst(String sceneName) throws IOException {
		// Authorization
		if (!Main.getRole().equals("Manager")) {
			new AlertClass().showAlert(Alert.AlertType.WARNING, "Access Denied", "Your Not Authorize To Access This");
			return errorPane;
		} else {
			pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(sceneName)));
			return pane;
		}
	}



}
