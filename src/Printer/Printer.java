package Printer;

import Items.Weapon;

public class Printer {

    public static void lvlUp(String name, int lvl, int maxhp){
        System.out.printf("%s достигает уровня %d! Максимальное здоровье теперь: %d.\n", name, lvl, maxhp);
    }

    public static void expGain(int exp, int allExp, int lvlcap){
        System.out.printf("Вы получили %d опыта. Опыта для следующего уровня: %d/%d.\n", exp, allExp, lvlcap);
    }

    public static void foundItem(Weapon wpn){
        System.out.printf("Вы находите оружие: %s { LVL:%s;DMG:%s;WGH:%.2f;PRC:%.2f }. ",
                wpn.getName(), wpn.getLVL(), wpn.getDMG(), wpn.getWeight(), wpn.getPrice());
        if (wpn.getSpheres()==null)
        {
            System.out.println("Этот предмет не связан ни с какими сферами.");
            return;
        }
        if (wpn.getSpheres().length==1)
        {
            System.out.printf("Этот предмет связан с единственной сферой: %s.\n", wpn.getSpheres()[0].toString());
            return;
        }
        StringBuilder spheres = new StringBuilder();
        for (int i=0;i<wpn.getSpheres().length;i++)
            spheres.append(wpn.getSpheres()[i].toString()).append(", ");
        spheres.delete(spheres.length()-2, spheres.length());
        System.out.printf("Этот предмет связан со сферами: "+spheres+".\n");
    }
}
