package GameObject.Spheres;

import GameObject.GameObject;
import GameObject.Interfaces.NamingSystem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Sphere extends GameObject implements NamingSystem {
    @JsonProperty("name")
    private String name;
    @JsonProperty("relatedWords")
    private ArrayList<String> relatedWords;
    @JsonProperty("incompatibleSpheres")
    private ArrayList<String> incompatibleSpheres;
    @JsonProperty("parentalSpheres")
    private ArrayList<String> parentalSpheres;
    @JsonProperty("divineMaterial")
    private String divineMaterial;
    @JsonProperty("associatedString")
    private String associatedString;

    public Sphere() {

    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRelatedWords() {
        return relatedWords;
    }

    public void setRelatedWords(ArrayList<String> relatedWords) {
        this.relatedWords = relatedWords;
    }

    public ArrayList<String> getIncompatibleSpheres() {
        return incompatibleSpheres;
    }

    public void setIncompatibleSpheres(ArrayList<String> incompatibleSpheres) {
        this.incompatibleSpheres = incompatibleSpheres;
    }

    public ArrayList<String> getParentalSpheres() {
        return parentalSpheres;
    }

    public void setParentalSpheres(ArrayList<String> parentalSpheres) {
        this.parentalSpheres = parentalSpheres;
    }

    public String getDivineMaterial() {
        return divineMaterial;
    }

    public void setDivineMaterial(String divineMaterial) {
        this.divineMaterial = divineMaterial;
    }

    public String getAssociatedString() {
        return associatedString;
    }

    public void setAssociatedString(String associatedString) {
        this.associatedString = associatedString;
    }
}

