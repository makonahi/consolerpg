package Items;

import Spheres.*;

import java.util.Random;

public abstract class Item {
    protected static final int MAXIMUM_ASSIGNED_SPHERES=3;
    protected static final Random random = new Random();
    protected int LVL;
    protected double weight;
    protected double price;
    protected String name;
    protected String baseName;
    protected String description;
    protected Sphere[] assignedSpheres;
    protected Rarity rarity;
    protected enum Rarity {
        ORDINARY("Ordinary", 50D, 0.2D),
        COMMON("Common", 30D, 0.5D),
        UNUSUAL("Unusual", 10D, 0.8D),
        RARE("\u001B[0;34mRare", 5D, 1.2D),
        SUPERIOR("\u001B[0;32mSuperior", 2.5D, 2D),
        EPIC("\u001B[0;31mEpic", 1.5D, 3D),
        LEGENDARY("The \u001B[33mLegendary", 0.5D, 4D),
        MYTHICAL("The \u001B[0;92mMythical", 0.35D, 4.5D),
        CELESTIAL("The \u001B[35mCelestial", 0.08D, 6D),
        TRANSCENDENT("The \u001B[0;96mTranscendent", 0.04D, 8D),
        DIVINE("The \u001B[0;95mDivine", 0.02D, 12D),
        ETERNAL("The \u001B[0;91mEternal", 0.01D, 16D);

        private final String name;
        private final double chance;
        private final Double quality;

        Rarity(String name, Double chance, Double quality) {
            this.name = name;
            this.chance = chance;
            this.quality = quality;
        }

        public String getName() {
            return name;
        }

        public Double getChance() {
            return chance;
        }

        public Double getQuality() {
            return quality;
        }
    }

    Item(int protagonistLvl){

    }

    private final Sphere[] allSpheresArr = new Sphere[]{
            new sphereNature(), new spherePlanets(), new sphereDarkness(), new sphereCraftsmanship(), new sphereDisease(),
            new sphereDeath(), new sphereFire(), new sphereColdness()
    };


    protected static String getRandomElement(String[] array) {
        int index = random.nextInt(array.length);
        return array[index];
    }

    protected static Rarity generateItemRarity() {
        double totalWeight = 0D;
        double randomNumber = random.nextDouble() * totalWeight;
        double cumulativeWeight = 0D;

        for (Rarity rarity : Rarity.values()) {
            totalWeight += rarity.getChance();
        }

        for (Rarity rarity : Rarity.values()) {
            cumulativeWeight += rarity.getChance();
            if (randomNumber <= cumulativeWeight) {
                return rarity;
            }
        }

        return Rarity.ORDINARY;
    }
    private static void shuffleSphereArray(Sphere[] arr)
    {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            Sphere a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
    }
    protected Sphere[] generateItemSpheresArray() {
        if (rarity==Rarity.COMMON||rarity==Rarity.ORDINARY||rarity==Rarity.UNUSUAL)
            return null;
        Sphere[] tempArr = new Sphere[random.nextInt(1,4)];
        shuffleSphereArray(allSpheresArr);
        System.arraycopy(allSpheresArr, 0, tempArr, 0, tempArr.length);
        return tempArr;
    }
    protected double generateItemPrice(){
        return random.nextInt(1, 5) * Math.pow(rarity.getQuality(),2D);
    }
    protected double generateItemWeight(){
        return random.nextDouble(3, 8) / Math.max(rarity.getQuality(), 1D);
    }
    protected int generateItemLvl(int protagonistLvl){
        return (int)Math.max(protagonistLvl+random.nextDouble(-10+rarity.getQuality(),10+rarity.getQuality()),0D);
    }
    protected String generateItemName(){
        return switch (rarity) {
            case ORDINARY, COMMON, UNUSUAL -> String.format("%s %s", rarity.getName(), baseName);
            case RARE, SUPERIOR, EPIC -> {
                String[] nameComponents = generateNameComponents();
                yield String.format("%s%s, %s %s %s", nameComponents[0], nameComponents[1], rarity.getName(),
                        nameComponents[2], baseName);
            }
            case MYTHICAL, LEGENDARY, CELESTIAL, TRANSCENDENT, DIVINE, ETERNAL -> {
                String[] nameComponents = generateNameComponents();
                yield String.format("%s%s, %s %s %s, %s %s",
                        nameComponents[0],
                        nameComponents[1],
                        rarity.getName(),
                        nameComponents[2],
                        baseName,
                        nameComponents[3],
                        nameComponents[4]);
            }
        };
    }
    private String[] generateNameComponents(){
        int lN = assignedSpheres.length;
        String[] nameComponents = new String[6];
        nameComponents[0]=getRandomElement(assignedSpheres[random.nextInt(0,lN)].PREFIXES);
        nameComponents[1]=getRandomElement(assignedSpheres[random.nextInt(0,lN)].SUFFIXES);
        nameComponents[2]=getRandomElement(assignedSpheres[random.nextInt(0,lN)].TITLES);
        nameComponents[3]=getRandomElement(assignedSpheres[random.nextInt(0,lN)].MODIFIERS);
        nameComponents[4]=getRandomElement(assignedSpheres[random.nextInt(0,lN)].AUTHORS);
        nameComponents[5]=getRandomElement(assignedSpheres[random.nextInt(0,lN)].PREFIXES);
        return nameComponents;
    }
    public String getName(){return name;}
    public int getLVL(){return LVL;}
    public double getWeight(){return weight;}
    public double getPrice(){return price;}
    public Sphere[] getSpheres(){return assignedSpheres;}
}
