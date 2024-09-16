package GameObj.Characters;

import GameObj.GameObject;
import GameObj.Interfaces.BasicDescriptionSystem;
import GameObj.Interfaces.NamingSystem;
import GameObj.Interfaces.LVLAdaptationSystem;
import GameObj.Items.Item;

import java.util.ArrayList;

public abstract class Person extends GameObject implements NamingSystem, LVLAdaptationSystem, BasicDescriptionSystem {
    protected int HP;
    protected int maximum_HP;
    protected int LVL;
    protected int EXP_Capacity;
    protected int EXP;
    protected String name;
    protected String description;
    protected ArrayList<Item> inventory;
    protected ArrayList<Item> equipment;
    protected int carryWeight;
    protected int evasion;

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
    public int getLVL(){
        return LVL;
    }
    @Override
    public void setLVL(int lvl){
        LVL = lvl;
    }
}

