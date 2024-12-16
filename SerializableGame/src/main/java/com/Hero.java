package main.java.com;

import java.io.*;
import java.util.*;

public class Hero implements Serializable {
    public static final long serialVersionUID = 6048672105580730642L;
    private int power;
    private String heroType;
    private String[]  weapons;

    public Hero(int power, String heroType, String[] weapons) {
        this.power = power;
        this.heroType = heroType;
        this.weapons = weapons;
    }

    public int getPower() {
        return power;
    }

    public String getHeroType() {
        return heroType;
    }

    public String getWapons() {
        return Arrays.toString(weapons);
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder = strBuilder.append(heroType).append(", ").append(power);
        for (String weapon : weapons) 
            strBuilder.append(", ").append(weapon);
        return strBuilder.toString();
        
    }
    
}
