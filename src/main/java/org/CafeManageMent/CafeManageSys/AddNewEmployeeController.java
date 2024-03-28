package org.CafeManageMent.CafeManageSys;

import java.net.URL;
import java.util.ResourceBundle;

import com.dataBases.EmpDAO;
import com.dataBases.PerformOperations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddNewEmployeeController implements Initializable {

	@FXML
	private Button cancel;

	@FXML
	private Button done;

	@FXML
	private TextField empAddress;

	@FXML
	private TextField empContact;

	@FXML
	private TextField empId;

	@FXML
	private TextField empName;

	@FXML
	private ComboBox<String> empPost;
	private String[] empTypes = { "Manager", "Cook", "Cachier", "Cleaner", "Waiter" };

	@FXML
	private TextField empSalary;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> list = FXCollections.observableArrayList();
		list.addAll(empTypes);
		empPost.setItems(list);
	}

	@FXML
	public void reset() {
		empName.setText(null);
		empAddress.setText(null);
		empId.setText(null);
		empSalary.setText(null);
		empContact.setText(null);
		empPost.setSelectionModel(null);
		empPost.setValue(null);
	}

	@FXML
	public void done() {
		String name = empName.getText();
		String address = empAddress.getText();
		String Id = empId.getText();
		String salary = empSalary.getText();
		String contact = empContact.getText();
		String selectedItem = empPost.getSelectionModel().getSelectedItem();
		int saveData = -1;
		try {
			Integer.parseInt(salary);
		} catch (NumberFormatException ex) {
			new AlertClass().showAlert(Alert.AlertType.WARNING, "Wrong Salary Entered",
					"" + "Please Enter Salary In DIGITS");
			empSalary.setText("");
		}

		if (empName.getText().isBlank() || empAddress.getText().isBlank() || Id.isBlank()
				|| empSalary.getText().isBlank() || contact.isBlank() || selectedItem == null ) {
			new AlertClass().showAlert(Alert.AlertType.CONFIRMATION, "Missing Data",
					"" + "Please Enter All the Details");

		} else {
			EmpDAO emp = new EmpDAO(name, Id, selectedItem, salary, contact, address);
			PerformOperations ope = new PerformOperations();
			saveData = ope.saveData(emp);
		}

		if (saveData > 0) {
			empName.setText(null);
			empAddress.setText(null);
			empId.setText(null);
			empSalary.setText(null);
			empContact.setText(null);
			empPost.setSelectionModel(null);
			empPost.setValue(null);
			new AlertClass().showAlert(Alert.AlertType.CONFIRMATION, "Done", "Employee Added Successfully");
		} else {
			new AlertClass().showAlert(Alert.AlertType.ERROR, "Something went wrong", "Some Error Occured");

		}
	}

}
