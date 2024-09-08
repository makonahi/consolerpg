package Items;

public class Weapon extends Item {
    protected int DMG;
    String[] features;
    public Weapon(int protagonistLvl){
        super(protagonistLvl);
        this.rarity = generateItemRarity();
        this.price = generateItemPrice();
        this.weight = generateItemWeight();
        this.assignedSpheres = generateItemSpheresArray();
        this.baseName = getRandomElement(BASE_NAMES);
        this.name = generateItemName();
        this.LVL = generateItemLvl(protagonistLvl);
        this.DMG = generateWeaponDMG();
    }
    protected int generateWeaponDMG(){
        return (int)(this.rarity.getQuality()*2)+LVL/2;
    }
    private static final String[] BASE_NAMES = {
            "Broadsword\u001B[0m", "Axe\u001B[0m", "Items.Dagger\u001B[0m", "Spear\u001B[0m", "Mace\u001B[0m", "Bow\u001B[0m"
    };
    public int getDMG(){return this.DMG;}
}

interface Melee{
    //void Strike();
}
interface Ranged{
    //void Shot();
}
interface TwoHanded{
    public static boolean IS_TWO_HANDED=true;
}
interface SingleHanded{
    public static boolean IS_TWO_HANDED=false;
}

class Longsword extends Weapon implements Melee, TwoHanded{
    Longsword(int protagonistLvl) {
        super(protagonistLvl);
    }
}

class Longbow extends Weapon implements Ranged, TwoHanded{
    Longbow(int protagonistLvl) {
        super(protagonistLvl);
    }
}

class Dagger extends Weapon implements Melee, SingleHanded{
    Dagger(int protagonistLvl) {
        super(protagonistLvl);
    }
}

class Crossbow extends Weapon implements Ranged, SingleHanded{
    Crossbow(int protagonistLvl) {
        super(protagonistLvl);
    }
}