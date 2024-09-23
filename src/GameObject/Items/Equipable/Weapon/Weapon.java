package GameObject.Items.Equipable.Weapon;

import GameObject.Items.Equipable.EquipableItem;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Weapon extends EquipableItem {
    //урон оружия определен его уровнем и редкостью
    @JsonProperty("dmg")
    private int dmg;
    //тип оружия определен классом WeaponType
    @JsonProperty("weapontype")
    private WeaponType weapontype;
    //заряжено ли дальнобойное оружие или нет
    @JsonProperty("isLoaded")
    private Boolean isLoaded;

    public Weapon(){

    }

    public Weapon(int protagonistLvl){
        this.weapontype = null;
        this.name = "rand";
        this.dmg = generateWeaponDMG();
    }
    private int generateWeaponDMG(){
        return (int)(this.rarity.getQuality()*2)+lvl/2;
    }
    public int getDmg(){
        return this.dmg;
    }
    public void setDmg(int dmg){
        this.dmg = dmg;
    }

    public WeaponType getWeapontype() {
        return weapontype;
    }

    public void setWeapontype(WeaponType weapontype) {
        weapontype = weapontype;
    }

    public Boolean getIsLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(Boolean loaded) {
        isLoaded = loaded;
    }

    @Override
    public String toString(){
        if (getAssignedSpheres()!=null)
            return String.format("%s {DMG:%s, lvl:%s, WGH:%.2f, PRC:%.2f, RAR:%s, SPR:%s}",
                name, dmg, lvl, weight, price, rarity.getName(), getAssignedSpheres().toString());
        else
            return String.format("%s {DMG:%s, lvl:%s, WGH:%.2f, PRC:%.2f, RAR:%s}",
                    name, dmg, lvl, weight, price, rarity);
    }
}

