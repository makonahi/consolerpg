package GameObject.Characters;

import Functionalities.Printer.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.System.out;

public class Hero extends Person {

    private String currentLocation;
    private HashMap<String, ArrayList<Integer>> locationKnowledge;
    private static final Scanner in = new Scanner(System.in);
    private int mana;
    private int maxMana;




    public Hero() {
        this.hp = 16;
        this.maxHp = 16;
        this.mana = 4;
        this.maxMana = 4;
        this.lvl = 0;
        this.expCap = 11;
        this.exp = 0;
        this.description="Это вы.";
        this.inventory=new ArrayList<>();
        this.equipment=new ArrayList<>();
        this.locationKnowledge=new HashMap<>();
    }

    private void EXPToLVL(){
        while (exp - expCap >= 0) {
            exp -= expCap;
            lvl++;
            maxHp = 16 + lvl * 2;
            hp = maxHp;
            expCap = 11 * (int) (Math.pow(1.6, lvl+1));
        }
        out.printf("\u001B[96m%s\u001B[0m достигает уровня \u001B[93m%d\u001B[0m! " +
                "\u001B[91mМаксимальное здоровье\u001B[0m теперь: \u001B[91m%d\u001B[0m.\n", name, lvl, maxHp);
    }

    public void gainEXP(int exp) {
        this.exp += exp;
        if (this.exp >= expCap){
            out.printf("Вы получили \u001B[96m%d опыта\u001B[0m.\n", exp);
            EXPToLVL();
            out.printf("\u001B[96mОпыта\u001B[0m для следующего уровня: \u001B[96m%d/%d\u001B[0m.\n", this.exp, expCap);
        }
        else
            out.printf("Вы получили \u001B[96m%d опыта\u001B[0m. " +
                        "\u001B[96mОпыта\u001B[0m для следующего уровня: \u001B[96m%d/%d\u001B[0m.\n",
                    exp, this.exp, expCap);
    }

    public ArrayList<Integer> getLocationKnowledge(String loc) {
        return locationKnowledge.get(loc);
    }

    public void addLocationKnowledge(String newLoc, int knowledgeLevel) {
        locationKnowledge.computeIfAbsent(newLoc, k -> new ArrayList<>()).add(knowledgeLevel);
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public String toString(){
        return String.format("\u001B[96m%s\u001B[0m, LVL:%s, HP:\u001B[91m%s/%s\u001B[0m, MN:\u001B[94m%s/%s\u001B[0m, L:%s",
                name, lvl, maxHp, hp, maxMana, mana, currentLocation);
    }

}
