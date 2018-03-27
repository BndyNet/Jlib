package net.bndy.lib;

import net.bndy.lib.cache.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCache {


    @AfterClass public static void destroy() {
        Cache.clear();
        Assert.assertEquals(Cache.getSize(), 0);
    }

    @Test public void t1_put() {
        Cache.put("k1", true);

        Assert.assertEquals(Cache.getBoolean("k1"), true);

        Cache.put("k1", false);
        Assert.assertEquals(Cache.getSize(), 1);
        Assert.assertEquals(Cache.get("k1"), false);
    }

    @Test public void t2_duration() throws InterruptedException {
        Cache.put("k2", "k2", 1000);
        Assert.assertEquals(Cache.getString("k2"), "k2");
        Assert.assertEquals(Cache.getSize(), 2);

        Thread.sleep(1001);
        Assert.assertEquals(Cache.getSize(), 1);
        Assert.assertEquals(Cache.getString("k2"), null);
    }

    @Test public void t3_entity() throws InterruptedException {
        TestModel m = new TestModel();
        m.setId(1l);
        m.setName("name1");
        Cache.put("m1", m, 1000);
        Assert.assertEquals(Cache.<TestModel>get("m1").getName(), "name1");

        Thread.sleep(1001);
        Assert.assertEquals(Cache.<TestModel>get("m1"), null);
    }
}
