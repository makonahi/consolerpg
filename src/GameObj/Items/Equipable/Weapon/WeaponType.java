package GameObj.Items.Equipable.Weapon;

import GameObj.Interfaces.BasicDescriptionSystem;
import GameObj.Interfaces.NamingSystem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WeaponType implements NamingSystem, BasicDescriptionSystem, Serializable {

    @JsonProperty("baseName")
    private String baseName;
    @JsonProperty("baseDescription")
    private String baseDescription;

    @JsonProperty("baseDMG")
    private int baseDMG;
    @JsonProperty("range")
    private int range;
    @JsonProperty("actionPointsUsage")
    private Double actionPointsUsage;
    @JsonProperty("isSingleHanded")
    private Boolean isSingleHanded;
    @JsonProperty("isRanged")
    private Boolean isRanged;

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
