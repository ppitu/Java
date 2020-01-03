
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FieldInspector implements FieldInspectorInterface {

    @Override
    public Collection<FieldInfo> inspect(String className) {
        try {
            Class aClass = Class.forName(className);
            List<FieldInfo> fieldInfos = new LinkedList<>();
            for (Field declaredField : aClass.getDeclaredFields()) {
                int modifier = declaredField.getModifiers();
                if (Modifier.isPublic(modifier)) {
                    Type type = mapGenericTypeNameToType(declaredField.getGenericType().getTypeName());
                    FieldVersion annotation = declaredField.getAnnotation(FieldVersion.class);
                    if (annotation != null) {
                        fieldInfos.add(new FieldInfo(type, declaredField.getName(), annotation.version()));
                    } else {
                        fieldInfos.add(new FieldInfo(type, declaredField.getName()));
                    }
                }
            }
            return fieldInfos;
        } catch (Exception a) {
            return null;
        }
    }

    private Type mapGenericTypeNameToType(String name) {
        Type type;
        if (name.equalsIgnoreCase("int") || name.equalsIgnoreCase("java.lang.Integer")) {
            type = Type.INT;
        } else if (name.equalsIgnoreCase("long") || name.equalsIgnoreCase("java.lang.Long")) {
            type = Type.LONG;
        } else if (name.equalsIgnoreCase("float") || name.equalsIgnoreCase("java.lang.Float")) {
            type = Type.FLOAT;
        } else if (name.equalsIgnoreCase("double") || name.equalsIgnoreCase("java.lang.Double")) {
            type = Type.DOUBLE;
        } else {
            type = Type.OTHER;
        }
        return type;
    }
}
