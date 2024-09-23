package GameObject.Characters;

import GameObject.GameObject;
import GameObject.Interfaces.BasicDescriptionSystem;
import GameObject.Interfaces.NamingSystem;
import GameObject.Interfaces.LVLAdaptationSystem;
import GameObject.Items.Item;

import java.util.ArrayList;

public abstract class Person extends GameObject implements NamingSystem, LVLAdaptationSystem, BasicDescriptionSystem {
    protected int hp;
    protected int maxHp;
    protected int lvl;
    protected int expCap;
    protected int exp;
    protected String name;
    protected String description;
    protected ArrayList<Item> inventory;
    protected ArrayList<Item> equipment;

    protected Person()
    {

    }

    protected class BodyPart{
        String bpName;
        int bpHP;
        double size;
        BodyPart(String n, int hp, double sZ){
            this.bpName=n;
            this.bpHP=hp;
            this.size=sZ;
        }
    }


    public ArrayList<Item> getInventory(){
        return inventory;
    }
    public void addToInventory(Item itm){
        inventory.add(itm);
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public void setName(String n){
        name = n;
    }

    @Override
    public String getDescription(){
        return description;
    }
    @Override
    public void setDescription(String d){
        description = d;
    }

    @Override
    public int getLvl(){
        return lvl;
    }
    @Override
    public void setLvl(int lvl){
        this.lvl = lvl;
    }
}

