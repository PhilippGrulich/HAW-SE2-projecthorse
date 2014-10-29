/**
 * @author Francis Opoku, Fabian Reiber 
 * */

package com.haw.projecthorse.assetmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.haw.projecthorse.assetmanager.exceptions.*;

public final class AssetManager {

	private static String assetDir = "";
	private static final String FILESEPARATOR = System
			.getProperty("file.separator");
	private static final String FOLDERNAME_SOUNDS = "sounds";
	private static final String FOLDERNAME_MUSIC = "music";
	private static final String FOLDERNAME_PICTURES = "pictures";
	private static String directory_sounds;
	private static String directory_music;
	private static String directory_pictures;
	private static com.badlogic.gdx.assets.AssetManager assetManager = new com.badlogic.gdx.assets.AssetManager();
	private static float soundVolume = 1;
	private static float musicVolume = 1;
	
	//Mapped mit LevelID auf TextureAtlas - Haelt AtlasObjekte Vorraetig zwecks Performance
	private static Map<String, TextureAtlas> administratedAtlases = new HashMap<String, TextureAtlas>();
	//Mapped mit LevelID auf Pfade von Atlas Objekten im Falle von Verlust eines Atlas bei dispose (Performance)
	private static Map<String, String> administratedAtlasesPath = new HashMap<String, String>();
	//Mapped mit LevelID auf Pfade von Sound / Music sofern 1 Mal vorher darauf zugegriffen wurde.
	//da laden aller Sounds / Music am Anfang, wie bei Atlas Dateien, nicht Sinnvoll.
	private static Map<String, ArrayList<String>> administratedSoundPath;
	private static Map<String, ArrayList<String>> administratedMusicPath;
	
	private static TextureRegion errorPic;

	
	public static void initialize(){
		setApplicationRoot();
		loadAtlases(directory_pictures, directory_pictures);
		loadAudioPaths();
	}
	
	/**
	 * Setzen des root-Verzeichnisses. Wird fuer den Zugriff auf Assets
	 * benoetigt. Weiterhin werden die Sound, Music und Texture Pfade gesetzt
	 */
	private static void setApplicationRoot() {
		if (Gdx.app.getType() == ApplicationType.Android) {

			assetDir = "";
		} else if (Gdx.app.getType() == ApplicationType.Desktop) {

			assetDir = System.getProperty("user.dir") + FILESEPARATOR + "bin"
					+ FILESEPARATOR;
		} else {
			System.out.println("In AssetManager: No android or desktop device");
		}
		
		directory_sounds = assetDir + FOLDERNAME_SOUNDS  ;
		directory_music = assetDir + FOLDERNAME_MUSIC ;
		directory_pictures = assetDir + FOLDERNAME_PICTURES ;
	
	}
	
	/**
	 * HashMap fuer Pfade zu Sound- und Musikdateien erzeugen,
	 * Methode fuer rekursiven Abstieg in Verzeichnisstruktur aufrufen.
	 */
	private static void loadAudioPaths(){
		administratedSoundPath = new HashMap<String, ArrayList<String>>();
		administratedMusicPath = new HashMap<String, ArrayList<String>>();
		loadAudioPaths(directory_sounds, directory_sounds, Assets.SOUNDS);
		loadAudioPaths(directory_music, directory_music, Assets.MUSIC);
	}
	
	/**
	 * 
	 * Arbeitet rekursiv, durchsucht Pfad path nach Audiodateien, 
	 * ruft bei Auffinden von Audiodatei chooseAudioMap auf.
	 * @param path Pfad zum Verzeichnis dessen Inhalt nach Audiodateien durchsucht wird.
	 * @param levelID Name des aktuellen Verzeichnisses.
	 * @param type Assets.SOUND oder Assets.MUSIC
	 */
	private static void loadAudioPaths(String path, String levelID, Assets type) {
		FileHandle[] files = Gdx.files.internal(path).list();
		for (FileHandle file : files) {
			if (file.isDirectory()) {
				System.out.println("AssetManager.loadAudioPaths: file.name(): "
						+ file.name() + " is dir");
				loadAudioPaths(path + FILESEPARATOR + file.name(), file.name(), type);
			} else {
				if (file.name().toLowerCase()
						.matches(".*(\\.mp3|\\.wav|\\.ogg)$")) {
					chooseAudioMap(levelID, path + FILESEPARATOR + file.name(), type);
				}
			}
		}
	}
	
	/**
	 * Fuer type == Assets.Sound wird (levelID, Path) als (Key, Value) in administratedSoundPaths abgelegt.
	 * Fuer type == Assets.Music analog.
	 * @param levelID Name des Ordners
	 * @param path
	 * @param type
	 */
	private static void chooseAudioMap(String levelID, String path, Assets type){
		if(type == Assets.SOUNDS){
			if (!administratedSoundPath.containsKey(levelID)) {
				administratedSoundPath.put(levelID,
						new ArrayList<String>());
			}
			int startIdx = path.indexOf(FOLDERNAME_SOUNDS);
			path = path.substring(startIdx, path.length()).replace("\\",
					"/");
		administratedSoundPath.get(levelID).add(path);
		}else if(type == Assets.MUSIC){
			if (!administratedMusicPath.containsKey(levelID)) {
				administratedMusicPath.put(levelID,
						new ArrayList<String>());
			}
			int startIdx = path.indexOf(FOLDERNAME_MUSIC);
			path = path.substring(startIdx, path.length()).replace("\\",
					"/");
			administratedMusicPath.get(levelID).add(path);
		}
	}
	
	/**
	 * Laedt alle Dateien von levelID die im sounds Ordner sind. 
	 * @param levelID ID des Levels.
	 */
	public static void loadSounds(String levelID){
		System.out.println("loadSounds: " + levelID);
		ArrayList<String> soundsPaths = administratedSoundPath.get(levelID);
		if(soundsPaths == null){
			System.out.println("Sounds fuer levelID: " + levelID + " nicht gefunden.");
		}else{
			for(String path : soundsPaths){
				System.out.println("loadSounds: " + path);
				assetManager.load(path, Sound.class);
				System.out.println("" + path + " geladen? " + assetManager.isLoaded(path));
			}
		}
		assetManager.finishLoading();
	}
	
	/**
	 * Laedt alle Dateien von levelID die im music Ordner sind. 
	 * @param levelID ID des Levels.
	 */
	public static void loadMusic(String levelID){
		System.out.println("loadMusic: " + levelID);
		ArrayList<String> musicPaths = administratedMusicPath.get(levelID);
		if(musicPaths == null){
			System.out.println("Music fuer levelID: " + levelID + " nicht gefunden.");
		}else{
			for(String path : musicPaths){
				System.out.println("loadMusic: " + path);
				assetManager.load(path, Music.class);
				System.out.println("" + path + " geladen? " + assetManager.isLoaded(path));
			}
		}
		assetManager.finishLoading();
	}
	
	/**
	 * Laedt einmal beim Start der Anwendung alle .atlas-Dateien in den Speicher.
	 * @param path Pfad zum Parent-Directory der Bilder.
	 * @param levelID path Pfad zum Parent-Directory der Bilder.
	 */
	private static void loadAtlases(String path, String levelID) {
		FileHandle[] files = Gdx.files.internal(path).list();
		for (FileHandle file : files) {
			if (file.name().matches(".*\\.atlas")) {
				int startIdx = path.indexOf(FOLDERNAME_PICTURES);
				String relativeFilePath = path.substring(startIdx,
						path.length()).replace("\\", "/")
						+ "/" + file.name();
				// assets = new
				// TextureAtlas(Gdx.files.internal(relativeFilePath));
				TextureAtlas atlas = new TextureAtlas(
						Gdx.files.internal(relativeFilePath));
				assetManager.load(relativeFilePath, TextureAtlas.class);
				administratedAtlasesPath.put(levelID, relativeFilePath);
				administratedAtlases.put(levelID, atlas);

			} else {
				loadAtlases(path + FILESEPARATOR + file.name(), file.name());
			}
		}
	}

	/**
	 * Liefert BitmapFont einer .fnt Datei
	 * @param levelID 
	 * @param filename
	 * @return b BitmapFont
	 */
	public static BitmapFont getFont(String levelID, String filename){
		int startIdx = directory_pictures.indexOf(FOLDERNAME_PICTURES);
		String relativeFilePath = directory_pictures.substring(startIdx,
				directory_pictures.length()).replace("\\", "/")
				 + FILESEPARATOR + levelID + "/" + filename + ".fnt";
		System.out.println(relativeFilePath);
		TextureRegion t = getTextureRegion(levelID, filename);
		
		BitmapFont b = new BitmapFont(Gdx.files.internal(relativeFilePath), t);
		return b;
	}

	/**
	 * Zerstoert den Assetmanager, disposed alle Assets.
	 */
	public static void disposeAll() {
		assetManager.dispose();
	}

	/**
	 * Disposed die Assets die durch den Atlas referenziert sind.
	 * 
	 * @param levelID
	 *            Die Level ID
	 * @param atlas
	 *            Der Atlas, den man beim load(...) erhalten hat.
	 */
	public static void disposeAtlas(String levelID, String atlas) {
		assetManager.unload(atlas);
		administratedAtlases.remove(levelID);
	}

	/**
	 * Spielt Sound ab, indem der Sound in den Arbeitsspeicher geladen wird. Der
	 * Pfad zur Sounddatei startet relativ zum Oberordner mit der levelID. Z.B.
	 * liegt die Datei in sounds/mainMenu/abc/flap.wav dann ist der name als
	 * abc/flap.wav anzugeben.
	 * 
	 * @param name
	 *            Pfad zur Sounddatei
	 * @param levelID
	 *            Die ID des Levels
	 */
	public static void playSound(String levelID, String name) {
		assetManager.get(FOLDERNAME_SOUNDS + "/" + levelID + "/" + name,
				Sound.class).play(soundVolume);
	}

	/**
	 * Aendert die Soundlautst�rke, indem die Spieltlautstaerke fuer Sounds neu
	 * gesetzt wird
	 * 
	 * @param volume
	 *            Die Lautstärke des Sounds im Bereich [0,1]. 0 ist stumm.
	 */
	public static void changeSoundVolume(float volume) {
		soundVolume = volume;
	}

	/**
	 * Spielt die Musikdatei ab, indem der Sound in den Arbeitsspeicher geladen
	 * wird. Der Pfad zur Sounddatei startet relativ zum Oberordner mit der
	 * levelID. Z.B. liegt die Datei in music/mainMenu/asdfa/flap.wav dann ist
	 * der name als asdfa/life.wav anzugeben.
	 * 
	 * @param leveldID
	 *            ID des Levels
	 * @param name
	 *            Name der Datei ink. relativem Pfad (ohne Oberordner mit Namen
	 *            "levelID"
	 */
	public static void playMusic(String levelID, String name) {
		assetManager.get(FOLDERNAME_MUSIC + "/" + levelID + "/" + name,
				Music.class).play();
		assetManager.get(FOLDERNAME_MUSIC + "/" + levelID + "/" + name,
				Music.class).setVolume(musicVolume);
	}

	/**
	 * Schaltet die Musik aus, indem sie angehalten wird.
	 * 
	 * @param levelID
	 *            ID des Levels
	 * @param name
	 *            Name der Datei inkl. relativem Pfad (ohne Oberordner mit Namen
	 *            "levelID").
	 */
	public static void turnMusicOff(String levelID, String name) {
		assetManager.get(FOLDERNAME_MUSIC + "/" + levelID + "/" + name,
				Music.class).stop();
	}

	/**
	 * Aendert die Musiklautstärke und setzt die Spiellautstaerke auf neuen
	 * Wert
	 * 
	 * @param levelID
	 *            ID des Levels
	 * @param name
	 *            Name der Datei inkl. relativem Pfad (ohne Oberordner mit Namen
	 *            "levelID").
	 * @param volume
	 *            Float-Wert zwischen [0,1]. 0 ist Stumm, 1 volle Lautstärke.
	 */
	public static void changeMusicVolume(String levelID, String name,
			float volume) {
		assetManager.get(FOLDERNAME_MUSIC + "/" + levelID + "/" + name,
				Music.class).setVolume(volume);
		musicVolume = volume;
	}

	/**
	 * Greift auf den TextrueAtlas der levelID zu und liefert die TextureRegion
	 * "filename"
	 * 
	 * @param levelID
	 *            ID des Levels
	 * @param filename
	 *            Dateiname
	 * @return result Ist die TextureRegion, sonst ein rotes Kreuz zur
	 *         Fehleranzeige
	 */
	public static TextureRegion getTextureRegion(String levelID, String filename) {
		AtlasRegion atlasRegion;

		if(!administratedAtlases.containsKey(levelID)){
			if(!administratedAtlasesPath.containsKey(levelID)){
				System.out.println("TextureAtlas + " + levelID + " existiert nicht.");
			}else{
				assetManager.load(administratedAtlasesPath.get(levelID), TextureAtlas.class);
				administratedAtlases.put(levelID, new TextureAtlas(
						Gdx.files.internal(administratedAtlasesPath.get(levelID))));
				assetManager.finishLoading();
			}
		}
		
		try {
			if (administratedAtlases.get(levelID).findRegion(filename) != null) {
				atlasRegion = administratedAtlases.get(levelID).findRegion(
						filename);
				Texture page = atlasRegion.getTexture();
				TextureRegion result = new TextureRegion(page,
						atlasRegion.getRegionX(), atlasRegion.getRegionY(),
						atlasRegion.getRegionWidth(),
						atlasRegion.getRegionHeight());
				return result;
			} else {
				throw new TextureNotFoundException("Bild " + filename
						+ " nicht gefunden.");
			}
		} catch (TextureNotFoundException e) {
			e.printStackTrace();
		}
		return errorPic;
	}

	/**
	 * Assettypen die zur Zeit unterstuetz werden.
	 *
	 */
	public enum Assets {
		SOUNDS, MUSIC, PICTURES
	}

	
	/***********************************************************************************************
	 ***********************************************************************************************
	 *********************************Lizenz-File-Handling******************************************
	 ***********************************************************************************************
	 ***********************************************************************************************
	 */
	public static void checkLicenses() {
		String[] licenseType = { "cc-0_license", "cc-by_license",
				"selfmade_license" };
		checkFiles(readLicensesAndSplitEntries(licenseType));
	}

	/**
	 * Liest aus den Lizenzdateien die Zeilen aus und splittet deren Eintraege
	 * getrennt nach dem Semikolon. Somit kann auf das 2D Array, auf jede
	 * Zeichenkette die in der Textdatei durch das Semikolon getrennt ist,
	 * zugegriffen werden
	 * 
	 * @param licenseTypes
	 *            cc-0, cc-by oder selfmade wird bei Aufruf automatisch
	 *            uebergeben
	 * @return Key:=licenseType Values:=2D Array das die Zeilen im txt-File
	 *         repraesentiert
	 */
	private static Map<String, String[][]> readLicensesAndSplitEntries(
			final String[] licenseTypes) {
		List<String> stringList = new ArrayList<String>();
		String licenseDir = System.getProperty("user.dir") + FILESEPARATOR
				+ ".." + FILESEPARATOR + "android" + FILESEPARATOR + "assets";
		Map<String, String[][]> stringMap = new HashMap<String, String[][]>();

		// **************************************************
		// Pictures Lizenzen
		// **************************************************

		// Zeilen aus den Lizenztextdateien lesen und
		// einer Liste hinzufuegen
		for (String item : licenseTypes) {
			try {
				BufferedReader bufReadPic = new BufferedReader(new FileReader(
						licenseDir + FILESEPARATOR + FOLDERNAME_PICTURES
								+ FILESEPARATOR + item + ".txt"));
				String fileLinePic = null;
				while ((fileLinePic = bufReadPic.readLine()) != null) {
					stringList.add(fileLinePic);
				}
				bufReadPic.close();
			} catch (FileNotFoundException e) {
				System.out.println("License-file not found");
			} catch (IOException e) {
				System.out.println("License-file couldn't read");
			}
		}
		stringMap.put(FOLDERNAME_PICTURES, createSeperatedEntries(stringList));
		stringList.clear();

		// **************************************************
		// Sound Lizenzen
		// **************************************************

		stringList = readLicense(licenseDir + FILESEPARATOR + FOLDERNAME_SOUNDS
				+ FILESEPARATOR + FOLDERNAME_SOUNDS + ".txt");
		stringMap.put(FOLDERNAME_SOUNDS, createSeperatedEntries(stringList));
		stringList.clear();

		// **************************************************
		// Music LizenzenfolderName
		// **************************************************

		stringList = readLicense(licenseDir + FILESEPARATOR + FOLDERNAME_MUSIC
				+ FILESEPARATOR + FOLDERNAME_MUSIC + ".txt");
		stringMap.put(FOLDERNAME_MUSIC, createSeperatedEntries(stringList));

		return stringMap;
	}

	/**
	 * Hilfsfunktion die die einzelnen Zeilen aus den Lizenzdateien liest
	 * 
	 * @param licenseDir
	 *            Pfad zur Lizenzdatei
	 * @return Liste der gelesenen Zeilen aus der Lizenzdatei
	 */
	private static List<String> readLicense(String licenseDir) {
		List<String> stringList = new ArrayList<String>();

		try {
			BufferedReader bufRead = new BufferedReader(new FileReader(
					licenseDir));
			String fileLine = null;
			while ((fileLine = bufRead.readLine()) != null) {
				stringList.add(fileLine);
			}
			bufRead.close();
		} catch (FileNotFoundException e) {
			System.out.println("License-file not found");
		} catch (IOException e) {
			System.out.println("License-file couldn't read");
		}
		return stringList;
	}

	/**
	 * Hilfsfunktion um die mit Semikolon getrennten Eintraege zu seperieren
	 * 
	 * @param stringList
	 *            Liste der gelesenen Zeilen aus der Lizenzdatei
	 * @return 2D-Array mit seperierten Eintraegen
	 */
	private static String[][] createSeperatedEntries(List<String> stringList) {
		final int maxLicenseLineSize = 5;
		String[] listLine;
		// aufsplitten der Zeilen, anhand des Semikolons
		// und abspeichern in ein 2D-Array. Anschließendes
		// einfuegen in die HashMap
		String[][] seperatedEntries = new String[stringList.size()][maxLicenseLineSize];
		for (int i = 0; i < stringList.size(); i++) {
			listLine = stringList.get(i).split(";");
			for (int j = 0; j < listLine.length; j++) {
				seperatedEntries[i][j] = listLine[j].trim();
			}
		}
		return seperatedEntries;
	}

	/**
	 * Geht durch die jeweiligen Level Ordner und prueft, ob die Dateien, welche
	 * in den Lizenztextdateien aufgelistet sind, vorhanden sind. Weiterhin wird
	 * geprueft, ob ein Lizenzeintrag in den Lizenztextdateien enthalten ist.
	 * Die Ergebnisse werden in einer logfile.txt im pictures Ordner gespeichert
	 * 
	 * @param stringMap
	 *            Key:=licenseType Values:=2D Array das die Zeilen im txt-File
	 *            repraesentiert
	 */
	private static void checkFiles(final Map<String, String[][]> stringMap) {
		String licenseDir = System.getProperty("user.dir") + FILESEPARATOR
				+ ".." + FILESEPARATOR + "android" + FILESEPARATOR + "assets";

		String[][] pictures = stringMap.get(FOLDERNAME_PICTURES);
		String[][] sounds = stringMap.get(FOLDERNAME_SOUNDS);
		String[][] music = stringMap.get(FOLDERNAME_MUSIC);

		File checkFile = null;

		try {
			FileWriter fWriter = new FileWriter(licenseDir + FILESEPARATOR
					+ "logfile.txt");
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			bWriter.write("Logfile for license check of assets");
			bWriter.newLine();
			bWriter.write("***PICTURES***");
			bWriter.newLine();

			// **************************************************
			// Pictures Eintraege
			// **************************************************
			for (int i = 0; i < pictures.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR
						+ FOLDERNAME_PICTURES + FILESEPARATOR + pictures[i][0]
						+ FILESEPARATOR + pictures[i][1]);
				writeFile(FOLDERNAME_PICTURES, pictures, checkFile, bWriter, i);
			}

			bWriter.write("***SOUNDS**");
			bWriter.newLine();

			// **************************************************
			// Sounds Eintraege
			// **************************************************
			for (int i = 0; i < sounds.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR
						+ FOLDERNAME_SOUNDS + FILESEPARATOR + sounds[i][0]
						+ FILESEPARATOR + sounds[i][1]);
				writeFile(FOLDERNAME_SOUNDS, sounds, checkFile, bWriter, i);
			}

			bWriter.write("***MUSIC***");
			bWriter.newLine();

			// **************************************************
			// Music Eintraege
			// **************************************************
			for (int i = 0; i < music.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR
						+ FOLDERNAME_MUSIC + FILESEPARATOR + music[i][0]
						+ FILESEPARATOR + music[i][1]);
				writeFile(FOLDERNAME_MUSIC, music, checkFile, bWriter, i);
			}

			bWriter.close();
			fWriter.close();

		} catch (IOException e) {
			System.out.println("Couldn't generate a logfile!");
		}
	}

	/**
	 * Hilfsfunktion um in die log-Datei zu schreiben
	 * 
	 * @param type
	 *            jeweiliger Assettyp
	 * @param stringFile
	 *            erstelltes 2D-Array mit seperierten Eintraegen
	 * @param checkFile
	 *            Pfad zu den jeweiligen Asset-Ordnern
	 * @param bWriter
	 *            BufferedWriter
	 * @param i
	 *            Laufvariable
	 */
	private static void writeFile(String type, String[][] stringFile,
			File checkFile, BufferedWriter bWriter, int i) {
		try {
			// Pruefen, ob Datei im jeweiligen Ordner
			// vorhanden ist
			if (checkFile.exists()) {
				bWriter.write(stringFile[i][1] + " FILE OK!");
			} else {
				bWriter.write(stringFile[i][1] + " NOT EXISTS!");
			}
			bWriter.newLine();

			// Lizensierung pruefen
			if (type.equals(FOLDERNAME_PICTURES)) {
				if (stringFile[i][2].toLowerCase().matches(
						"cc-0|cc-by|selfmade")) {
					bWriter.write(stringFile[i][2] + " ENTRY OK!");
				} else {
					bWriter.write(stringFile[i][2] + " NOT VALID!");
				}
				bWriter.newLine();
			}
		} catch (IOException e) {
			System.out.println("Couldn't write to logfile!");
		}
	}
}