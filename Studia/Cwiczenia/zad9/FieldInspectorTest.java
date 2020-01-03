



import org.junit.jupiter.api.Test;

import java.util.Collection;

class FieldInspectorTest {
    @Test
    void test() {
        FieldInspector fieldInspector = new FieldInspector();
        Collection<FieldInspectorInterface.FieldInfo> inspect = fieldInspector.inspect("field.TestClass");
        inspect.forEach(x -> System.out.println(x.name + " " + x.type.name() + " " + x.version));
    }
}
