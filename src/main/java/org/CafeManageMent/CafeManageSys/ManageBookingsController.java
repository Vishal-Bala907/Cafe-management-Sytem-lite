package org.CafeManageMent.CafeManageSys;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.Inventory.BookingListData;
import com.dataBases.BookinDAO;
import com.dataBases.PerformOperations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ManageBookingsController implements Initializable {
	@FXML
	private TableColumn<BookingListData, String> NumberOfPersons;

	@FXML
	private TableColumn<BookingListData, String> contact;

	@FXML
	private TableColumn<BookingListData, String> cost;

	@FXML
	private TableColumn<BookingListData, String> date;

	@FXML
	private TableColumn<BookingListData, String> id;

	@FXML
	private TableView<BookingListData> bookingTable;

	@FXML
	private TableColumn<BookingListData, String> name;

	@FXML
	private TableColumn<BookingListData, String> total;

	@FXML
	private TextField contact1;

	@FXML
	private TextField discount;

	@FXML
	private DatePicker date1;

	@FXML
	private TextField empName;

	@FXML
	private TextField seats;

	@FXML
	private TextField total1;
	@FXML
	private TextField costPerPerson;
	@FXML
	private Label lable;

	private List<BookinDAO> allBookings;
	private int ID;
	private boolean booked = false;

	private PerformOperations operation = new PerformOperations();
	private List<BookinDAO> list;
	private ObservableList<BookingListData> values = FXCollections.observableArrayList();;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		allBookings = operation.getAllBookings();
		total1.setEditable(false);

		list = operation.getAllBookings();
		refresh();
	}

	public void refresh() {
		values.clear();
		list = operation.getAllBookings();
		for (BookinDAO item : list) {
			BookingListData obj = new BookingListData(String.valueOf(item.getId()), item.getName(),
					item.getNumberOfPersons(), item.getCost(),  item.getDate(), item.getMob(),
					item.getTotalPrice());
			values.add(obj);
		}

		for (@SuppressWarnings("unused")
		BookingListData obj : values) {

			NumberOfPersons.setCellValueFactory(cellData -> cellData.getValue().getNumberOfPersons());
			contact.setCellValueFactory(cellData -> cellData.getValue().getMob());
			id.setCellValueFactory(cellData -> cellData.getValue().getId());
			cost.setCellValueFactory(cellData -> cellData.getValue().getCost());
			date.setCellValueFactory(cellData -> cellData.getValue().getDate());
			name.setCellValueFactory(cellData -> cellData.getValue().getName());
			total.setCellValueFactory(cellData -> cellData.getValue().getTotal());

			bookingTable.setItems(values);
		}

	}

	@FXML
	public void searchItem() {
		String name = empName.getText();
		for (BookinDAO dao : allBookings) {
			if (dao.getName().equals(name)) {
				empName.setText(name);
				date1.setValue(LocalDate.parse(dao.getDate()));
				seats.setText(dao.getNumberOfPersons());
				contact1.setText(dao.getMob());
				total1.setText(dao.getTotalPrice());
				ID = Integer.valueOf(dao.getId());
				costPerPerson.setText(dao.getCost());
				lable.setText("BOOKED: " + dao.getNumberOfPersons());
				booked = true;
			}
		}
		if (!booked) {
			AlertClass alert = new AlertClass();
			alert.showAlert(Alert.AlertType.ERROR, "None", "Booking Not Found");
			discount.setText(null);
		}
	}

	@FXML
	public void remove() {
		Alert con = new Alert(Alert.AlertType.CONFIRMATION);
		con.setContentText("Are you Sure");
		con.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				AlertClass alert = new AlertClass();
				operation.deleteSingleBooking(String.valueOf(ID));
				alert.showAlert(AlertType.CONFIRMATION, "Done", " Booking Deleted");
				refresh();
				reset();
			}
		});
	}

	@FXML
	public void reset() {
		empName.setText("");
		date1.setValue(LocalDate.now());
		seats.setText("");
		contact1.setText("");
		total1.setText("");
		costPerPerson.setText("");
	}

}
