package GameObject.Interfaces;

import static GameObject.RandomGenerator.RandomGenerator.random;

//объекты могут иметь уровень, скалируемый уровнем персонажа
public interface LVLAdaptationSystem {

     int getLvl();
     void setLvl(int lvl);

     default int generateLvl(int protagonistLvl, Double scaleParam){
          if (scaleParam>5.0D)
               return (int)random.nextDouble(scaleParam/2, scaleParam);
          else
               return Math.max(protagonistLvl + random.nextInt(-3,3), 0);
     }
}
