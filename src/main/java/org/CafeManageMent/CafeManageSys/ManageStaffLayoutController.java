package org.CafeManageMent.CafeManageSys;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.Inventory.EmpLists;
import com.dataBases.EmpDAO;
import com.dataBases.PerformOperations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ManageStaffLayoutController implements Initializable {
	@FXML
	private TableColumn<EmpLists, String> AddressColumn;

	@FXML
	private TableColumn<EmpLists, String> contactColumn;

	@FXML
	private TableColumn<EmpLists, String> idColumn;

	@FXML
	private TableView<EmpLists> managerTableView;

	@FXML
	private TableColumn<EmpLists, String> nameColumn;

	@FXML
	private TableColumn<EmpLists, String> postColumn;

	@FXML
	private TableColumn<EmpLists, String> salaryColumn;

	@FXML
	private TableColumn<EmpLists, String> sn;

	private ObservableList<EmpLists> data = FXCollections.observableArrayList();
	private List<EmpDAO> list;

	@FXML
	private TextField empAddress;

	@FXML
	private TextField empContact;

	@FXML
	private TextField empID;

	@FXML
	private TextField empName;

	@FXML
	private ComboBox<String> empPost;
	private String[] empTypes = { "Manager", "Cook", "Cachier", "Cleaner", "Waiter" };
	@FXML
	private TextField empSalary;
	@FXML
	private TextField searchItemText;
	private String id;
	private boolean found = false;
	private PerformOperations values;

	public void refresh() {
		list = values.getEmployeeValues();
		data.clear();
		for (EmpDAO obj : list) {
			data.add(new EmpLists(String.valueOf(obj.getId()), obj.getName(), obj.getID(), obj.getPost(),
					obj.getSalary(), obj.getContant(), obj.getAddress()));
			sn.setCellValueFactory(cellData -> cellData.getValue().getSn());
			nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
			idColumn.setCellValueFactory(cellData -> cellData.getValue().getID());

			postColumn.setCellValueFactory(cellData -> cellData.getValue().getPost());
			salaryColumn.setCellValueFactory(cellData -> cellData.getValue().getSalary());
			AddressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddress());
			contactColumn.setCellValueFactory(cellData -> cellData.getValue().getContact());

		}
		managerTableView.setItems(data);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		values = new PerformOperations();
		ObservableList<String> list = FXCollections.observableArrayList();
		list.addAll(empTypes);
		empPost.setItems(list);
		refresh();

	}

	@FXML
	public void update() {
		String name = empName.getText();
		String empIDDB = empID.getText();
		String post = empPost.getSelectionModel().getSelectedItem();
		String salary = empSalary.getText();
		String contact = empContact.getText();
		String address = empAddress.getText();
		try {
			Integer.parseInt(salary);
		} catch (NumberFormatException ex) {
			new AlertClass().showAlert(Alert.AlertType.INFORMATION, "Wrong Salary Entered",
					"" + "Please Enter Salary In DIGITS");
			empSalary.setText(null);
		}

		if (name.isBlank() || address.isBlank() || empIDDB.isBlank() || empSalary.getText().isBlank()
				|| contact.isBlank() || post == null ) {
			new AlertClass().showAlert(Alert.AlertType.ERROR, "Missing Data", "" + "Please Enter All the Details");

		} else {
			EmpDAO update = new EmpDAO(name, empIDDB, post, salary, contact, address);
			update.setId(Integer.parseInt(id));
			values.updateEmployee(update);
			new AlertClass().showAlert(Alert.AlertType.CONFIRMATION, "Done", "Employee Updated Successfully");
			reset();
			refresh();
		}
	}

	@FXML
	public void remove() {
		values.removeEmployee(id);
		new AlertClass().showAlert(Alert.AlertType.CONFIRMATION, "Done", "Employee Has Been Removed Successfully");
		reset();
		refresh();
	}

	public void reset() {
		empName.setText("");
		empID.setText("");
		empPost.setValue("");
		empSalary.setText("");
		empContact.setText("");
		empAddress.setText("");
		id = String.valueOf("");
	}

	@FXML
	public void searchItem() {
		for (EmpDAO object : list) {
			if (object.getName().equals(searchItemText.getText())) {
				empName.setText(object.getName());
				empID.setText(object.getEmpID());
				empPost.setValue(object.getPost());
				empSalary.setText(object.getSalary());
				empContact.setText(object.getContant());
				empAddress.setText(object.getAddress());
				id = String.valueOf(object.getId());
				found = true;
			}
		}
		if (!found) {
			AlertClass alertClass = new AlertClass();
			alertClass.showAlert(Alert.AlertType.ERROR, "No Data Found",
					"The Value your searching for is not available");
		}
	}

}
