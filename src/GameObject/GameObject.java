package GameObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public abstract class GameObject implements Serializable {

    /**
     * Получение всех полей объекта в формате ArrayList<Field>, включая унаследованные поля
     * @return список всех полей объекта
     */
    private List<Field> getAllFields() {
        List<Field> fields = new ArrayList<Field>();
        for (Class<?> c = this.getClass(); c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
    /**
     * Вывод всех полей объекта, включая унаследованные, в строковом формате {f1:10 f2:someString ...}
     */
    protected String getAllObjectFields() {
        return getObjectFieldsCharacterized(getAllFields());
    }

    /**
     * Вывод всех полей объекта, исключая унаследованные, в строковом формате {f1:10 f2:someString ...}
     */
    protected String getObjectDeclaredFields() {
        return getObjectFieldsCharacterized(Arrays.asList(this.getClass().getDeclaredFields()));
    }

    private String getObjectFieldsCharacterized(List<Field> field) {
        StringBuilder charaterizedFields = new StringBuilder();
        charaterizedFields.append("{");
        for (Field objsField : field){
            objsField.setAccessible(true);
            try {
                charaterizedFields.append(String.format("%s:%s ", objsField.getName(), objsField.get(this)));
            } catch (Exception e) {
                out.println(e.getMessage());
            }
        }
        charaterizedFields.append("}");
        return charaterizedFields.toString();
    }


}

