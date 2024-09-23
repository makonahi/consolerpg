package GameObject.Items.Equipable;

import GameObject.Interfaces.LVLAdaptationSystem;
import GameObject.Interfaces.SphereSystem;
import GameObject.Items.Item;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public abstract class EquipableItem extends Item implements SphereSystem, LVLAdaptationSystem {

    protected EquipableItem() {
        this.assignedSpheres = null;
        this.lvl = 0;
    }
    //SphereAssignable
    @JsonProperty("assignedSpheres")
    protected ArrayList<String> assignedSpheres;
    //Scalable
    @JsonProperty("lvl")
    protected int lvl;

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
        /*nameComponents[0]=Foo.RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].PREFIXES);
        nameComponents[1]=Foo.RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].SUFFIXES);
        nameComponents[2]=Foo.RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].TITLES);
        nameComponents[3]=Foo.RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].MODIFIERS);
        nameComponents[4]=Foo.RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].AUTHORS);
        nameComponents[5]=Foo.RandomGenerator.getRandomElement(assignedSpheres[random.nextInt(0,lN)].PREFIXES);*/
        return nameComponents;
    }

    @Override
    public int getLvl(){
        return lvl;
    }
    @Override
    public void setLvl(int LVL){
        this.lvl = LVL;
    }

    public ArrayList<String> getAssignedSpheres(){
        return assignedSpheres;
    }
    public void addAssignedSpheres(String sphere) {
        assignedSpheres.add(sphere);
    }
}
