package net.bndy.lib;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class IOHelperTest {

    @Test public void testGetFilesAndDirectories() {
        Assert.assertEquals(IOHelper.getFilesAndDirectories(".").length,
                IOHelper.getDirectories(".").size() + IOHelper.getFiles(".").size());
        Assert.assertEquals(IOHelper.getAllFilesAndDirectories(".").size(),
                IOHelper.getAllDirectories(".").size() + IOHelper.getAllFiles(".").size());
    }

    @Test public void getAppPath() {
        String path = IOHelper.getAppPath();
        System.out.println(path);
        Assert.assertNotNull(path);
    }

    @Test public void slashPath() {
        String path = IOHelper.slashPath("C:\\\\a\\\\b\\cc//d/e");
        System.out.println(">>" + path);
        System.out.println(">>" + IOHelper.convert2SysPath(path));
        Assert.assertNotNull(path);
    }

    @Test public void textFileOperation() throws IOException {
        String file = "./dddd/a/b/c/a.txt";
        IOHelper.appendTextFileWithNewLine(file, "content with line");
        IOHelper.forceDelete("./dddd");
    }
}
