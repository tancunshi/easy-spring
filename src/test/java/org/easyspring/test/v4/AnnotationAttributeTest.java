package org.easyspring.test.v4;

import org.easyspring.core.annotation.AnnotationAttributes;
import org.junit.Test;
import static junit.framework.TestCase.*;

public class AnnotationAttributeTest {
    @Test
    public void getAttribute(){
        AnnotationAttributes attr = new AnnotationAttributes();
        attr.put("double",2020.1);
        attr.put("class",String.class);
        assertTrue(attr.getNumber("double")
                .equals(new Double("2020.1")));
        assertTrue(attr.getClass("class") == String.class);
        try {
            attr.getBoolean("not exist");
        }catch (IllegalArgumentException e){
            return;
        }
        fail();
    }
}
