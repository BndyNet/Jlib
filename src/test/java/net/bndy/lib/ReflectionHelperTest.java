package net.bndy.lib;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionHelperTest {

    @Test public void newInstance() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        TestModel tm = (TestModel)ReflectionHelper.newInstance(TestModel.class);
        Assert.assertNotNull(tm);
        tm.setId(10l);
        Assert.assertEquals(tm.getId().longValue(), 10l);
    }

    @Test public void setFieldValue() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException {
        TestModel tm = (TestModel)ReflectionHelper.newInstance(TestModel.class);
        Assert.assertNotNull(tm);
        ReflectionHelper.setFieldValue(tm, "id", 12l);
        Assert.assertEquals(tm.getId().longValue(), 12l);

    }
}
