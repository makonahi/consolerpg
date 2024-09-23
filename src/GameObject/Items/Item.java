package GameObject.Items;

import GameObject.GameObject;
import GameObject.Interfaces.BasicDescriptionSystem;
import GameObject.Interfaces.NamingSystem;
import GameObject.Items.Equipable.Accessories.Accessory;
import GameObject.Items.Equipable.Weapon.Weapon;
import GameObject.Items.Equipable.Weapon.WeaponType;
import GameObject.RandomGenerator.RandomGenerator;
import static GameObject.RandomGenerator.RandomGenerator.random;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Arrays;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Weapon.class, name = "weapon"),
        @JsonSubTypes.Type(value = WeaponType.class, name = "weapontype"),
        @JsonSubTypes.Type(value = Accessory.class, name = "accessory")
})
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public Item(){
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
    public String getName() {
        return name;
    }
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setName(String n) {
        name = n;
    }
    @Override
    public void setDescription(String n) {
        description = n;
    }

    public static String getInteraction() {
        return "Взять";
    }

    @Override
    public String toString(){
        return getName();
    }
}
