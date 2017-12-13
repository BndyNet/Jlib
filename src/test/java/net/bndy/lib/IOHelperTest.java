package net.bndy.lib;

import org.junit.Assert;
import org.junit.Test;

public class IOHelperTest {

    @Test public void testGetFilesAndDirectories() {
        Assert.assertEquals(IOHelper.getFilesAndDirectories(".").length,
                IOHelper.getDirectories(".").size() + IOHelper.getFiles(".").size());
        Assert.assertEquals(IOHelper.getAllFilesAndDirectories(".").size(),
                IOHelper.getAllDirectories(".").size() + IOHelper.getAllFiles(".").size());
    }
}
