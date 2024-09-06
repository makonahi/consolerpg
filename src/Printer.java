public class Printer {

    public static void lvlUp(String name, int lvl, int maxhp){
        System.out.printf("%s достигает уровня %d! Максимальное здоровье теперь: %d.\n", name, lvl, maxhp);
    }

    public static void expGain(int exp){
        System.out.printf("Вы получили %d опыта.\n", exp);
    }

    public static void expToUp(int exp, int lvl){
        System.out.printf("Опыта для следующего уровня: %d/%d.\n", exp, (int)(Math.sqrt(lvl*100)*1.8f));
    }

    public static void foundItem(Item item){
        System.out.printf("Вы находите предмет: %s.\n", item.name);
    }
}
