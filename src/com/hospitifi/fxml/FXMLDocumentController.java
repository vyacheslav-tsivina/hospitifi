package com.hospitifi.fxml;

import com.hospitifi.model.Room;
import com.hospitifi.model.User;
import com.hospitifi.service.EmployeeService;
import com.hospitifi.service.OccupationService;
import com.hospitifi.service.RoomService;
import com.hospitifi.service.UserService;
import com.hospitifi.util.ServiceFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

	//Login & general section
	@FXML
	Button loginButton;
	@FXML
	TextField login;
	@FXML
	PasswordField password;
	@FXML
	Label invalidCombination;
	@FXML
	Button logoutButton;

	private UserService userService = ServiceFactory.getUserService();
	private RoomService roomService = ServiceFactory.getRoomService();
	private EmployeeService employeeService = ServiceFactory.getEmployeeService();
	private OccupationService occupationService = ServiceFactory.getOccupationService();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//any code here will be called every time scene changes
		//(after all @FXML annotated members have been injected)
		if(userService.getCurrentUserRole() == null) {
			configureLogin();
		}
	}

	public static Parent load(final URL url, final ResourceBundle resBundle, final Object controller)
			throws IOException {
		Objects.requireNonNull(url);
		Objects.requireNonNull(controller);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		loader.setResources(resBundle);

		//controller is synchronized between fxml files with setControllerFactory
		loader.setControllerFactory(controllerClass -> {
			if (controllerClass != null && !controllerClass.isInstance(controller)) {
				throw new IllegalArgumentException("Invalid controller instance, expecting instance of class '" +
						controllerClass.getName() + "'");
			}
			return controller;
		});
		return (Parent) loader.load();
	}

	public void configureLogin(){

		// "invalid login/password" message will be invisible by default
		if (invalidCombination != null) {
			invalidCombination.setVisible(false);
		}

		// attempt login when enter key is pressed and while login TextField has focus
		login.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent ke)
			{
				if (ke.getCode().equals(KeyCode.ENTER))
				{
					try {
						handleLoginButton(new ActionEvent());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		// attempt login when enter key is pressed and while PasswordField has focus
		password.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent ke)
			{
				if (ke.getCode().equals(KeyCode.ENTER))
				{
					try {
						handleLoginButton(new ActionEvent());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@FXML
	private void handleLoginButton(ActionEvent event) throws IOException{
		/*
		 * If login/pass are valid, change scene to user's corresponding menu.
		 * If invalid, display the error message.
		 */
		String log = login.getText();
		String pass = password.getText();

		if ((log != null && pass != null ) && userService.authenticateUser(log, pass)) {

			Stage stage = (Stage) loginButton.getScene().getWindow();  //get reference to button's stage  
			String role = userService.getCurrentUserRole();
			Parent root;
			if (role.equalsIgnoreCase("admin")) {
				root = load(getClass().getResource("AdminMenu.fxml"), null, this);
				configureAdmin();
			}
			else if (role.equalsIgnoreCase("manager")) {
				root = load(getClass().getResource("ManagerMenu.fxml"), null, this);
				configureManager();
			}
			else {  
				root = load(getClass().getResource("ReceptionistMenu.fxml"), null, this);
				configureReceptionist();
			}
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else {
			invalidCombination.setVisible(true);
		}
	}

	@FXML
	private void handleLogoutButton(ActionEvent event) throws IOException{
		Stage stage = (Stage) logoutButton.getScene().getWindow();  
		Parent root = load(getClass().getResource("Login.fxml"), null, this);  
		configureLogin();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		userService.logOut();
	}







	//AdminMenu code section
	@FXML
	Button newUser;
	@FXML
	Button deleteUser;
	@FXML
	Button editUser;
	@FXML
	Button newRoom;
	@FXML
	Button deleteRoom;
	@FXML
	Button editRoom;
	@FXML
	SplitPane userPane;
	@FXML
	SplitPane roomPane;
	@FXML
	TableView<User> userTable;
	@FXML
	TableView<Room> roomTable;
	@FXML
	TableColumn<User, Long> userIdCol;
	@FXML
	TableColumn<User, String> userLoginCol;
	@FXML
	TableColumn<User, String> userPasswordCol;
	@FXML
	TableColumn<User, String> userRoleCol;
	@FXML
	TableColumn<Room, Long> roomIdCol;
	@FXML
	TableColumn<Room, String> roomNumberCol;
	@FXML
	TableColumn<Room, Integer> roomFloorCol;
	@FXML
	TableColumn<Room, Integer> roomBedsCol;
	@FXML
	TableColumn<Room, Integer> roomBedTypeCol;
	@FXML
	TableColumn<Room, Boolean> roomSafeCol;
	@FXML
	TableColumn<Room, Boolean> roomBathCol;
	@FXML
	TableColumn<Room, Integer> roomRateCategoryCol;
	@FXML
	TextField idAdminEdit;
	@FXML
	TextField loginAdminEdit;
	@FXML
	TextField passwordAdminEdit;
	@FXML
	ChoiceBox<String> roleAdminEditChoiceBox = new ChoiceBox<>();
	@FXML
	RadioButton adminUsersRadioButton;
	@FXML
	RadioButton adminRoomsRadioButton;
	@FXML
	Label adminDetailId;
	@FXML
	Label adminDetailLogin;
	@FXML
	Label adminDetailPassword;
	@FXML
	Label adminDetailRole;
	@FXML
	Label adminMessage;
	@FXML
	GridPane adminNewEditUserGridPane;
	@FXML
	Button adminUserOkButton;
	@FXML
	Button adminUserCancelButton;
	
	private ObservableList<User> userData;

	private void configureAdmin() {  //initial setup for admin menu

		//ToggleGroup only allows one RadioButton to be selected at a time
		ToggleGroup tg = new ToggleGroup();
		adminUsersRadioButton.setToggleGroup(tg);
		adminRoomsRadioButton.setToggleGroup(tg);
		adminUsersRadioButton.setSelected(true); //Users RadioButton selected by default
		adminUsersRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				userPane.setVisible(true);
				roomPane.setVisible(false);
				
			}
		});
		adminRoomsRadioButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				userPane.setVisible(false);
				roomPane.setVisible(true);
			}
		});

		roleAdminEditChoiceBox.setItems(FXCollections.observableArrayList("admin","manager","receptionist"));
		
		showUserDetails(null); //clear user details
		adminNewEditUserGridPane.setVisible(false); //user form hidden
		adminUserOkButton.setVisible(false);
		adminUserCancelButton.setVisible(false);
		adminMessage.setText(""); //no message initially
		
		userTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		userTable.getSelectionModel().selectedItemProperty().addListener((observable) -> hideAdminNewEditUser());
		userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showUserDetails(newValue));
		userIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		userLoginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
		userPasswordCol.setCellValueFactory(new PropertyValueFactory<>("passwordHash"));
		userRoleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
		
		userData = FXCollections.observableArrayList();
		userData.addAll(userService.getAll());
		userTable.setItems(userData);
	}
	
	private void hideAdminNewEditUser(){
		adminNewEditUserGridPane.setVisible(false); //user form hidden
		adminUserOkButton.setVisible(false);
		adminUserCancelButton.setVisible(false);
	}
	
	private void clearAdminUsersTextFields(){
		idAdminEdit.clear();
		loginAdminEdit.clear();
		passwordAdminEdit.clear();
	}
	
	private void showUserDetails (User user){
		if (user != null) {
	        // Fill the labels with info from the user object.
			adminDetailId.setText(Long.toString(user.getId()));
			adminDetailLogin.setText(user.getLogin());
			adminDetailPassword.setText(user.getPasswordHash());
			adminDetailRole.setText(user.getRole());

	    } else {
	        // Person is null, remove all the text.
	    	adminDetailId.setText("");
	    	adminDetailLogin.setText("");
	    	adminDetailPassword.setText("");
	    	adminDetailRole.setText("");
	    }
	}
	
	@FXML
	public void newUserClicked(ActionEvent event) {
		userTable.getSelectionModel().clearSelection();
		
		clearAdminUsersTextFields();
		
		adminNewEditUserGridPane.setVisible(true);
		adminUserOkButton.setVisible(true);
		adminUserCancelButton.setVisible(true);
	}
	
	@FXML
	public void deleteUserClicked(ActionEvent event) {
		int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
	    if (selectedIndex >= 0) {  //something is selected

			userService.delete(userTable.getSelectionModel().getSelectedItem().getId());
	    	userTable.getItems().remove(selectedIndex);
			//userData.remove(userTable.getSelectionModel().getSelectedItem());
	    	userTable.getSelectionModel().clearSelection();
	    } else {  // nothing selected
	    	clearAdminUsersTextFields();
			hideAdminNewEditUser();
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setGraphic(null);
	        alert.initOwner(adminMessage.getScene().getWindow());
	        alert.setTitle("Delete - No Selection");
	        alert.setHeaderText("No User Selected");
	        alert.setContentText("Please select a user in the table to delete.");
	        alert.showAndWait();
	    }
	}
	
	@FXML
	public void editUserClicked(ActionEvent event) {
		int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {  //something is selected

			idAdminEdit.setText(Long.toString(userTable.getSelectionModel().getSelectedItem().getId()));
			loginAdminEdit.setText(userTable.getSelectionModel().getSelectedItem().getLogin());
			passwordAdminEdit.setText(userTable.getSelectionModel().getSelectedItem().getPasswordHash());
			roleAdminEditChoiceBox.setValue(userTable.getSelectionModel().getSelectedItem().getRole());
			
			adminNewEditUserGridPane.setVisible(true);
			adminUserOkButton.setVisible(true);
			adminUserCancelButton.setVisible(true);
			
	    } else {  // nothing selected
	    	clearAdminUsersTextFields();
			hideAdminNewEditUser();
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(adminMessage.getScene().getWindow());
	        alert.setTitle("Edit - No Selection");
	        alert.setHeaderText("No User Selected");
	        alert.setContentText("Please select a user in the table to edit.");
	        alert.showAndWait();
	    }
	}
	
	@FXML
	public void adminNewEditUserCancelButtonPressed(ActionEvent event) {
		clearAdminUsersTextFields();
		hideAdminNewEditUser();
	}
	
	@FXML
	public void adminNewEditUserOkButtonPressed(ActionEvent event) {
		int index = userTable.getSelectionModel().getSelectedIndex();
		if (index < 0 ) { //no cell selected, which results when "New" is pressed
			if (idAdminEdit == null || loginAdminEdit == null || passwordAdminEdit == null || roleAdminEditChoiceBox.getValue() == null) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.initOwner(adminMessage.getScene().getWindow());
		        alert.setTitle("Error");
		        alert.setHeaderText(null);
		        alert.setContentText("Could not create because one or more entries are blank.");
		        alert.showAndWait();
		        return;
			}
			for (User user : userData) {
				if(user.getId() == Long.valueOf(idAdminEdit.getText())) {
			        Alert alert = new Alert(AlertType.ERROR);
			        alert.initOwner(adminMessage.getScene().getWindow());
			        alert.setTitle("Error");
			        alert.setHeaderText("Could not create because this ID already exists.");
			        alert.setContentText("Please enter a unique ID.");
			        alert.showAndWait();
			        return;
				}
				else if(user.getLogin().equals(loginAdminEdit.getText())) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.initOwner(adminMessage.getScene().getWindow());
			        alert.setTitle("Error");
			        alert.setHeaderText("Could not create because this login name already exists.");
			        alert.setContentText("Please enter a unique login name.");
			        alert.showAndWait();
			        return;
				}
			}
			User user = new User(Long.valueOf(idAdminEdit.getText()), loginAdminEdit.getText(), null, 
					passwordAdminEdit.getText(), roleAdminEditChoiceBox.getSelectionModel().getSelectedItem());
			userService.save(user);
			
			userTable.getItems().add(user);
			userTable.getSelectionModel().clearSelection();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("User was successfully added.");
			alert.showAndWait();
		}
		else { //"Edit" button was pressed
			if (idAdminEdit == null || loginAdminEdit == null || passwordAdminEdit == null || roleAdminEditChoiceBox.getValue() == null) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.initOwner(adminMessage.getScene().getWindow());
		        alert.setTitle("Error");
		        alert.setHeaderText(null);
		        alert.setContentText("Could not create because one or more entries are blank.");
		        alert.showAndWait();
		        return;
			}
			User user = new User(Long.valueOf(idAdminEdit.getText()), loginAdminEdit.getText(), null, 
					passwordAdminEdit.getText(), roleAdminEditChoiceBox.getSelectionModel().getSelectedItem());
			userService.update(user);
			
			userTable.getItems().add(index, user);
			userTable.getItems().remove(index + 1);
			userTable.getSelectionModel().clearSelection();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("User was successfully edited.");
			alert.showAndWait();
		}
	}
	
	






	//ManagerMenu code section
	private void configureManager() {
		//To be written
	}

	//ReceptionistMenu code section
	private void configureReceptionist() {
		//To be written
	}
}
