package GameObject.Items.Equipable.Weapon;

import GameObject.Interfaces.BasicDescriptionSystem;
import GameObject.Interfaces.NamingSystem;
import GameObject.Items.Item;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeaponType extends Item implements NamingSystem, BasicDescriptionSystem {

    @JsonProperty("baseName")
    private String baseName;
    @JsonProperty("baseDescription")
    private String baseDescription;

    @JsonProperty("baseDMG")
    private int baseDMG;
    @JsonProperty("range")
    private int range;
    @JsonProperty("actionPointsUsage")
    private double actionPointsUsage;
    @JsonProperty("isSingleHanded")
    private boolean isSingleHanded;
    @JsonProperty("isRanged")
    private boolean isRanged;

    public WeaponType() {

    }
    WeaponType(int baseDMG, int range, double actionPointsUsage, boolean isSingleHanded, boolean isRanged){
        this.baseDMG=baseDMG;
        this.range=range;
        this.actionPointsUsage=actionPointsUsage;
        this.isSingleHanded=isSingleHanded;
        this.isRanged=isRanged;
    }

    @Override
    public void setName(String n) {
        baseName = n;
    }

    @Override
    public void setDescription(String d) {
        baseDescription=d;
    }

    @Override
    public String getName(){
        return baseName;
    }

    @Override
    public String getDescription(){
        return baseDescription;
    }

    public int getBaseDMG(){
        return baseDMG;
    }

    public int getRange(){
        return range;
    }

    public double getActionPointsUsage(){
        return actionPointsUsage;
    }

    public boolean getIsSingleHanded(){
        return isSingleHanded;
    }

    public boolean getIsRanged(){
        return isRanged;
    }
}
