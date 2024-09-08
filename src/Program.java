
import Items.Weapon;
import Person.Hero;
import Printer.Printer;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        Hero protagonist = new Hero(name);
        int tempexp;
        int counter=0;
        do{
             tempexp=in.nextInt();
             Hero.EXPGAIN(tempexp);
             Weapon randweap = new Weapon(protagonist.getLVL());
             Printer.foundItem(randweap);
        }while (tempexp!=0);
    }
}
