package hw2_1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class WindowPane extends Application { 

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        Label name = new Label("Name: ");
        Label id = new Label("ID: ");
        TextField nameField = new TextField();
        TextField idField = new TextField();
        TextArea area = new TextArea();
        
        Button add = new Button("ADD");
        add.setOnAction((ActionEvent event) -> {
            if(FileHandler.saveInput(nameField.getText(), idField.getText())) {
                area.setText("Info added.\nName:" + nameField.getText() + "\nID: " + idField.getText());
            }
        });
        
        Button display = new Button("Display");
        display.setOnAction((ActionEvent event) -> {
            String message = FileHandler.readOutput();
            area.setText(message);
        });
        
        
        grid.add(name, 1, 1);
        grid.add(id, 1, 2);
        grid.add(nameField, 2, 1);
        grid.add(idField, 2, 2);
        grid.add(add, 3, 2);
        grid.add(display, 1, 3);
        grid.add(area, 3, 3);
        
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
