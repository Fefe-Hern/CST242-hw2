package hw2_2;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArrayEditor {
    static boolean writeArray(String fileName, int[] arrayToSave) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arrayToSave);
            oos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("error occurred");
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ArrayEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    static int[] readArray(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            int[] arrayToSave = arrayToSave = (int[]) (ois.readObject());
            ois.close();
            return arrayToSave;
        } catch (Exception e) {
            int[] numbers = {1,2,3,4,5};
            return numbers;
        }
    }
}
