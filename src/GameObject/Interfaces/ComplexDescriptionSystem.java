package GameObject.Interfaces;

import java.util.ArrayList;

public interface ComplexDescriptionSystem {

    public void addDescription(int knowledgeLevel, String description);

    public String getDetailedDescription(ArrayList<Integer> knowledgeLevel);

}
