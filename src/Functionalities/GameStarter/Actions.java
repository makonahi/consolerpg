package Functionalities.GameStarter;

public enum Actions{
    LOOK_AROUND("X", "осмотреться"),
    PICK_UP("P", "подобрать предмет"),
    ATTACK("A", "атаковать"),
    OPEN_INVENTORY("I", "инвентарь"),
    GO_TO("M", "подойти к..."),
    GET_IN("G", ""),
    GET_AWAY("Y", "вернуться"),
    GET_UP("V", "встать"),
    GET_HISTORY_OF_INVESTIGATION("H", "вывести историю исследования"),
    DROP_ITEM("D", "выкинуть"),
    EXPLORE_ITEM("E", "осмотреть предмет"),
    EQUIP_ITEM("Q", "экипировать предмет"),
    EXIT("O", "выйти из игры"),
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
        return String.format("\u001B[32m[%s] - %s\u001B[0m", actionShortcut, actionName);
    }
}