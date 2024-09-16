package GameObj.Items.Equipable.Weapon;

import GameObj.Items.Equipable.EquipableItem;
import GameObj.Items.Item;
import RandomGenerator.RandomGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Weapon extends EquipableItem {
    //урон оружия определен его уровнем и редкостью
    @JsonProperty("DMG")
    private int DMG;
    //тип оружия определен классом WeaponType
    @JsonProperty("type")
    private WeaponType type;
    //заряжено ли дальнобойное оружие или нет
    @JsonProperty("isLoaded")
    private Boolean isLoaded;

    public Weapon(int protagonistLvl){
        super(protagonistLvl);
        this.type = null;
        this.name = "Weapon";
        this.DMG = generateWeaponDMG();
    }
    private int generateWeaponDMG(){
        return (int)(this.rarity.getQuality()*2)+LVL/2;
    }
    private WeaponType generateWeaponType(){
        return RandomGenerator.getObjectFromFile(WEAPONTYPE_DIR);
    }
    public int getDMG(){
        return this.DMG;
    }
}

