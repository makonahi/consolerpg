package Person;

import Items.Item;

public abstract class Person {
    protected static int HP=16;
    protected static int MAXHP=16;
    protected static int LVL=1;
    protected static int LVLCAP=11;
    protected static int EXP=0;
    protected static String name;
    protected static Item[] inventory;
    protected static Item[] equipment;
    protected static int carryWeight;
    protected static int evasion;
    protected enum Attributes {

    }

    protected Person(String n)
    {
        name = n;
        inventory = new Item[12];
        equipment = new Item[6];
        BodyPart[] body;
    }

    protected class BodyPart{
        private String bpName;
        private int bpHP;
        private double size;
        BodyPart(String n, int hp, double sZ){
            this.bpName=n;
            this.bpHP=hp;
            this.size=sZ;
        }
    }
    public int getLVL(){return LVL;}
}

