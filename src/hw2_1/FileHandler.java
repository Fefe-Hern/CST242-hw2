package hw2_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;

public class FileHandler {
    static boolean saveInput(String name, String id) {
        if(name.isEmpty() || id.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty input");
            alert.setContentText("Error: One of the fields left empty.");
            alert.showAndWait();
            return false;
        }
        try {
            int idNum = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("NonNumber");
            alert.setContentText("Error: ID isn't an integer.");
            alert.showAndWait();
            return false;
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Null");
            alert.setContentText("Error: Null Pointer Exception.");
            alert.showAndWait();
            return false;
        }
        try {
            FileWriter fw = new FileWriter("NameId.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            /*
            bw.newLine();
            bw.write("Name: " + name);
            bw.newLine();
            bw.write("ID: " + id);
            bw.newLine();
            bw.close();
*/
            pw.println("Name: " + name + "\nID: " + id + "\n");
            pw.close();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FileWriter");
            alert.setContentText("Error writing to file.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    static String readOutput() {
        String message = "";
        String flag = "";
        try {
            FileReader fr = new FileReader("NameId.txt");
            BufferedReader br = new BufferedReader(fr);
            while(flag != null) {
                message = message + flag + "\n";
                flag = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
}
