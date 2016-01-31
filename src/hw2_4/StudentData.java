package hw2_4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentData {
    private static HashMap<Integer, Student> studentMap = new HashMap<>();
    private static File file = new File("StudentData.dat");
    
    static boolean add(Student student) {
        if(studentMap.get(student.getId()) != null) { // Key already exists
            return false;
        } else { // Key doesnt exist
            studentMap.put(student.getId(), student);
            return true;
        }
    }
    
    static boolean saveToFile() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(studentMap);
            oos.close();
            return true;
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return false;
    }

    static boolean loadHashMap() {
        try {
            if(file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                studentMap = (HashMap<Integer, Student>) ois.readObject();
            } else {
                return false;
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return true;
    }

    static boolean checkIfFileExists() {
        if(file.exists()) return true;
        else return false;
    }

    static boolean idExists(int key) {
        if(studentMap.containsKey(key)) {
            return true;
        } else {
            return false;
        }
    }

    static Student getStudent(int key) {
        return studentMap.get(key).DeepCopy();
    }
    
    static Student deleteStudent(int key) {
        return studentMap.remove(key);
    }
    
}
