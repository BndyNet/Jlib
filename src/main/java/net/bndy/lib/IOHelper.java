/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.lib;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

/**
 * IO Utils
 */
public class IOHelper {

    public static final String[] EXTENSION_NAMES_FOR_IMAGE = {".bmp", ".gif", ".img", ".jpe", ".jpg", ".jpeg", ".pbm",
        ".png", ".tga", ".tiff"};
    public static final String[] EXTENSION_NAMES_FOR_VIDEO = {".avi", ".flv", ".wmv", ".mov", ".mp4"};
    public static final String[] EXTENSION_NAMES_FOR_AUDIO = {".mp3", ".mav", ".aiff", ".wma"};


    /**
     * Creates thumbnail.
     *
     * @param filename    source image file
     * @param thumbWidth  the thumbnail width
     * @param outFilename the thumbnail file location
     * @throws IOException IOException
     */
    public static void createThumbnail(String filename, int thumbWidth, String outFilename) throws IOException {
        BufferedImage originalBufferedImage = null;
        try {
            File originFile = new File(filename);
            String imageType = originFile.getName().substring(originFile.getName().lastIndexOf(".") + 1);
            originalBufferedImage = ImageIO.read(originFile);

            int thumbnailWidth = thumbWidth;

            int widthToScale, heightToScale;
            if (originalBufferedImage.getWidth() > originalBufferedImage.getHeight()) {

                heightToScale = (int) (1.1 * thumbnailWidth);
                widthToScale = (int) ((heightToScale * 1.0) / originalBufferedImage.getHeight()
                    * originalBufferedImage.getWidth());

            } else {
                widthToScale = (int) (1.1 * thumbnailWidth);
                heightToScale = (int) ((widthToScale * 1.0) / originalBufferedImage.getWidth()
                    * originalBufferedImage.getHeight());
            }

            BufferedImage resizedImage = new BufferedImage(widthToScale, heightToScale,
                originalBufferedImage.getType());
            Graphics2D g = resizedImage.createGraphics();

            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(originalBufferedImage, 0, 0, widthToScale, heightToScale, null);
            g.dispose();

            int x = (resizedImage.getWidth() - thumbnailWidth) / 2;
            int y = (resizedImage.getHeight() - thumbnailWidth) / 2;

            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("Width of new thumbnail is bigger than original image");
            }
            BufferedImage thumbnailBufferedImage = resizedImage.getSubimage(x, y, thumbnailWidth, thumbnailWidth);
            ImageIO.write(thumbnailBufferedImage, imageType.toUpperCase(), new File(outFilename));
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    /**
     * Checks whether the path existed.
     *
     * @param path the path
     * @return true if existed, otherwise false
     */
    public static boolean isPathExisted(String path) {
        return new File(path).exists();
    }

    /**
     * Checks whether the path existed and is a file
     *
     * @param path the file path
     * @return true if existed and is a file, otherwise false
     */
    public static boolean isFileExisted(String path) {
        File f = new File(path);
        return f.exists() && f.isFile();
    }

    /**
     * Checks whether the path existed and is a directory.
     *
     * @param path the path
     * @return true if existed and is a directory, otherwise false
     */
    public static boolean isDirectoryExisted(String path) {
        File f = new File(path);
        return f.exists() && f.isDirectory();
    }

    /**
     * Ensures the directory existed, if does not exists, it will be created.
     *
     * @param path the directory path
     * @return true if the directory existed or created successfully, otherwise false
     */
    public static boolean ensureDirectory(String path) {
        new File(path).mkdirs();
        return true;
    }

    /**
     * Copies from one directory to another directory.
     *
     * @param srcDir  the source directory
     * @param destDir the destination directory
     * @throws IOException IOException
     */
    public static void copyDirectory(String srcDir, String destDir) throws IOException {
        FileUtils.copyDirectory(new File(srcDir), new File(destDir));
    }

    /**
     * Copies a file.
     *
     * @param srcFile  the source file
     * @param destFile the destination file
     * @throws IOException IOException
     */
    public static void copyFile(String srcFile, String destFile) throws IOException {
        FileUtils.copyFile(new File(srcFile), new File(destFile));
    }

    /**
     * Copies one file to destination directory.
     *
     * @param srcFile the source file
     * @param destDir the destination directory
     * @throws IOException IOException
     */
    public static void copyFile2Directory(String srcFile, String destDir) throws IOException {
        FileUtils.copyFileToDirectory(new File(srcFile), new File(destDir));
    }

    /**
     * Gets files and directories in the specified path, and does not include subdirectories and files.
     *
     * @param path the path
     * @return an array typed File
     */
    public static File[] getFilesAndDirectories(String path) {
        return new File(path).listFiles();
    }

    /**
     * Just gets all files in the specified path.
     *
     * @param path the path
     * @return a list typed File
     */
    public static List<File> getFiles(String path) {
        List<File> result = new ArrayList<>();
        for (File f : new File(path).listFiles()) {
            if (f.isFile()) {
                result.add(f);
            }
        }
        return result;
    }

    /**
     * Just gets all directories in the specified path.
     *
     * @param path the path
     * @return a list typed File
     */
    public static List<File> getDirectories(String path) {
        List<File> result = new ArrayList<>();
        for (File f : new File(path).listFiles()) {
            if (f.isDirectory()) {
                result.add(f);
            }
        }
        return result;
    }

    /**
     * Gets all files, directories and subdirectories in the specified path
     *
     * @param path the path
     * @return a list typed File
     */
    public static List<File> getAllFilesAndDirectories(String path) {
        List<File> result = new ArrayList<>();
        for (File f : new File(path).listFiles()) {
            if (f.isFile()) {
                result.add(f);
            } else if (f.isDirectory()) {
                result.addAll(getAllFilesAndDirectories(f.getAbsolutePath()));
            }
        }
        return result;
    }

    /**
     * Gets all files in the specified path, and include files in subdirectories.
     *
     * @param path the path
     * @return a list typed File
     */
    public static List<File> getAllFiles(String path) {
        List<File> result = new ArrayList<>();
        List<File> allFiles = getAllFilesAndDirectories(path);
        for (File f : allFiles) {
            if (f.isFile()) {
                result.add(f);
            }
        }
        return result;
    }

    /**
     * Gets all directories and subdirectories in the specified path.
     *
     * @param path the path
     * @return a list typed File
     */
    public static List<File> getAllDirectories(String path) {
        List<File> result = new ArrayList<>();
        List<File> allFiles = getAllFilesAndDirectories(path);
        for (File f : allFiles) {
            if (f.isDirectory()) {
                result.add(f);
            }
        }
        return result;
    }

    /**
     * Gets the file type by name.
     *
     * @param name the file name
     * @return FileType
     */
    public static FileType getTypeByName(String name) {
        String extensionName = name.toLowerCase().substring(name.indexOf("."));
        if (Arrays.asList(EXTENSION_NAMES_FOR_IMAGE).indexOf(extensionName) >= 0) {
            return FileType.IMAGE;
        } else if (Arrays.asList(EXTENSION_NAMES_FOR_VIDEO).indexOf(extensionName) >= 0) {
            return FileType.VIDEO;
        } else if (Arrays.asList(EXTENSION_NAMES_FOR_AUDIO).indexOf(extensionName) >= 0) {
            return FileType.AUDIO;
        }

        return FileType.UNKNOWN;
    }

    /**
     * Reads text file.
     *
     * @param filePath the file full name
     * @return the file content
     * @throws IOException if an I/O error occurs opening the file
     */
    public static String readTextFile(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath)).collect(Collectors.joining("\n"));
    }

    /**
     * Deletes a file or directory even if the directory has files.
     *
     * @param path The file path or directory path.
     * @return <code>true</code> if and only if the file or directory which existed is
     * successfully deleted; <code>false</code> otherwise or the target does not exist or the target does not exist.
     * @throws IOException If I/O error
     */
    public static boolean forceDelete(String path) throws IOException {
        if (isDirectoryExisted(path)) {
            FileUtils.forceDelete(new File(path));
            return true;
        }
        return false;
    }

    /**
     * Gets current application directory path.
     *
     * @return The path of current application.
     */
    public static String getAppPath() {
        try {
            return new File("").getCanonicalPath();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Gets path.
     *
     * @param path The first path.
     * @param more The more paths.
     * @return The whole path.
     */
    public static String path(String path, String... more) {
        return Paths.get(path, more).toString();
    }

    /**
     * Gets absolute path.
     *
     * @param path The first path.
     * @param more The more paths
     * @return The absolute path.
     */
    public static String absolutePath(String path, String... more) {
        return Paths.get(path, more).toAbsolutePath().toString();
    }

    /**
     * Uses slash (/) to separate paths.
     *
     * @param path The path
     * @return The slashed path.
     */
    public static String slashPath(String path) {
        return path.replaceAll("[\\\\/]+", "/");
    }

    /**
     * Converts a path using current system separator.
     *
     * @param path The path
     * @return The path contains current system separator.
     */
    public static String convert2SysPath(String path) {
        return slashPath(path).replaceAll("[\\\\/]+", File.separator);
    }

    /**
     * Creates a text file with specified file content.
     *
     * @param filePath    The file path
     * @param fileContent The file content
     * @throws IOException If I/O error
     */
    public static void createTextFile(String filePath, String fileContent) throws IOException {
        File file = new File(filePath);
        if (ensureDirectory(file.getParentFile().getCanonicalPath())) {
            if (file.exists()) {
                file.delete();
            }
            Files.write(Paths.get(filePath), fileContent.getBytes(), StandardOpenOption.CREATE);
        }
    }

    /**
     * Appends text to specified file.
     * If the file does not exist then it will be created.
     *
     * @param filePath    The file path
     * @param fileContent The file content
     * @throws IOException If I/O error
     */
    public static void appendTextFile(String filePath, String fileContent) throws IOException {
        File file = new File(filePath);
        if (ensureDirectory(file.getParentFile().getCanonicalPath())) {
            Files.write(Paths.get(filePath), fileContent.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }

    /**
     * Appends text and new line to specified file.
     * If the file does not exist then it will be created.
     *
     * @param filePath    The file path
     * @param fileContent The file content
     * @throws IOException If I/O error
     */
    public static void appendTextFileWithNewLine(String filePath, String fileContent) throws IOException {
        appendTextFile(filePath, fileContent + System.lineSeparator());
    }
}
