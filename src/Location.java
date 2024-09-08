public class Location {
    protected static String name;
    private static Sublocation[] subLocs;
    protected static String description;
    private static String[] connections;
    class InteractiveObject{
        String name;
        String description;
    }
    Location(String n, String d){
        name = n;
        description = d;
    }
    Location(String n, String d, Sublocation[] sblc, String[] cntc){
        name = n;
        description = d;
        subLocs = sblc;
        connections = cntc;
    }
    public void fileLocationReader(){

    }
    public void fileLocationWriter(){

    }
}
class Sublocation extends Location {
    Sublocation(String n, String d){
        super(name, description);
    }
}