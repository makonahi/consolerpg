package Printer;

import GameObj.Items.Equipable.Weapon.Weapon;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Objects;

import static java.lang.System.*;

public class Printer {

    public static void lvlUp(String name, int lvl, int maxhp){
        out.printf("%s достигает уровня %d! Максимальное здоровье теперь: %d.\n", name, lvl, maxhp);
    }

    public static void expGain(int exp, int allExp, int lvlcap){
        out.printf("Вы получили %d опыта. Опыта для следующего уровня: %d/%d.\n", exp, allExp, lvlcap);
    }

    public static void foundItem(Weapon wpn){
        out.printf("Вы находите оружие: %s { LVL:%s;DMG:%s;WGH:%.2f;PRC:%.2f }. ",
                wpn.getName(), wpn.getLVL(), wpn.getDMG(), wpn.getWeight(), wpn.getPrice());
        if (wpn.getSpheres()==null)
        {
            out.println("Этот предмет не связан ни с какими сферами.");
            return;
        }
        if (wpn.getSpheres().size()==1)
        {
            out.printf("Этот предмет связан с единственной сферой: %s.\n", wpn.getSpheres().getFirst().toString());
            return;
        }
        StringBuilder spheres = new StringBuilder();
        for (String sphere : wpn.getSpheres())
            spheres.append(sphere).append(", ");
        spheres.delete(spheres.length()-2, spheres.length());
        out.printf("Этот предмет связан со сферами: "+spheres+".\n");
    }
    public static void errorMessage(String errorText){
        out.println(errorText);
    }
    public static void fileSaved(String type, String fileName){
        out.printf("%s сохранен(а): %S.\n", type, fileName);
    }
    public static void fileErrorWhileReading(String type, String errorMessage){
        out.printf("%s не может быть прочитан(а): %S.\n", type, errorMessage);
    }
    public static void fileErrorWhileWriting(String type, String errorMessage){
        out.printf("%s не может быть записан(а): %S.\n", type, errorMessage);
    }
    public static void fileErrorWhileDeleting(String errorMessage){
        out.printf("Ошибка при удалении: %S.\n", errorMessage);
    }

    public static void enterFieldValue(Field fld){
        enterFieldValue(fld, "");
    }
    public static void enterFieldValue(Field fld, String hint){
        out.printf("Введите значение для поля %s (%s)%s:\n", fld.getName(), fld.getType().getSimpleName(), hint);
    }

    public static boolean printFileTree(String topDir){
        File dir = new File(topDir);
        if (!dir.exists() || Objects.requireNonNull(dir.listFiles()).length==0) {
            Printer.errorMessage("Директория отсутствует или пуста. Создайте хотя бы один файл.");
            return false;
        }
        out.println("Файлы в директории:");
        for(File item : Objects.requireNonNull(dir.listFiles())){
            if(item.isDirectory())
                continue;
            out.println(item.getName());
        }
        return true;
    }

    public static <T> void printObjInfo(T obj){
        if (obj == null) {
            out.println("Объект равен null.");
            return;
        }

        Class<?> clazz = obj.getClass();
        out.println("Информация о полях объекта класса " + clazz.getName() + ":");

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true);
            out.print("Поле: " + field.getName() + " (Тип: " + field.getType().getSimpleName() + ") ");

            try {
                Object value = field.get(obj);
                out.println("Значение: " + value);
            } catch (Exception e) {
                out.println("Не удалось получить значение поля: " + e.getMessage());
            }
        }
    }

}
