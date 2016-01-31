package hw2_2;

import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ArrayEditWindow extends Application {    
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        Label instructions = new Label("Enter a series of numbers with a space inbetween.");
        grid.add(instructions, 0, 0);
        TextField arrayText = new TextField();
        grid.add(arrayText, 0, 1);
        
        Button saveToFile = new Button("Save to File");
        saveToFile.setOnAction((ActionEvent event) -> {
            TextInputDialog dialog = new TextInputDialog("filename");
            dialog.setTitle("Input File Name");
            dialog.setContentText("Please enter the name of your file:");
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()) {
                prepareToSaveText(result, arrayText.getText());
            }
            arrayText.setText("");
        });
        
        
        Button readFromFile = new Button("Read from File");
        readFromFile.setOnAction((ActionEvent event) -> {
            TextInputDialog dialog = new TextInputDialog("filename");
            dialog.setTitle("Input File Name");
            dialog.setContentText("Please enter the name of your file:");
            Optional<String> result = dialog.showAndWait();
            
            if(result.isPresent()) {
                arrayText.setText("");
                int[] newArray = ArrayEditor.readArray(result.get());
                    for (int i = 0; i < newArray.length; i++) {
                        arrayText.appendText(newArray[i] + " ");
                    }
            }
        });
        
        grid.add(saveToFile, 0, 2);
        grid.add(readFromFile, 0, 3);
        
        Scene scene = new Scene(grid, 350, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void prepareToSaveText(Optional<String> result, String text) {
        String fileName = result.get();
        String[] items = text.split(" ");
        int[] results = new int[items.length];
        for (int i = 0; i < items.length; i++) {
            results[i] = Integer.parseInt(items[i]);
        }
        ArrayEditor.writeArray(fileName, results);
    }

}
