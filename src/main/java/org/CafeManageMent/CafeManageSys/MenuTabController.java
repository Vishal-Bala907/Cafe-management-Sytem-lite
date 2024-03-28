package org.CafeManageMent.CafeManageSys;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.Inventory.ShowBillItemsOnBill;
import com.dataBases.InventryDAO;
import com.dataBases.PerformOperations;
import com.effects.EffectsOnButtons;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class MenuTabController implements Initializable {

	@FXML
	private FlowPane menuare;

	@FXML
	private TextArea textArea;
    @FXML
    private Label grandTotal;
	private AnchorPane[] menuItems; // card for item
	private Label[] itemNameAndPrice; // this label contains items name and price
	private ImageView[] itemImage; // this contains items image
	private HBox[] hbox;
<<<<<<< HEAD:src/main/java/org/CafeManageMent/CafeManageSys/MenuTabController.java
	private Spinner<Integer>[] spinner; // spinner to select a value or QT
	private Button[] addButton; // button to add item to the bill
	private int items = 0;
=======
	private Spinner<Integer>[] spinner;
	private Button[] addButton;
	private int items = 0; 
	private int height = 700;
>>>>>>> 2b044c68cd36c2c3415ff11a970a0c6eda41f588:src/main/java/org/CafeManageMent/CafeManageSys/MenuController.java

	
	// For Displaying Bill Items on Bill
    @FXML
    private TableView<ShowBillItemsOnBill> InventoryTreeView;
    @FXML
    private TableColumn<ShowBillItemsOnBill, String> nameAndPrice;

    @FXML
    private TableColumn<ShowBillItemsOnBill, String> qtCol;

    @FXML
    private TableColumn<ShowBillItemsOnBill, String> total;
    ObservableList<ShowBillItemsOnBill> billItemsList = FXCollections.observableArrayList();

	List<InventryDAO> update; // values need to update
	List<InventryDAO> values; // values from the db
	private int GrandTotal = 0;
	ArrayList<String> quantity = new ArrayList<>(); // quantity to be updated
	ArrayList<String> itemId = new ArrayList<>(); // items that need to be updated
	ArrayList<String> billItems = new ArrayList<String>();

	// This method setup the menu card
	public void setMenuItems(@SuppressWarnings("exports") AnchorPane[] menuItems) {
		for (int i = 0; i < menuItems.length; i++) {
			menuItems[i] = new AnchorPane();
			menuItems[i].setMaxHeight(Double.MAX_VALUE);
			menuItems[i].setMaxWidth(Double.MAX_VALUE);
			menuItems[i].setMinHeight(225.0);
			menuItems[i].setMinWidth(225.0);
			menuItems[i].setPrefHeight(225.0);
			menuItems[i].setPrefWidth(225.0);
			menuItems[i].setStyle("-fx-background-color: #96eaff; -fx-border-color: #00838c; -fx-border-width: 3px;");
			EffectsOnButtons ef = new EffectsOnButtons();
			ef.setShadowEffectOnMenuItems(menuItems[i]);
		}

	}

	// this method set the items name and price label
	public void setItemNameAndPrice(@SuppressWarnings("exports") Label[] itemNameAndPrice) {
		for (int i = 0; i < itemNameAndPrice.length; i++) {
			itemNameAndPrice[i] = new Label();
			itemNameAndPrice[i].setAlignment(javafx.geometry.Pos.CENTER);
			itemNameAndPrice[i].setLayoutY(6.0);
			itemNameAndPrice[i].setPrefHeight(32.0);
			itemNameAndPrice[i].setPrefWidth(225.0);
			itemNameAndPrice[i].setText(values.get(i).getName() + "  Rs." + values.get(i).getPrice());
			itemNameAndPrice[i].setStyle("-fx-background-color: pink;");
			AnchorPane.setLeftAnchor(itemNameAndPrice[i], 0.0);
			AnchorPane.setRightAnchor(itemNameAndPrice[i], 0.0);
			AnchorPane.setTopAnchor(itemNameAndPrice[i], 0.0);
			Font font = new Font(13.0);
			itemNameAndPrice[i].setFont(font);
		}
	}

	// this method sets the item iamge
	public void setItemImage(@SuppressWarnings("exports") ImageView[] itemImage) {
		for (int i = 0; i < itemImage.length; i++) {
			itemImage[i] = new ImageView();
			itemImage[i].setFitHeight(150.0);
			itemImage[i].setFitWidth(200.0);
			itemImage[i].setLayoutX(13.0);
			itemImage[i].setLayoutY(32.0);

			byte[] arr = values.get(i).getImage();
			Image image = new Image(new ByteArrayInputStream(arr));

			itemImage[i].setImage(image);
			itemImage[i].setPreserveRatio(false);

			itemImage[i].setPickOnBounds(true);
			itemImage[i].setPreserveRatio(false);
			AnchorPane.setBottomAnchor(itemImage[i], 43.0);
		}
	}

	public void setHbox(@SuppressWarnings("exports") HBox[] hbox) {
		for (int i = 0; i < hbox.length; i++) {
			hbox[i] = new HBox();
			hbox[i].setAlignment(javafx.geometry.Pos.CENTER);
			hbox[i].setLayoutX(13.0);
			hbox[i].setLayoutY(180.0);
			hbox[i].setPrefHeight(45.0);
			hbox[i].setPrefWidth(200.0);
			hbox[i].setSpacing(25.0);
			hbox[i].setStyle("-fx-background-color: pink;");
			AnchorPane.setBottomAnchor(hbox[i], 0.0);
			AnchorPane.setLeftAnchor(hbox[i], 0.0);
			AnchorPane.setRightAnchor(hbox[i], 0.0);
			AnchorPane.setTopAnchor(hbox[i], 180.0);

		}
	}

	public void setSpinner(Spinner<Integer>[] spinner) {
		for (int i = 0; i < spinner.length; i++) {
			Spinner<Integer> range = new Spinner<Integer>(0, 50, 0);
			spinner[i] = range;
			spinner[i].setEditable(true);
			spinner[i].setPrefHeight(25.0);
			spinner[i].setPrefWidth(71.0);
		}
	}

	public void setAddButton(@SuppressWarnings("exports") Button[] addButton) {
		for (int i = 0; i < addButton.length; i++) {
			addButton[i] = new Button();
			addButton[i].setAlignment(javafx.geometry.Pos.CENTER);
			addButton[i].setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
			addButton[i].setMnemonicParsing(false);
			addButton[i].setText("Add");
			addButton[i].setOnMouseClicked(handler);
			addButton[i].setId(String.valueOf(i));

		}
	}

	public void refresh() {
		for (int i = 0; i < values.size(); i++) {
			hbox[i].getChildren().addAll(addButton[i], spinner[i]);
			menuItems[i].getChildren().addAll(itemNameAndPrice[i], itemImage[i], hbox[i]);
		}
	}

	@SuppressWarnings("unchecked")
	public void initializeMenuItems() {

		PerformOperations ope = new PerformOperations();
		update = new ArrayList<InventryDAO>();

		values = ope.getValues();
		items = values.size();

		menuItems = new AnchorPane[items];
		itemNameAndPrice = new Label[items];
		itemImage = new ImageView[items];
		hbox = new HBox[items];
		spinner = new Spinner[items];
		addButton = new Button[items];

		setMenuItems(menuItems);
		setItemNameAndPrice(itemNameAndPrice);
		setItemImage(itemImage);
		setHbox(hbox);
		setAddButton(addButton);
		setSpinner(spinner);

		refresh();
	}

	@FXML
	void resetButton() {
		values.clear();
		update.clear();
		itemId.clear();
		quantity.clear();
		billItemsList.clear();
		InventoryTreeView.setItems(billItemsList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initializeMenuItems();
		// adding the items to the flowpane
		menuare.getChildren().addAll(menuItems);

	}

	@FXML
	public void makeOrderButton() {
		GrandTotal = 0;
		PerformOperations ope = new PerformOperations();

		int save = -1;

		int listSize = quantity.size();

		for (int i = 0; i < listSize; i++) {
			save = ope.updateQuantity(quantity.get(i), String.valueOf(itemId.get(i)));
		}

		if (save > 0) {
			new AlertClass().showAlert(Alert.AlertType.CONFIRMATION, "Done", "Done");
			values.clear();
			update.clear();
			itemId.clear();
			quantity.clear();
<<<<<<< HEAD:src/main/java/org/CafeManageMent/CafeManageSys/MenuTabController.java
			billItemsList.clear();
			InventoryTreeView.setItems(billItemsList);
		} else {
			new AlertClass().showAlert(Alert.AlertType.ERROR, "Something went wrong", "Some Error Occured");
		}
	}

	EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {

			int numberOfItemsSelected = 0;
			PerformOperations ope = new PerformOperations();

			List<InventryDAO> oldValues = ope.getValues(); // old values from DB

			int id = Integer.parseInt(((Button) event.getSource()).getId()); // button id

			numberOfItemsSelected = spinner[id].getValueFactory().getValue();

			// quantity to be updated
			quantity.add(String.valueOf(oldValues.get(id).getItemsSold() + numberOfItemsSelected));

			// item to be updated
			itemId.add(String.valueOf(oldValues.get(id).getId()));

			// Billing float itemTotalPrice =
			float itemTotalPrice = Integer.valueOf(oldValues.get(id).getPrice()) * numberOfItemsSelected;
			GrandTotal += itemTotalPrice;
			grandTotal.setText("GRAND TOTAL: " + String.valueOf(GrandTotal));
			

			// Setting items to bill
			billItemsList.add( new ShowBillItemsOnBill(itemNameAndPrice[id].getText(),
					 String.valueOf(itemTotalPrice),String.valueOf(numberOfItemsSelected)
					));
			
			total.setCellValueFactory(cellData -> cellData.getValue().getItemPrice());
			nameAndPrice.setCellValueFactory(cellData -> cellData.getValue().getItemName());
			qtCol.setCellValueFactory(cellData -> cellData.getValue().getItemQuantity());
			
			InventoryTreeView.setItems(billItemsList);
				
			
		}

	};
=======
//			quantityColumn.

		} else {
			new AlertClass().showAlert(Alert.AlertType.ERROR, "Something went wrong", "Some Error Occured");
		}

	}

	@FXML
	public void resetBill(@SuppressWarnings("exports") ActionEvent event) {
		showBillItemsOnBills.clear();
		values.clear();
		update.clear();
		itemId.clear();
		quantity.clear();
		priceColumn.setCellValueFactory(cellData -> cellData.getValue().getItemPrice());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().getItemName());
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getItemQuantity());
		GT.setText("GrandTotal : ");
		InventoryTreeView.setItems(showBillItemsOnBills);

	}

	// Responsive layout
>>>>>>> 2b044c68cd36c2c3415ff11a970a0c6eda41f588:src/main/java/org/CafeManageMent/CafeManageSys/MenuController.java

}
