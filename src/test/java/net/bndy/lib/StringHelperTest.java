package net.bndy.lib;

import org.junit.Assert;
import org.junit.Test;

public class StringHelperTest {

    @Test public void testGenerateRandomString() {
        for(int i = 0; i < 10; i++){
            System.out.println(StringHelper.generateRandomString(10));
        }
        String s = StringHelper.generateRandomString(10);
        Assert.assertEquals(s.length(), 10);
    }

    @Test public void testGenerateRandomCode () {
        String s = StringHelper.generateRandomCode(10);
        System.out.println(s);
        Assert.assertEquals(s.length(), 10);
    }

    @Test public void testCut() {
        Assert.assertEquals(StringHelper.cut("Bndy.Net", 10), "Bndy.Net");
        Assert.assertEquals(StringHelper.cut("Bndy.Net", 3), "Bnd");
        Assert.assertEquals(StringHelper.cut("Bndy.Net", 3, "..."), "Bnd...");
    }
}