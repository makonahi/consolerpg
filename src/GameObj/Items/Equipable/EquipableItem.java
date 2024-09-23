package GameObj.Items.Equipable;

import GameObj.Interfaces.LVLAdaptationSystem;
import GameObj.Interfaces.SphereSystem;
import GameObj.Items.Item;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public abstract class EquipableItem extends Item implements SphereSystem, LVLAdaptationSystem {

    protected EquipableItem(int protagonistLvl) {
        this.assignedSpheres = null;
        this.LVL = generateLvl(protagonistLvl, this.rarity.getQuality());
    }
    //SphereAssignable
    @JsonProperty("assignedSpheres")
    protected ArrayList<String> assignedSpheres;
    //Scalable
    @JsonProperty("LVL")
    protected int LVL;

    protected String generateName(String type){
        return switch (rarity) {
            case COMMON, UNUSUAL, RARE -> String.format("%s %s", rarity.getName(), type);
            case MYTHICAL, LEGENDARY, CELESTIAL -> {
                String[] nameComponents = generateNameComponents();
                yield String.format("%s%s, %s %s %s", nameComponents[0], nameComponents[1], rarity.getName(),
                        nameComponents[2], type);
            }
            case TRANSCENDENT, DIVINE, ETERNAL -> {
                String[] nameComponents = generateNameComponents();
                yield String.format("%s%s, %s %s %s, %s %s",
                        nameComponents[0],
                        nameComponents[1],
                        rarity.getName(),
                        nameComponents[2],
                        type,
                        nameComponents[3],
                        nameComponents[4]);
            }
        };
    }
    private String[] generateNameComponents(){
        int lN = assignedSpheres.size();
        String[] nameComponents = new String[6];
        /*nameComponents[0]=RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].PREFIXES);
        nameComponents[1]=RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].SUFFIXES);
        nameComponents[2]=RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].TITLES);
        nameComponents[3]=RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].MODIFIERS);
        nameComponents[4]=RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].AUTHORS);
        nameComponents[5]=RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].PREFIXES);*/
        return nameComponents;
    }
    @Override
    public int getLVL(){return LVL;}
    @Override
    public void setLVL(int lvl){LVL = lvl;}

    public ArrayList<String> getSpheres(){
        return assignedSpheres;
    }
    public void addSpheres(String sphere) {
        assignedSpheres.add(sphere);
    }
}
