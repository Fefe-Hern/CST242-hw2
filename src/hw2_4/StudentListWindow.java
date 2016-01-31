package hw2_4;

import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StudentListWindow extends Application {
    final int NUMBER_FOR_ID_FIELD = 2;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Label firstName = new Label("First Name: ");
        Label lastName = new Label("Last Name: ");
        Label id = new Label("ID: ");
        Label phone = new Label("Phone #: ");
        Label address = new Label("Address: ");
        Label major = new Label("Major: ");
        Label gpa = new Label("GPA: ");
        TextField[] textFieldArray = new TextField[7];
        /*TextField firstNameField = new TextField(); 0
        TextField lastNameField = new TextField(); 1
        TextField idField = new TextField(); 2 <- important key field
        TextField phoneField = new TextField(); 3
        TextField addressField = new TextField(); 4
        TextField majorField = new TextField(); 5
        TextField gpaField = new TextField(); 6*/
        
        grid.add(firstName, 0, 0);
        grid.add(lastName, 0, 1);
        grid.add(id, 0, 2);
        grid.add(phone, 0, 3);
        grid.add(address, 0, 4);
        grid.add(major, 0, 5);
        grid.add(gpa, 0, 6);
        for (int i = 0; i < textFieldArray.length; i++) {
            textFieldArray[i] = new TextField();
            grid.add(textFieldArray[i], 1, i);
        }
        /*grid.add(firstNameField, 1, 0);
        grid.add(lastNameField, 1, 1);
        grid.add(idField, 1, 2);
        grid.add(phoneField, 1, 3);
        grid.add(addressField, 1, 4);
        grid.add(majorField, 1, 5);
        grid.add(gpaField, 1, 6);*/
        
        Button addButton = new Button("ADD");
        addButton.setOnAction((ActionEvent event) -> {
            boolean continueFlag = true;
            for (int i = 0; i < textFieldArray.length; i++) {
                //Check to see if any fields are empty, or ID isn't a number.
                if(textFieldArray[i].getText().equals("") == true) {
                    continueFlag = false;
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("A textfield is empty.");
                    alert.setContentText("Textfield " + i + " is empty.");
                    alert.showAndWait();
                    i = textFieldArray.length;
                }
                if(i == NUMBER_FOR_ID_FIELD) {
                    try {
                        int numberTest = Integer.parseInt(textFieldArray[i].getText());
                    } catch(NumberFormatException ex) {
                        continueFlag = false;
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setHeaderText("ID isn't number.");
                        alert.setContentText("The ID: " + 
                                textFieldArray[i].getText() + " is not a number.");
                        alert.showAndWait();
                    }
                }
            }
            // If all fields are full and ID is a number, convert into class
            // and store in hashmap
            if(continueFlag) {
                Student student = new Student(
                        textFieldArray[0].getText(), // First Name
                        textFieldArray[1].getText(), // Last Name
                        Integer.parseInt(textFieldArray[2].getText()), // ID
                        textFieldArray[3].getText(), // Phone
                        textFieldArray[4].getText(), // Address
                        textFieldArray[5].getText(), // Major
                        textFieldArray[6].getText()); // GPA
                
                if(StudentData.add(student)) { // Reset textfields
                    for (int i = 0; i < textFieldArray.length; i++) {
                        textFieldArray[i].setText("");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("ID Taken");
                    alert.setContentText("This student was not added to the database.");
                    alert.showAndWait();
                }
            }
        });
        
        Button saveButton = new Button("SAVE ADDED");
        saveButton.setOnAction((ActionEvent event) -> {
            if (StudentData.checkIfFileExists()) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Overwrite File?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (StudentData.saveToFile()) {
                        Alert info = new Alert(Alert.AlertType.INFORMATION);
                        info.setContentText("Data saved to StudentData.dat");
                        info.showAndWait();
                    } else {
                        Alert warning = new Alert(Alert.AlertType.WARNING);
                        warning.setHeaderText("Data not saved");
                        warning.setContentText("The data was not saved.");
                        warning.showAndWait();
                    }
                } else {
                    // donothing, exit save confirmation.
                }
            } else { // No file exists, just create and save.
                if (StudentData.saveToFile()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Data saved to StudentData.dat");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Data not saved");
                    alert.setContentText("The data was not saved.");
                    alert.showAndWait();
                }
            }
        });
        
        Button loadButton = new Button("LOAD");
        loadButton.setOnAction((ActionEvent event) -> {
            if(StudentData.loadHashMap()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Data loaded from StudentData.dat");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Data not loaded");
                alert.setContentText("No data was loaded.");
                alert.showAndWait();
            }
        });
        
        grid.add(addButton, 0, 7);
        grid.add(saveButton, 1, 7);
        grid.add(loadButton, 2, 7);
        
        //New Stuff. Note that Searching and Displaying are redundant, and one had
        // been omitted.
        Button searchButton = new Button("Search ID");
        Button deleteButton = new Button("Delete ID");
        //Button displayButton = new Button("Display ID");
        searchButton.setOnAction((ActionEvent event) -> {
            try {
                int numberTest = Integer.parseInt(textFieldArray[NUMBER_FOR_ID_FIELD].getText());
                if (StudentData.idExists(numberTest)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(StudentData.getStudent(numberTest).toString());
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("The ID doesn't exist.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("ID isn't number.");
                alert.setContentText("The ID: "
                        + textFieldArray[NUMBER_FOR_ID_FIELD].getText() + " is not a number.");
                alert.showAndWait();
            }
        });
        
        deleteButton.setOnAction((ActionEvent event) -> {
            try {
                int numberTest = Integer.parseInt(textFieldArray[NUMBER_FOR_ID_FIELD].getText());
                if (StudentData.idExists(numberTest)) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setContentText("Delete student with ID " + numberTest + "?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        Alert deleteInfo = new Alert(AlertType.INFORMATION);
                        deleteInfo.setContentText(
                                StudentData.deleteStudent(numberTest).getFirstName() +
                                        " has been deleted.");
                        deleteInfo.showAndWait();
                    } else {
                        // donothing, return.
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("The ID " + textFieldArray[NUMBER_FOR_ID_FIELD] + " doesn't exist.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("ID isn't number.");
                alert.setContentText("The ID: "
                        + textFieldArray[NUMBER_FOR_ID_FIELD].getText() + " is not a number.");
                alert.showAndWait();
            }
        });
        
        grid.add(searchButton, 0, 8);
        grid.add(deleteButton, 1, 8);
        //grid.add(displayButton, 2, 8);
        
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
