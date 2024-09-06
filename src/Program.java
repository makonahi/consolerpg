
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        Protagonist protagonist = new Protagonist(name);
        int tempexp;
        int counter=0;
        do{
             tempexp=in.nextInt();
             Protagonist.EXPGAIN(tempexp);
             Item randweap = new Weapon();
             Printer.foundItem(randweap);
             System.out.println(counter);
             break;
        }while (tempexp!=0);
    }
}
