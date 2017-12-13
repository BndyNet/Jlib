package net.bndy.lib;

import org.junit.Assert;
import org.junit.Test;

public class StringHelperTest {

    @Test public void testGenerateRandomString() {
        Assert.assertEquals(StringHelper.generateRandomString(10).length(), 10);
    }

    @Test public void testCut() {
        Assert.assertEquals(StringHelper.cut("Bndy.Net", 10), "Bndy.Net");
        Assert.assertEquals(StringHelper.cut("Bndy.Net", 3), "Bnd");
        Assert.assertEquals(StringHelper.cut("Bndy.Net", 3, "..."), "Bnd...");
    }
}