package Functionalities.GameStarter;

import Functionalities.Directories;
import GameObject.Characters.Hero;
import GameObject.Items.Item;
import GameObject.Locations.Location;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static java.lang.System.out;

public class GameProcessor {
    private static final Scanner in = new Scanner(System.in);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Hero Protagonist = new Hero();
    private static Location currentLocation;
    private static String[] potentionalTravelTargetNameAndRelatedAction=new String[2];
    private static Random random = new Random();
    private static int state = 0;
    enum STATES{
        DEF,
        INVENTORYOPENED,
        DOWNED,
        NEARSOMETHING;
    }
    /*
    0 - персонаж не взаимодействует с внутриигровым меню
    1 - открыт инвентарь
    2 - вблизи объекта
     */
    private static final ArrayList<Actions> InventoryCommands = new ArrayList<>(){{
        add(Actions.DROP_ITEM);
        add(Actions.EXPLORE_ITEM);
        add(Actions.EQUIP_ITEM);
        add(Actions.CLOSE_INVENTORY);
    }};
    private static final ArrayList<Actions> GENERAL_ACTIONS = new ArrayList<>(){{
        add(Actions.LOOK_AROUND);
        add(Actions.PICK_UP);
        add(Actions.ATTACK);
        add(Actions.OPEN_INVENTORY);
        add(Actions.GO_TO);
        add(Actions.EXIT);
    }};
    private static final ArrayList<Actions> GET_IN_OR_GET_AWAY_ACTIONS = new ArrayList<>(){{
        add(Actions.GET_IN);
        add(Actions.GET_AWAY);
    }};
    private static final ArrayList<Actions> SPECIAL_ACTIONS = new ArrayList<>(){{
        add(Actions.GET_UP);
    }};
    private static final ArrayList<String> EMPTY_INVENTORY_PHRASES = new ArrayList<>(){{
        add("Пусто, как на дне высохшего колодца.");
        add("Ничего нет, словно ветром унесло...");
        add("Карманы пусты, паутина завелась.");
        add("Пусто, как в брошенной шахте.");
        add("Пусто, как у тролля в голове.");
        add("Твои карманы пусты, как безлунная ночь...");
    }};
    private static final Deque<String> newGameInitializationDialogue = new ArrayDeque<>(){{
        add("Вы лежите на земле, абсолютно без сил. Вы открываете глаза и смотрите на голубое небо над вами.\n");
        add("Внезапно приходит понимание: вы ничего не помните. В вашей голове лишь смутно \u001B[96mвитает одно слово\u001B[0m...\n");
        add("\u001B[32m[НОВАЯ ИГРА]\u001B[0m\n");
        add("Введите имя персонажа: ");
        add("\u001B[96m"+Protagonist.getName() + "\u001B[0m... Это точно ваше имя.\nТеперь пора что-то предпринять.");
    }};
    /**
     * Функция инициализации новой игры. При создании нового прохождения создается папка с именем персонажа,
     * куда копируются все базовые данные (локации, сферы, сгенерированные заранее предметы и т.д.)
     * Необходимо это для перезаписи меняющихся во время прохождения игровый объектов. (GameObject)
     *
     * Создает начальный диалог
     * Создает папку сохранения, загружает стартовую локацию и запускает MainGameCycle()
     */
    public static void startNewGame() {
        state=STATES.DOWNED.ordinal();
        out.println("Вы лежите на земле, абсолютно без сил. Вы открываете глаза и смотрите на голубое небо над вами.\n" +
                "Внезапно приходит понимание: вы ничего не помните. В вашей голове лишь смутно \u001B[96mвитает одно слово\u001B[0m...");
        out.println("\u001B[32m[НОВАЯ ИГРА]\u001B[0m");
        out.print("Введите имя персонажа: ");
        Protagonist.setName(in.nextLine());
        out.println("\u001B[96m"+Protagonist.getName() + "\u001B[0m... Это точно ваше имя.\nТеперь пора что-то предпринять.");
        copyFilesToNewGameDirectory();
        loadLocation("Загадочные руины");
        MainGameCycle();
    }

    /**
     * filler
     */
    public static void loadExistingSave(){

    }

    /**
     * Основной игровой цикл.
     */
    private static void MainGameCycle(){
        Actions act;
        do{
            do {
                out.println(Protagonist.toString());
                if (state==STATES.DEF.ordinal())
                    for (Actions a : GENERAL_ACTIONS) {
                        if (Objects.equals(a, Actions.ATTACK))
                            if (!isArrayListCorrect(currentLocation.getMonsters()))
                                continue;
                        if (Objects.equals(a, Actions.GO_TO))
                            if (!isArrayListCorrect(currentLocation.getSubLocations(getCurrentLocationKnowledge())))
                                continue;
                        if (Objects.equals(a, Actions.PICK_UP))
                            if (!isArrayListCorrect(currentLocation.getItems(getCurrentLocationKnowledge())))
                                continue;
                        if (Objects.equals(a, Actions.LOOK_AROUND))
                            if (getCurrentLocationKnowledge().contains(1))
                                continue;
                        out.print(a.getFullHint() + " ");
                    }
                else if (state==STATES.INVENTORYOPENED.ordinal())
                    for (Actions a : InventoryCommands)
                        out.print(a.getFullHint() + " ");
                else if (state==STATES.NEARSOMETHING.ordinal()) {
                    out.printf("\u001B[32m[%s] - %s \u001B[0m", Actions.GET_IN.getActionShortcut(), potentionalTravelTargetNameAndRelatedAction[1]);
                    out.print(Actions.GET_AWAY.getFullHint());
                }
                else if (state==STATES.DOWNED.ordinal()) {
                    out.print(Actions.GET_UP.getFullHint());
                }
                out.println();
                act = convertInput(in.nextLine());
                if ((state==STATES.DEF.ordinal() && !GENERAL_ACTIONS.contains(act)) ||
                        (state==STATES.INVENTORYOPENED.ordinal() && !InventoryCommands.contains(act)) ||
                            (state==STATES.NEARSOMETHING.ordinal() && !GET_IN_OR_GET_AWAY_ACTIONS.contains(act)) ||
                                state==STATES.DOWNED.ordinal() && !SPECIAL_ACTIONS.contains(act))
                    act=null;
                else
                    break;
            } while (true);
            processAction(act);


        }while (!Objects.equals(act, Actions.EXIT));
    }

    private static Actions convertInput(String input){
        for (Actions a : Actions.values())
            if (Objects.equals(input.toUpperCase(), a.getActionShortcut()))
                return a;
        return null;
    }

    private static void processAction(Actions act){
        switch (act){
            case LOOK_AROUND:
                action_lookAround();
                break;
            case GET_IN:
                action_getIntoDoor();
                break;
            case GET_AWAY:
                action_getAwayFromDoor();
                break;
            case GET_UP:
                action_getUp();
                break;
            case PICK_UP:
                action_itemPickUp();
                break;
            case ATTACK:
                action_attackMonster();
                break;
            case OPEN_INVENTORY:
                action_openInvetnory();
                break;
            case GO_TO:
                action_goToLocation();
                break;
            case GET_HISTORY_OF_INVESTIGATION:
                action_printInvestigationHistory();
                break;
            case DROP_ITEM:
                action_dropItem();
                break;
            case EXPLORE_ITEM:
                action_exploreItem();
                break;
            case EQUIP_ITEM:
                action_equipItem();
                break;
            case EXIT:
                action_exitGame();
                break;
            case CLOSE_INVENTORY:
                action_closeInventory();
                break;
            case null:
                out.println("Это ни к чему не приводит.");
                break;
        }
    }

    private static void action_printInvestigationHistory() {
        out.println(currentLocation.getDetailedDescription(getCurrentLocationKnowledge()));
    }

    private void printListOfStuff(){

    }

    private static void action_closeInventory(){
        out.println("Вы закрываете свой вещмешок.");
        state=STATES.DEF.ordinal();
    }

    private static void action_exitGame(){
        System.exit(130);
    }

    private static void action_equipItem(){

    }

    private static void action_exploreItem(){

    }

    private static void action_dropItem(){

    }

    private static void action_attackMonster(){

    }

    private static void action_lookAround(){
        Protagonist.addLocationKnowledge(Protagonist.getCurrentLocation(), 1);
        out.println(currentLocation.getDetailedDescription(1));
        Protagonist.gainEXP(currentLocation.getFirstVisitExp());
        doTag("VISITING_GIVES_KNOWLEDGE");
        doTag("VISITING_GIVES_FINAL_KNOWLEDGE");
    }

    private static void action_getUp(){
        state=STATES.DEF.ordinal();
        out.println("Вы встаете: туман, затмевающий сознание, уходит. Что-либо вспомнить пока не получается...");
        out.println(currentLocation.getDetailedDescription(getCurrentLocationKnowledge()));
    }

    private static void action_itemPickUp(){
        ArrayList<Item> knownItems = currentLocation.getItems(
                Protagonist.getLocationKnowledge(
                        Protagonist.getCurrentLocation()));
        printArrayListOfStuffElements(knownItems);
        int index = getListOfStuffElementIndex(Item.getInteraction(), knownItems.size());
        if (index != -1)
            transferItemToInventory(knownItems.get(index));
        else
            out.println("Такого нет.");
    }

    private static void action_goToLocation(){
        ArrayList<Location.Door> knownDoors =  currentLocation.getSubLocations(
                Protagonist.getLocationKnowledge(
                        Protagonist.getCurrentLocation()));
        printArrayListOfStuffElements(knownDoors);
        int index = getListOfStuffElementIndex(Location.getInteraction(), knownDoors.size());
        if (index != -1) {
            state=STATES.NEARSOMETHING.ordinal();
            potentionalTravelTargetNameAndRelatedAction[0]=knownDoors.get(index).getSubLocationFileName();
            potentionalTravelTargetNameAndRelatedAction[1]=knownDoors.get(index).getDoorActionName();
            out.printf("Вы подходите к [\u001B[35m%s\u001B[0m]. %s\n",
                    knownDoors.get(index).getDoorToLocationName(),
                    knownDoors.get(index).getShortDescription());
        }
        else
            out.println("Такого нет.");
    }

    private static void action_openInvetnory(){
        if (Protagonist.getInventory().isEmpty() || Protagonist.getInventory() == null)
            out.println(EMPTY_INVENTORY_PHRASES.get(
                    random.nextInt(EMPTY_INVENTORY_PHRASES.size())));
        else {
            state=STATES.INVENTORYOPENED.ordinal();
            ArrayList<Item> itemsInInventoryList = Protagonist.getInventory();
            printArrayListOfStuffElements(itemsInInventoryList);
        }
    }

    private static void action_getAwayFromDoor(){
        potentionalTravelTargetNameAndRelatedAction=new String[2];
        out.printf("Вы возвращаетесь в \u001B[35m%s\u001B[0m.\n", Protagonist.getCurrentLocation());
        state=STATES.DEF.ordinal();
    }

    private static void action_getIntoDoor(){
        writeCurrentLocationState();
        loadLocation(potentionalTravelTargetNameAndRelatedAction[0]);
        printArrivalMessage();
        doTag("FINALIZE_QUEST_AT_KNOWLEDGE_LEVEL");
        state=STATES.DEF.ordinal();
    }

    private static void doTag(String tag){
        for (String t : currentLocation.getSpecialTags()){
            if (t.contains(tag))
                implementTag(t);
        }
        return;
    }

    private static void implementTag(String tag){
        List<String> tagProperties = Arrays.asList(tag.split(":"));
        switch (tagProperties.getFirst()){
            //[VISITING_GIVES_KNOWLEDGE:LOCATION_NAME:30]
            case "VISITING_GIVES_KNOWLEDGE":{
                Protagonist.addLocationKnowledge(tagProperties.get(1), Integer.parseInt(tagProperties.get(2)));
                out.printf("Вы получили новое знание о: [\u001B[35m%s\u001B[0m].\n", tagProperties.get(1));
                break;
            }
            case "VISITING_GIVES_FINAL_KNOWLEDGE":{
                Protagonist.addLocationKnowledge(tagProperties.get(1), 999);
                out.printf("Теперь вы все знаете о: [\u001B[35m%s\u001B[0m].\n", tagProperties.get(1));
                break;
            }
            case "FINALIZE_QUEST_AT_KNOWLEDGE_LEVEL":{
                if (Collections.max(getCurrentLocationKnowledge())==Integer.parseInt(tagProperties.get(1))) {
                    Protagonist.addLocationKnowledge(currentLocation.getName(), 999);
                    Protagonist.gainEXP(Integer.parseInt(tagProperties.get(2)));
                    out.printf("Вы закончили исследование [\u001B[35m%s\u001B[0m].\n", currentLocation.getName());
                }
                break;
            }
        }
    }

    /**
     * Функция "передачи" предмета из другого объекта в инвентарь игрока.
     * @param item передаваемый предмет. Сравнивается с находящимися в объекте предметами с помощью Objects.equals(), после чего клон удаляется.
     */
    private static void transferItemToInventory(Item item){
        out.printf("Вы берете [\u001B[35m%s\u001B[0m]. %s\n",
                item.getName(), item.getDescription());
        Protagonist.addToInventory(item);
        currentLocation.removeItem(item);
    }
    /**
     * Функция вывода списка в виде нумерованного списка, построчно, в цветовой кодировке 35 ANSI
     * @param listOfStuff список, который необходимо вывести
     */
    private static <T> void printArrayListOfStuffElements(ArrayList<T> listOfStuff){
        out.print("\u001B[35m");
        for (int i=0;i<listOfStuff.size();i++){
            out.println((i+1) + ". " + listOfStuff.get(i).toString());

        }
        out.print("\u001B[0m");
    }
    /**
     * Функция запроса номера элемента взаимодействия.
     * @param requestLine связаное с полем действие (например, с полем Item - "Подобрать", с полем Location - "Подойти")
     * @param maxValue длина списка, возвращаемое значение должно находиться между 0 и этим аргументом, в противном случае = -1
     * @return возвращает индекс в формате Integer, если индекс не входит в диапазон, возвращает -1
     */
    private static int getListOfStuffElementIndex(String requestLine, int maxValue){
        out.printf("%s [введите номер]...\n", requestLine);
        String input = in.nextLine();
        int index = -1;
        try {
            index = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
        if (index > maxValue || index < 0) {
            return -1;
        }
        return index;
    }

    /**
     * Загрузка НОВОЙ локации и запись её в переменную currentLocation.
     * Текущее состояние СТАРОЙ локации перезаписывается в файл.
     * прим. файлы грузятся из папки игрока
     * @param newLocation
     * @param hasTraveledToAnotherPlace
     */
    private static void loadLocation(String newLocation) {
        Protagonist.setCurrentLocation(newLocation);
        Protagonist.addLocationKnowledge(newLocation, 0);
        File loadLocation = new File(getFullFormatedPath(Directories.LOCATIONS_DIR.getJustDirName(), newLocation));
        try {
            currentLocation = objectMapper.readValue(loadLocation, Location.class);
        }
        catch (IOException e){
            out.println(e.getMessage());
        }
    }

    //TODO описание функции
    private static void writeCurrentLocationState(){
        File loadLocation = new File(getFullFormatedPath(Directories.LOCATIONS_DIR.getJustDirName(),
                Protagonist.getCurrentLocation()));
        try {
            objectMapper.writeValue(loadLocation, currentLocation);
        }
        catch (IOException e){
            out.println(e.getMessage());
        }
    }

    /**
     * Функция получения полного пути ФАЙЛА для объекта из папки с прохождением.
     * @param dirName имя директории (прим. Locations\, Spheres\)
     * @param fileName имя игрового объекта
     * @return полный путь до объекта
     */
    private static String getFullFormatedPath(String dirName, String fileName) {
        return "saves\\" + Protagonist.getName() + "\\" + dirName + "\\" + fileName + ".json";
    }

    /**
     * Вывод generic-фразы при прибытии в другую локацию (НЕ сублокации).
     * При этом выводится детальная история её исследования при прошлых посещениях
     */
    private static void printArrivalMessage(){
        out.println("Вы прибываете в [\u001B[35m" + currentLocation.getName() + "\u001B[0m].");
        out.println(
                currentLocation.getLastKnownDescription(
                        Protagonist.getLocationKnowledge(
                                Protagonist.getCurrentLocation())));
    }
    /**
     * Получение списка всех знаний о текущей локаций. Эта функция получает только уровни знаний и ничего не выводит.
     * @return список вида {0, 1, 3, 2}
     */
    private static ArrayList<Integer> getCurrentLocationKnowledge(){
        return Protagonist.getLocationKnowledge(Protagonist.getCurrentLocation());
    }

    /**
     * Провека списка на корректность.
     * @param lst проверяемый список.
     * @return возвращает true если список корректен (не пуст или null), в противном случае возвращает false
     * @param <T> любой тип
     */
    private static <T> boolean isArrayListCorrect(ArrayList<T> lst) {
        if (lst==null || lst.isEmpty())
            return false;
        return true;
    }

    /**
     * Копирование файлов из game_data в -> папку saves-> папку игрока.
     */
    private static void copyFilesToNewGameDirectory() {
        for (Directories dir : Directories.values()){
            try {
                Path sourceDir = Paths.get("game_data\\" + dir.getDirName());
                Path destinationDir = Paths.get("saves\\" + Protagonist.getName() + "\\" + dir.getDirName());
                Files.createDirectories(destinationDir);
                Files.list(sourceDir).forEach(sourcePath -> {
                    try {
                        Path targetPath = destinationDir.resolve(sourcePath.getFileName());
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
