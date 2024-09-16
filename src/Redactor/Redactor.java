package Redactor;

import GameObj.Items.Equipable.Weapon.WeaponType;
import GameObj.Locations.Location;
import Printer.Printer;
import GameObj.Spheres.Sphere;

import java.io.*;
import static java.lang.System.*;
import java.lang.Class;

import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Redactor implements Serializable{
    private static final String LOCATION_DIR = "Locations\\";
    private static final String SPHERES_DIR = "Spheres\\";
    private static final String WEAPONTYPE_DIR = "Weapon\\WeaponType\\";
    private static final Scanner in = new Scanner(System.in);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final List<Commands> EDIT_COMMANDS = new ArrayList<>(){{
        add(Commands.EDIT_SPHERE);
    }};
    private final List<Commands> OPTION_COMMANDS = new ArrayList<>(){{
        add(Commands.CREATE);
        add(Commands.READ);
        add(Commands.DELETE);
    }};

    private enum Commands{
        EXIT("E", "выйти"),
        CONTINUE("Y", "продолжить"),

        CREATE("C", "создать"),
        READ("READ", "чтение"),
        DELETE("DEL", "удалить"),


        EDIT_SPHERE("S", "сфера"),
        EDIT_LOCATION("L", "локация"),
        EDIT_WEAPON("WPN", "оружие"),
        EDIT_WEAPONTYPE("WPNTYPE", "тип оружия");


        private final String code;
        private final String hint;

        Commands(String code, String hint) {
            this.code = code;
            this.hint = hint;
        }

        public String getCode() {
            return code;
        }
        public String getHint() {
            return hint;
        }
        public String getHintWithCode() {
            return String.format("[%s] - %s", code, hint);
        }
    }

    //чтение файла-объекта по имени файла
    private <T> T readFile( Class<T> clz, String dir, String fileName) {
        File objFile = new File(dir, fileName);
        if (!objFile.exists()) {
            Printer.fileErrorWhileReading(objFile.getAbsolutePath(), "файл не найден");
            return null;
        }
        try {
            return objectMapper.readValue(objFile, clz);
        } catch (Exception e) {
            Printer.fileErrorWhileReading(objFile.getAbsolutePath(), e.getMessage());
            return null;
        }
    }

    //запись объекта в файл
    private <T> void writeFile(T obj, String dir, String fileName){
        try{
            File checkDir = new File(dir);
            if (!checkDir.exists())
                if (!checkDir.mkdirs())
                    Printer.fileErrorWhileWriting(obj.toString(), "директория не может быть создана");
            File objFile = new File(dir, fileName+".json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(objFile, obj);
            Printer.fileSaved(obj.toString(), objFile.getAbsolutePath());
        }
        catch (IOException e) {
            Printer.fileErrorWhileWriting(obj.toString(), e.getMessage());
        }
    }

    private void deleteFile(String dir, String fileName){
        try{
            Files.delete(Paths.get(dir+fileName));
        }
        catch (IOException e){
            Printer.fileErrorWhileDeleting(e.getMessage());
        }
    }

    //создание нового объекта
    private static <T> T createNewObject(Class<T> clz) {
        T obj = null;
        try {
            obj = clz.getDeclaredConstructor().newInstance();
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Printer.enterFieldValue(field);
                String input = in.nextLine();
                Object value = convertInput(input, field.getType());
                field.set(obj, value);
            }
            out.println("Объект создан.");
        } catch (Exception e) {
            Printer.fileErrorWhileWriting(clz.toString(), e.getMessage());
        }
        return obj;
    }

    private <T> String getFileName(T obj){
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equalsIgnoreCase("name")||
                field.getName().equalsIgnoreCase("baseName"))
                    return (String) field.get(obj);
            }
        } catch (Exception e) {
            Printer.fileErrorWhileWriting(obj.toString(), e.getMessage());
        }
        return String.valueOf(obj.hashCode());
    }

    private String requestFileName(){
        out.println("Введите имя файла:");
        String fileName=in.nextLine();
        if (!fileName.endsWith(".json"))
            fileName+=".json";
        return fileName;
    }

    private static Object convertInput(String input, Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return Integer.parseInt(input);
        } else if (type == double.class || type == Double.class) {
            return Double.parseDouble(input);
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.parseBoolean(input);
        } else if (type == String.class) {
            return input;
        } else if (type == ArrayList.class) {
            List<String> list = new ArrayList<>();
            for (String item : input.split(";")) {
                list.add(item.trim());
            }
            return list;
        }  else {
            out.println("РЕДАКТИРОВАНИЕ ВСТРОЕННОГО ОБЪЕКТА:");
            return createNewObject(type);
        }
    }

    private <T> void requestOption(Class<T> clz, String dir, Commands cmd){
        switch (cmd){
            case Commands.CREATE : {
                T obj = createNewObject(clz);
                writeFile(obj, dir, getFileName(obj));
                break;
            }
            case Commands.READ : {
                if(!Printer.printFileTree(dir))
                    return;
                T obj;
                obj = readFile(clz, dir, requestFileName());
                Printer.printObjInfo(obj);
                break;
            }
            case Commands.DELETE: {
                if(!Printer.printFileTree(dir))
                    return;
                deleteFile(dir, requestFileName());
                break;
            }
            case null :
            default :
        }
    }

    private Commands getCommand(String input)
    {
        for (Commands cmd : Commands.values())
            if (Objects.equals(input.toUpperCase(), cmd.getCode()))
                return cmd;
        return null;
    }

    public void start(){
        Commands cmd, opt;
        do {
            do {
                StringBuilder listedCommands = new StringBuilder();
                for (Commands edCmd : EDIT_COMMANDS)
                    listedCommands.append(edCmd.getHintWithCode()).append(" ");
                out.printf("Выберите, какой объект вы хотите редактировать:\n%s\n", listedCommands);
                cmd = getCommand(in.nextLine());
            } while (!EDIT_COMMANDS.contains(cmd));
            do {
                StringBuilder listedCommands = new StringBuilder();
                for (Commands optCmd : OPTION_COMMANDS)
                    listedCommands.append(optCmd.getHintWithCode()).append(" ");
                out.printf("Выберите опцию открытия:\n%s\n", listedCommands);
                opt = getCommand(in.nextLine());
            } while (!OPTION_COMMANDS.contains(opt));
            switch (cmd) {
                case EDIT_SPHERE:
                    requestOption(Sphere.class, SPHERES_DIR, opt);
                    break;
                case EXIT:
                case null:
                default:
                    continue;
            }
            out.printf("Напишите %s, если хотите %s.\n", Commands.CONTINUE.getCode(), Commands.CONTINUE.getHint());
            cmd = getCommand(in.nextLine());
        }while (cmd==Commands.CONTINUE);
    }
}
