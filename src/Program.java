
import GameObj.Items.Equipable.Weapon.Weapon;
import GameObj.Locations.Location;
import Redactor.Redactor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Program {
    public static void main(String[] args) throws IOException {
        Location testLocation = new Location();
        testLocation.setName("Загадочные руины");
        testLocation.addConnections(1, "Тёмный лес");
        testLocation.addConnections(2, "Горы");
        testLocation.addConnections(5, "Разрушенная башня");
        StringBuilder str = new StringBuilder();
        str.append("Вы приходите в себя посреди развалин небольшого городка.");
        str.append("\n");
        str.append("Вас окружает несколько каменных фундаментов, изрядно поросших разным мхом и кустарником.");
        str.append("\n");
        str.append("С первого взгляда не определить, кто или что уничтожил это место. Может быть, город просто забросили.");
        str.append("\n");
        testLocation.addDescription(0, str.toString());
        testLocation.addDescription(1, "Вокруг остатков города растет очень плотный и древний дубовый лес.\n " +
                "Осматриваясь, вы также замечаете вдалеке горы, однако местности вы по-прежнему не узнаете.");
        testLocation.addDescription(2, "Забравшись на крышу дома, вы видите вдалеке полуразрушенную башню\n " +
                "Детально разглядеть её отсюда не получится.");
        testLocation.addDescription(5, "Завершив осмотр этого места, вы понимаете, что город, судя по всему, был уничтожен крупным взрывом.\n " +
                "Крупное углубление в центре города является взрывным кратером.");
        testLocation.addItems(new Weapon(0));
        testLocation.addSubLocations("Разрушенный дом");
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("Locations\\Загадочные руины.json"), testLocation);
    }
}
