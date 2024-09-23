package GameObject.Locations;

import GameObject.Characters.NotPlayableObjects.Character;
import GameObject.Characters.NotPlayableObjects.Monsters.Monster;
import GameObject.GameObject;
import GameObject.Interfaces.ComplexDescriptionSystem;
import GameObject.Interfaces.NamingSystem;
import GameObject.Items.Item;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Location.class, name = "location"),
        @JsonSubTypes.Type(value = Location.Door.class, name = "location.door")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location extends GameObject implements ComplexDescriptionSystem, NamingSystem {

    //Nameable
    @JsonProperty("name")
    protected String name;
    //уровень знания о локации связан с максимально доступным подробным описанием
    @JsonProperty("descriptions")
    private HashMap<Integer, String> descriptions;
    @JsonProperty("firstVisitExp")
    private int firstVisitExp;
    @JsonProperty("specialTags")
    private ArrayList<String> specialTags;
    //уровень знания о связях с другими местами также связан с максимально доступным уровнем знаний
    @JsonProperty("connections")
    private HashMap<Integer, String> connections;
    @JsonProperty("subLocations")
    private HashMap<Integer, Door> subLocations;
    @JsonProperty("items")
    private HashMap<Integer, Item> items;
    @JsonProperty("monsters")
    private ArrayList<Monster> monsters;
    @JsonProperty("npcs")
    private ArrayList<Character> npcs;

    public Location(){
        connections=new HashMap<>();
        subLocations=new HashMap<>();
        items=new HashMap<>();
        descriptions=new HashMap<>();
        monsters=new ArrayList<>();
        npcs=new ArrayList<>();
    }

    //FIXME просто ужасная реализация, максимально неоптимизирован
    public void removeItem(Item item) {
        for (Map.Entry<Integer, Item> entry :  items.entrySet()) {
            if (entry.getValue().getName()==item.getName())
                items.remove(entry.getKey());
        }
    }

    public static class Door implements Serializable {
        @JsonProperty("subLocationFileName")
        private String subLocationFileName;
        @JsonProperty("doorToLocationName")
        private String doorToLocationName;
        @JsonProperty("doorActionName")
        private String doorActionName;
        @JsonProperty("shortDescription")
        private String shortDescription;
        public Door(){

        }

        @Override
        public String toString(){
            return getDoorToLocationName();
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String d) {
            shortDescription = d;
        }

        public String getDoorToLocationName() {
            return doorToLocationName;
        }

        public void setDoorToLocationName(String n) {
            doorToLocationName = n;
        }

        public String getDoorActionName() {
            return doorActionName;
        }

        public void setDoorActionName(String doorActionName) {
            this.doorActionName = doorActionName;
        }

        public String getSubLocationFileName() {
            return subLocationFileName;
        }

        public void setSubLocationFileName(String subLocationFileName) {
            this.subLocationFileName = subLocationFileName;
        }
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public void setName(String s) {
        name = s;
    }

    public String getConnections(ArrayList<Integer> knowledgeLevel){
        StringBuilder connections = new StringBuilder();
        for (Map.Entry<Integer, String> entry :  descriptions.entrySet()) {
            if (knowledgeLevel.contains(entry.getKey()))
                connections.append(entry.getValue()).append("\n");
        }
        connections.delete(connections.length()-2, connections.length());
        return connections.toString();
    }

    public void addConnections(int knowledgeLevel, String loc) {
        connections.put(knowledgeLevel, loc);
    }

    public void addItems(Item itm, int knowledgeLevel) {
        items.put(knowledgeLevel, itm);
    }

    public ArrayList<Item> getItems(ArrayList<Integer> knowledgeLevel){
        ArrayList<Item> tempMap = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry :  items.entrySet()) {
            if (knowledgeLevel.contains(entry.getKey()))
                tempMap.add(entry.getValue());
        }
        return tempMap;
    }

    @Override
    public void addDescription(int knowledgeLevel, String description) {
        descriptions.put(knowledgeLevel, description);
    }


    public String getLastKnownDescription(ArrayList<Integer> knowledgeLevel) {
        return descriptions.get(Collections.max(knowledgeLevel));
    }

    @Override
    public String getDetailedDescription(ArrayList<Integer> knowledgeLevel) {
        StringBuilder detailedDescription = new StringBuilder();
        for (Map.Entry<Integer, String> entry :  descriptions.entrySet()) {
            if (knowledgeLevel.contains(entry.getKey()) && entry.getKey() != 999)
                detailedDescription.append(entry.getValue()).append("\n");
        }
        detailedDescription.delete(detailedDescription.length()-1, detailedDescription.length());
        return detailedDescription.toString();
    }
    public String getDetailedDescription(int knowledgeLevel) {
        return descriptions.getOrDefault(knowledgeLevel, null);
    }

    public ArrayList<Door> getSubLocations(ArrayList<Integer> knowledgeLevel) {
        ArrayList<Door> tempLst = new ArrayList<>();
        for (Map.Entry<Integer, Door> entry :  subLocations.entrySet()) {
            if (knowledgeLevel.contains(entry.getKey()))
                tempLst.add(entry.getValue());
        }
        return tempLst;
    }

    public void addSubLocations(Door door, int knowledgeLevel) {
        subLocations.put(knowledgeLevel, door);
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void addMonsters(Monster monster) {
        monsters.add(monster);
    }

    public ArrayList<Character> getNpcs() {
        return npcs;
    }

    public void addNpcs(Character npc) {
        npcs.add(npc);
    }

    public int getFirstVisitExp() {
        return firstVisitExp;
    }

    public void setFirstVisitExp(int firstVisitExp) {
        this.firstVisitExp = firstVisitExp;
    }

    public static String getInteraction() {
        return "Подойти к";
    }

    public ArrayList<String> getSpecialTags() {
        return specialTags;
    }

    public void setSpecialTags(ArrayList<String> specialTags) {
        this.specialTags = specialTags;
    }
}