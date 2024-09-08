package Spheres;

public class sphereDeath extends Sphere{
    {
        this.name = "Death";

        this.PREFIXES = new String[]{
                "Grave", "Reaper", "Ghoul", "Necro", "Wraith", "Soul", "Skull", "Tomb", "Mortis",
                "Phantom", "Lich"
        };
        this.SUFFIXES = new String[]{
                "scythe", "shade", "spirit", "wail", "grim"
        };
        this.TITLES = new String[]{
                "Soulbound", "Deadly", "Hollow", "Mortal"
        };
        this.MODIFIERS = new String[]{
                "with a Death's touch from", "Brought from the Nevermore by", "Haunted by",
                "Marked by"
        };
        this.AUTHORS = new String[]{
                "Dead God", "Restless soul", "Death itself", "God of Fear", "Imprisoned soul, resting in item"
        };
    }
}
