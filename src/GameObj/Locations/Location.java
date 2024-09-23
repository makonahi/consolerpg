package GameObj.Locations;

import GameObj.GameObject;
import GameObj.Interfaces.DialogueSystem;
import GameObj.Interfaces.ComplexDescriptionSystem;
import GameObj.Interfaces.NamingSystem;
import GameObj.Items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Location extends GameObject implements ComplexDescriptionSystem, NamingSystem {

    //Nameable
    @JsonProperty("name")
    protected String name;
    //уровень знания о локации связан с максимально доступным подробным описанием
    @JsonProperty("descriptions")
    private HashMap<Integer, String> descriptions;

    //уровень знания о связях с другими местами также связан с максимально доступным уровнем знаний
    @JsonProperty("connections")
    private HashMap<Integer, String> connections;
    @JsonProperty("subLocations")
    private ArrayList<String> subLocations;
    @JsonProperty("items")
    private ArrayList<Item> items;

    public Location(){
        connections=new HashMap<>();
        subLocations=new ArrayList<>();
        items=new ArrayList<>();
        descriptions=new HashMap<>();
    }

    @Override
    public String getName(){
        return name;
    }
    @Override
    public void setName(String s) {
        name = s;
    }

    public String getConnections(int knowledgeLevel){
        StringBuilder knownConnections = new StringBuilder();
        for (int i=0;i<knowledgeLevel;i++)
            knownConnections.append(descriptions.getOrDefault(knowledgeLevel, null)).append("\n");
        return knownConnections.toString();
    }
    public void addConnections(int knowledgeLevel, String loc) {
        connections.put(knowledgeLevel, loc);
    }

    public void addItems(Item itm) {
        items.add(itm);
    }

    @Override
    public void addDescription(int knowledgeLevel, String description) {
        descriptions.put(knowledgeLevel, description);
    }

    @Override
    public String getDetailedDescription(int knowledgeLevel) {
        StringBuilder detailedDescription = new StringBuilder();
        for (int i=0;i<knowledgeLevel;i++)
            detailedDescription.append(descriptions.getOrDefault(knowledgeLevel, null));
        return detailedDescription.toString();
    }

    public ArrayList<String> getSubLocations() {
        return subLocations;
    }

    public void addSubLocations(String sbLC) {
        subLocations.add(sbLC);
    }
}