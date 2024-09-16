package GameObj.Characters;

import GameObj.Locations.Location;
import Printer.Printer;
import Redactor.Redactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Hero extends Person {

    private String currentLocation;
    private HashMap<String, Integer> locationKnowledge;
    private boolean isInventoryOpened=false;
    private static final Scanner in = new Scanner(System.in);

    private final ArrayList<Actions> InventoryCommands = new ArrayList<>(){{
        add(Actions.DROP_ITEM);
        add(Actions.EXPLORE_ITEM);
        add(Actions.EQUIP_ITEM);
        add(Actions.CLOSE_INVENTORY);
        add(Actions.GO_TO);
    }};
    private final ArrayList<Actions> GeneralCommands = new ArrayList<>(){{
        add(Actions.LOOK_AROUND);
        add(Actions.PICK_UP);
        add(Actions.ATTACK);
        add(Actions.OPEN_INVENTORY);
    }};

    private enum Actions{
        LOOK_AROUND("X", "осмотреться"),
        PICK_UP("P", "подобрать"),
        ATTACK("A", "атаковать"),
        OPEN_INVENTORY("I", "инвентарь"),
        GO_TO("M", "идти"),
        DROP_ITEM("D", "выкинуть"),
        EXPLORE_ITEM("E", "осмотреть предмет"),
        EQUIP_ITEM("Q", "экипировать предмет"),
        CLOSE_INVENTORY("C", "закрыть инвентарь");

        private final String actionShortcut;
        private final String actionName;

        Actions(String actionShortcut, String actionName){
            this.actionShortcut=actionShortcut;
            this.actionName=actionName;
        }
        public String getActionShortcut(){
            return actionShortcut;
        }
        public String getActionName(){
            return actionName;
        }
        public String getFullHint(){
            return String.format("[%s] - %s", actionShortcut, actionName);
        }
    }

    public Hero(String n) {
        this.name = n;
        this.HP=16;
        this.maximum_HP = 16;
        this.LVL=1;
        this.EXP_Capacity = 11;
        this.EXP=0;
    }

    private void EXPToLVL(){
        while (EXP - EXP_Capacity >= 0) {
            EXP -= EXP_Capacity;
            LVL++;
            maximum_HP = 16 + LVL * 2;
            HP = maximum_HP;
            EXP_Capacity = 11 * (int) (Math.pow(1.6, LVL));
        }
        Printer.lvlUp(name, LVL, maximum_HP);
    }

    public void gainEXP(int exp) {
        EXP += exp;
        if (EXP >= EXP_Capacity)
            EXPToLVL();
        Printer.expGain(exp, EXP, EXP_Capacity);
    }

    private Actions convertInput(String input){
        for (Actions act : Actions.values())
            if (Objects.equals(input.toUpperCase(), act.actionShortcut))
                return act;
        return null;
    }

    public void processAction(){
        Actions act = convertInput(in.nextLine());
        if (Objects.equals(act,null))
            act=convertInput(in.nextLine());
        else if (!isInventoryOpened&&!GeneralCommands.contains(act))
            act=convertInput(in.nextLine());
        else if (isInventoryOpened&&!InventoryCommands.contains(act))
            act=convertInput(in.nextLine());
        switch (act){
            case Actions.LOOK_AROUND:
            {
                break;
            }
        }
    }

    public boolean isInventoryOpened() {
        return isInventoryOpened;
    }

    public void setInventoryOpened(boolean inventoryOpened) {
        isInventoryOpened = inventoryOpened;
    }

}
