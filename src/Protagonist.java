
public class Protagonist {
    public static int HP=16;
    public static int MAXHP=16;
    public static int LVL=1;
    public static int EXP=0;
    public static String name;
    public static Item[] inventory;
    public static int carryWeight;

    Protagonist(String tname)
    {
        name = tname;
        inventory = new Item[12];
    }

    public static boolean CheckIfLVLUP(){
        int LVLCAP=(int)(Math.sqrt(LVL*100)*1.8f);
        if (EXP>=LVLCAP)
        {
            while (EXP-LVLCAP>=0) {
                EXP-=LVLCAP;
                LVL++;
                MAXHP = 16 + LVL * 2;
                HP = MAXHP;
            }
           Printer.lvlUp(name, LVL, MAXHP);
            return true;
        }
        return false;
    }

    public static void EXPGAIN(int exp){
        EXP+=exp;
        Printer.expGain(exp);
        if (!CheckIfLVLUP())
            Printer.expToUp(EXP, LVL);
    }

}
