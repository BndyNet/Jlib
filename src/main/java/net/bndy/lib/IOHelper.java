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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class IOHelper {
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

	public static boolean isPathExisted(String path) {
	    return new File(path).exists();
	}

	public static boolean isFileExisted(String path) {
	    File f = new File(path);
	    return f.exists() && f.isFile();
	}

	public static boolean isDirectoryExisted(String path) {
		File f = new File(path);
		return f.exists() && f.isDirectory();
	}

	public static boolean ensureDirectory(String path) {
	    return new File(path).mkdirs();
	}

	public static void copyDirectory(String srcDir, String destDir) throws IOException {
        FileUtils.copyDirectory(new File(srcDir), new File(destDir));
	}

	public static void copyFile(String srcFile, String destFile) throws IOException {
		FileUtils.copyFile(new File(srcFile), new File(destFile));
	}

	public static void copyFile2Directory(String srcFile, String destDir) throws IOException {
        FileUtils.copyFileToDirectory(new File(srcFile), new File(destDir));
	}

	public static File[] getFilesAndDirectories(String path) {
		return new File(path).listFiles();
	}

	public static List<File> getFiles(String path) {
	    List<File> result = new ArrayList<>();
	    for (File f: new File(path).listFiles()) {
	    	if (f.isFile()) {
	    		result.add(f);
			}
		}
		return result;
	}

	public static List<File> getDirectories(String path) {
		List<File> result = new ArrayList<>();
		for (File f: new File(path).listFiles()) {
			if (f.isDirectory()) {
				result.add(f);
			}
		}
		return result;
	}

	public static List<File> getAllFilesAndDirectories(String path) {
		List<File> result = new ArrayList<>();
		for (File f: new File(path).listFiles()) {
			if (f.isFile()) {
				result.add(f);
			} else if (f.isDirectory()) {
				result.addAll(getAllFilesAndDirectories(f.getAbsolutePath()));
			}
		}
		return  result;
	}

	public static List<File> getAllFiles(String path) {
	    List<File> result = new ArrayList<>();
		List<File> allFiles = getAllFilesAndDirectories(path);
		for(File f: allFiles) {
			if (f.isFile()) {
				result.add(f);
			}
		}
		return result;
	}

	public static List<File> getAllDirectories(String path) {
		List<File> result = new ArrayList<>();
		List<File> allFiles = getAllFilesAndDirectories(path);
		for(File f: allFiles) {
			if (f.isDirectory()) {
				result.add(f);
			}
		}
		return result;
	}
}
