package main.java.com;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Pound implements Serializable{
    private transient Duck duck = new Duck();

    public static void main(String[] args) {
        Pound pound = new Pound();
        try {
            FileOutputStream fos = new FileOutputStream("Pound.ser");
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(pound);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    
}
