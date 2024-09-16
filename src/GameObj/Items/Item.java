package GameObj.Items;

import GameObj.GameObject;
import GameObj.Interfaces.BasicDescriptionSystem;
import GameObj.Interfaces.NamingSystem;
import RandomGenerator.RandomGenerator;
import static RandomGenerator.RandomGenerator.random;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Item extends GameObject implements NamingSystem, BasicDescriptionSystem {

    //Nameable
    @JsonProperty("name")
    protected String name;
    @JsonProperty("description")
    protected String description;

    @JsonProperty("weight")
    protected double weight;
    @JsonProperty("price")
    protected double price;
    @JsonProperty("rarity")
    protected Rarity rarity;

    protected Item(){
        this.rarity = generateItemRarity();
        this.price = generateItemPrice();
        this.weight = generateItemWeight();
    }

    private Rarity generateItemRarity() {
        return Rarity.values()[RandomGenerator.generateGeometricNumber(Rarity.values().length)];
    }
    private Rarity generateItemRarity(Rarity maxRarity){
        return Rarity.values()
                [RandomGenerator.generateGeometricNumber(Arrays.asList(Rarity.values()).indexOf(maxRarity))];
    }
    private ArrayList<String> generateItemSpheresArray() {
        ArrayList<String> tempLst = new ArrayList<>();
        tempLst.add(RandomGenerator.getObjectFromFile(SPHERES_DIR).toString());
        return tempLst;
    }
    private double generateItemPrice(){
        return random.nextInt(3, 5) * rarity.getQuality() + Math.pow(rarity.getQuality(),2D);
    }
    private double generateItemWeight(){
        return random.nextDouble(3, 8) / Math.max(rarity.getQuality(), 1D);
    }

    public double getWeight(){
        return weight;
    }
    public double getPrice(){
        return price;
    }

    @Override
    public String getName() {return name;}
    @Override
    public String getDescription() {return description;}

    @Override
    public void setName(String n) {name = n;}
    @Override
    public void setDescription(String n) {name = n;}

}
