package org.CafeManageMent.CafeManageSys;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.Inventory.InventoryDAO;
import com.dataBases.InventryDAO;
import com.dataBases.PerformOperations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ManageInventoryController implements Initializable {
	@FXML
	private TableView<InventoryDAO> InventoryTreeView;

	@FXML
	private TableColumn<InventoryDAO, String> itemIdColumn;

	@FXML
	private TableColumn<InventoryDAO, String> itemSold;

	@FXML
	private TableColumn<InventoryDAO, String> nameColumn;

	@FXML
	private TableColumn<InventoryDAO, String> priceColumn;

	@FXML
	private TableColumn<InventoryDAO, String> quantityColumn;

	@FXML
	private TableColumn<InventoryDAO, String> sn;

	@FXML
	private TextField quantityText;

	@FXML
	private TextField searchItemText;

	@FXML
	private TextField priceText;

	@FXML
	private TextField nameText;
	@FXML
	private TextField itemIdText;
	@FXML
	private ImageView itemIimage;

	ObservableList<InventoryDAO> data = FXCollections.observableArrayList();
	private List<InventryDAO> list;
	PerformOperations operation = new PerformOperations();
	private String id = "";
	private byte[] img;
	private PerformOperations values;
	
	public void refresh() {
		list = values.getValues();
		data.clear();
		for (InventryDAO obj : list) {
			InventoryDAO dao = new InventoryDAO(String.valueOf(obj.getId()), obj.getName(), obj.getPrice(),
					obj.getItem_ID(), String.valueOf(obj.getItemsSold()));
			data.add(dao);
			priceColumn.setCellValueFactory(cellData -> cellData.getValue().getPrice());
			itemIdColumn.setCellValueFactory(cellData -> cellData.getValue().getItem_ID());

			sn.setCellValueFactory(cellData -> cellData.getValue().getId());
			nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
			itemSold.setCellValueFactory(cellData -> cellData.getValue().getItemsSold());
			InventoryTreeView.setItems(data);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		values = new PerformOperations();
		refresh();

	}

//	Method , used to find item 
	@FXML
	public void searchItem() {

		boolean found = false;
		list = operation.getValues();

		for (InventryDAO object : list) {
			if (object.getName().equals(searchItemText.getText())) {
				quantityText.setText(String.valueOf(object.getItemsSold()));
				nameText.setText(object.getName());
				priceText.setText(object.getPrice());
				itemIdText.setText(object.getItem_ID());
				id = String.valueOf(object.getId());
				// image

				img = object.getImage();
				Image image = new Image(new ByteArrayInputStream(img));
				itemIimage.setImage(image);
				found = true;
			}
		}
		if (!found) {
			AlertClass alertClass = new AlertClass();
			alertClass.showAlert(Alert.AlertType.ERROR, "No Data Found",
					"The Value your searching for is not available");
		}

	}

	@FXML
	public void addNewImage() {
		
		// Create a FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		java.io.File selectedFile = fileChooser.showOpenDialog(new Stage());
		System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		String path = selectedFile.getAbsolutePath();
		URL url = null;
		try {
			url = selectedFile.toURI().toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(url.toString());
		itemIimage.setImage(new Image(url.toString()));

		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			img = new byte[fileInputStream.available()];
			fileInputStream.read(img);
			System.out.println(img);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML 
	public void updateItem() {
		String name = nameText.getText();
		String ID = id;
		String price = priceText.getText();
		String itemsSold = quantityText.getText();
		String itemId = itemIdText.getText();
		try {
			Integer.parseInt(price);
			Integer.parseInt(quantityText.getText());
		} catch (NumberFormatException e) {
			AlertClass alertClass = new AlertClass();
			alertClass.showAlert(Alert.AlertType.ERROR, "Wrong Data Entered", "" + "Please Enter Price "
					+ " OR in Sold Items in Numbers");
		}


		if (nameText.getText().isBlank() || itemIimage.getImage() == null || priceText.getText().isBlank()
				|| quantityText.getText().isBlank() || itemIdText.getText().isBlank()) {
			AlertClass alertClass = new AlertClass();
			alertClass.showAlert(Alert.AlertType.WARNING, "Empty Fields", "" + "Please Fill All the FIELDS ");

		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					InventryDAO obj = new InventryDAO(Integer.valueOf(ID), name, price, itemId, img,Integer.valueOf(itemsSold));
					PerformOperations opera = new PerformOperations();
					opera.updateInventry(obj);
					new AlertClass().showAlert(Alert.AlertType.CONFIRMATION, "DONE", "" + "Item Updated ");
					//Clearing every field
					quantityText.setText("");
					nameText.setText("");
					priceText.setText("");
					itemIdText.setText("");
					itemIimage.setImage(null);
					refresh();
				}
			});

		}

	}

	@FXML public void removeItem() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				PerformOperations ope = new PerformOperations();
				quantityText.setText("");
				nameText.setText("");
				priceText.setText("");
				itemIdText.setText("");
				itemIimage.setImage(null);
				ope.removeSingleDataFromInventory(id);
				new AlertClass().showAlert(Alert.AlertType.INFORMATION,
						"Done", "Item Removed Successfully");
				refresh();
			}
			// Perform action on Cancel button click}
		});
	}

}
