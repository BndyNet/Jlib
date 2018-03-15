package net.bndy.lib;

import org.junit.Assert;
import org.junit.Test;

public class AnnotationHelperTest {

    @TestAnnotation(name = "ClassAnnotationName")
    public class TestClazz {

        @TestAnnotation(name = "FieldAnnotationName")
        public String field;

        @TestAnnotation(name = "PrivateField")
        private long id;
    }

    @Test
    public void getAnnotation() {
        TestClazz tc = new TestClazz();

        TestAnnotation ta = AnnotationHelper.getClassAnnotation(TestAnnotation.class, TestClazz.class);
        Assert.assertEquals(ta.name(), "ClassAnnotationName");

        TestAnnotation ta1 = AnnotationHelper.getFieldAnnotation(TestAnnotation.class, TestClazz.class, "field");
        Assert.assertEquals(ta1.name(), "FieldAnnotationName");

        TestAnnotation ta2 = AnnotationHelper.getFieldAnnotation(TestAnnotation.class, TestClazz.class, "id");
        Assert.assertEquals(ta2.name(), "PrivateField");
    }
}
