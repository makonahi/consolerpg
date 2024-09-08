package Spheres;

public class sphereDisease extends Sphere{
    {
        this.name = "Disease";

        this.PREFIXES = new String[]{
                "Cancer", "Cyst", "Filth", "Bloat", "Bloody", "Maggot", "Lust",
                "Meat", "Slime", "Worm", "Waste", "Blight", "Monster", "Gore", "Fever"
        };
        this.SUFFIXES = new String[]{
                "piece", "tentacle", "chunk", "tumor", "spreader"
        };
        this.TITLES = new String[]{
                "Ugly", "Deformed", "Undying", "Gross", "Infected", "Feverish", "Epidemic",
                "Rotting", "Corrupted", "Pestilent"
        };
        this.MODIFIERS = new String[]{
                "Corrupted by", "Poisoned by", "Rotting from", "Decayed by"
        };
        this.AUTHORS = new String[]{
                "God of Pestilence", "The Plaguebringer", "Rotting Spirits", "Plague Priesthood"
        };
    }
}
