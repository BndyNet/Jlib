package net.bndy.lib;

import org.junit.Assert;
import org.junit.Test;

public class HttpHelperTest {

    @Test
    public void httpGet() throws Exception {
        String s = HttpHelper.requestGet("http://www.hashcollision.org/hkn/python/idle_intro/index.html");
        System.out.println(s);
        Assert.assertTrue(s.indexOf("<!DOCTYPE") == 0);
    }
}
