package GameObject.Items.Equipable.Accessories;

import GameObject.Items.Equipable.EquipableItem;
import GameObject.Items.Equipable.Weapon.WeaponType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Accessory extends EquipableItem {
    @JsonProperty("accessoryType")
    private AccessoryType accessoryType;
    @JsonProperty("statIncrease")
    private int statIncrease;

    public Accessory() {

    }

    public AccessoryType getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(AccessoryType accessoryType) {
        this.accessoryType = accessoryType;
    }

    public int getStatIncrease() {
        return statIncrease;
    }

    public void setStatIncrease(int statIncrease) {
        this.statIncrease = statIncrease;
    }
}
