package main.java.com;

import java.io.*;

/**
 * App
 */
public class App {
    public static void main(String[] args) {
        Hero elf = new Hero(50, "Efl", new String[]{"Bow", "Dager"});
        Hero dwarf = new Hero(74, "Dwarf", new String[]{"Axe", "Hammer"});
        Hero human = new Hero(30, "Human", new String[]{"Sword", "Bow", "Spear"});

        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Heros.ser"));
            os.writeObject(elf);
            os.writeObject(dwarf);
            os.writeObject(human);
            os.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("Heros.ser"));
            //Object serializedObject = is.readObject();
            //elf = (Hero) serializedObject;
            //serializedObject = is.readObject();
            //dwarf = (Hero) serializedObject;
            //serializedObject = is.readObject();
            //human = (Hero) serializedObject;
            elf = (Hero) is.readObject(); 
            dwarf = (Hero) is.readObject();
            human = (Hero) is.readObject();
            is.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(elf.toString());
        System.out.println(dwarf.toString());
        System.out.println(human.toString());
    }
    
}
