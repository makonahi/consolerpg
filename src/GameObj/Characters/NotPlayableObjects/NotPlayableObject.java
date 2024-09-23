package GameObj.Characters.NotPlayableObjects;

import GameObj.Characters.Person;
import GameObj.Interfaces.SphereSystem;
import GameObj.Spheres.Sphere;

import java.util.ArrayList;

abstract class NotPlayableObject extends Person {

    protected String introDialogue;
    protected ArrayList<Sphere> assignedSpheres;

}
