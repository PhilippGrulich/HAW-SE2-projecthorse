/**
 * @author Francis Opoku, Fabian Reiber 
 * */

package com.haw.projecthorse.desktop;

import java.io.File;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

/**
 * 
 * Bei Änderungen der Assets, einmal die Packer-main ausfuehren, damit eine
 * neue *.atlas-Datei erstellt wird. Anschließend unter Project -> Clean
 * Projekt einmal cleanen
 *
 */

public class Packer {

	public static final String filesep = System.getProperty("file.separator");
	public static final String root = System.getProperty("user.dir") + filesep
			+ ".." + filesep + "android" + filesep + "assets" + filesep
			+ "pictures";

	public static void main(String[] arg) {
		packImages();
	}

	public static void packImages() {
		File dir = new File(root);
		String inputDir = "";
		String outputDir = "";
		String outputFile = "";
		String[] content = dir.list();
		Settings settings = new Settings();
		settings.maxWidth = 2560;
		settings.maxHeight = 2000;
		settings.pot = false;

		for (int i = 0; i < content.length; i++) {
			if (new File(root + filesep + content[i]).isDirectory()) {
				inputDir = root + filesep + content[i];
				outputDir = root + filesep + content[i];
				outputFile = content[i];
				TexturePacker.processIfModified(settings, inputDir, outputDir,
						outputFile);
			}
		}

	}
}
