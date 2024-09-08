package Person;

import Printer.Printer;

public class Hero extends Person {

    public Hero(String n) {
        super(name);
        //BodyPart[] body = new BodyPart[6]{
        //};
    }
    private static void LVLUP(){
        while (EXP - LVLCAP >= 0) {
            EXP -= LVLCAP;
            LVL++;
            MAXHP = 16 + LVL * 2;
            HP = MAXHP;
            LVLCAP = 11 * (int) (Math.pow(1.6, LVL));
        }
        Printer.lvlUp(name, LVL, MAXHP);
    }

    public static void EXPGAIN(int exp) {
        EXP += exp;
        if (EXP >= LVLCAP)
            LVLUP();
        Printer.expGain(exp, EXP, LVLCAP);
    }

}
