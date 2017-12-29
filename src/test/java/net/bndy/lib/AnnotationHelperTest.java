package net.bndy.lib;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.*;

public class AnnotationHelperTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = {ElementType.TYPE, ElementType.FIELD})
    public @interface TestAnnotation {
        String name();
    }

    @TestAnnotation(name = "ClassAnnotationName")
    public class TestClazz {

        @TestAnnotation(name = "FieldAnnotationName")
        public String field;
    }

    @Test
    public void getAnnotation() throws NoSuchFieldException {
        TestClazz tc = new TestClazz();

        TestAnnotation ta = AnnotationHelper.getClassAnnotation(TestAnnotation.class, TestClazz.class);
        Assert.assertEquals(ta.name(), "ClassAnnotationName");

        TestAnnotation ta1 = AnnotationHelper.getFieldAnnotation(TestAnnotation.class, TestClazz.class, "field");
        Assert.assertEquals(ta1.name(), "FieldAnnotationName");
    }
}
