package Spheres;

public abstract class Sphere {
    //A sphere, or cosmic principle, is an aspect where a being has influence.
    protected String name;
    public String[] PREFIXES;
    public String[] SUFFIXES;
    public String[] TITLES;
    public String[] MODIFIERS;
    public String[] AUTHORS;

    @Override
    public String toString()
    {
        return "Sphere of " + name;
    }
}

