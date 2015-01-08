package com.haw.projecthorse.assetmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.haw.projecthorse.assetmanager.exceptions.TextureNotFoundException;

/**
 * Im AssetManager werden alle noetigen Bilder, Sounds und Musikdatein verwaltet. 
 * Insbesondere der Zugriff auf Bilder wird hier verwaltet. Sound und Musik laufen 
 * ueberwiegend ueber den AudioManager.
 * @author Francis Opoku und Fabian Reiber
 * @version 1.0
 *
 */
public final class AssetManager {

	private static String assetDir = "";
	private static final String FILESEPARATOR = System.getProperty("file.separator");
	private static final String FOLDERNAME_SOUNDS = "sounds";
	private static final String FOLDERNAME_MUSIC = "music";
	private static final String FOLDERNAME_PICTURES = "pictures";
	private static final String FOLDERNAME_FONTS = "fonts";
	private static final String FOLDERNAME_TEXTS = "texts";
	private static String directorySounds;
	private static String directoryMusic;
	private static String directoryPictures;
	private static String directoryFonts;
	public static com.badlogic.gdx.assets.AssetManager assetManager;
	// Mapped mit LevelID auf TextureAtlas - Haelt AtlasObjekte Vorraetig zwecks
	// Performance
	private static Map<String, TextureAtlas> administratedAtlases;
	// Mapped mit LevelID auf Pfade von Atlas Objekten im Falle von Verlust
	// eines Atlas bei dispose (Performance)
	private static Map<String, String> administratedAtlasesPath;
	// Mapped mit LevelID auf Pfade von Sound / Music sofern 1 Mal vorher darauf
	// zugegriffen wurde.
	// da laden aller Sounds / Music am Anfang, wie bei Atlas Dateien, nicht
	// Sinnvoll.
	private static Map<String, ArrayList<String>> administratedSoundPath;
	private static Map<String, ArrayList<String>> administratedMusicPath;
	
	private static TextureRegion errorPic;
	
	private static BitmapFont[] textFonts, headlineFonts;

	/**
	 * Privater Konstruktor, da Utility-Klassen keinen Default oder anderen
	 * oeffentlichen Konstruktor besitzen sollte.
	 */
	private AssetManager(){}
	
	/**
	 * Initialisierung aller noetigen Datenstrukturen.
	 */
	public static void initialize() {
		assetManager = new com.badlogic.gdx.assets.AssetManager();
		administratedAtlases = new HashMap<String, TextureAtlas>();
		administratedAtlasesPath = new HashMap<String, String>();
		setApplicationRoot();
		loadAtlases(directoryPictures, directoryPictures);
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

			assetDir = System.getProperty("user.dir") + FILESEPARATOR + "bin" + FILESEPARATOR;
		} else {
			Gdx.app.log("AssetManager", "No android or desktop device");
		}

		directorySounds = assetDir + FOLDERNAME_SOUNDS;
		directoryMusic = assetDir + FOLDERNAME_MUSIC;
		directoryPictures = assetDir + FOLDERNAME_PICTURES;
		directoryFonts = assetDir + FOLDERNAME_FONTS;

	}

	/**
	 * HashMap fuer Pfade zu Sound- und Musikdateien erzeugen, Methode fuer
	 * rekursiven Abstieg in Verzeichnisstruktur aufrufen.
	 */
	private static void loadAudioPaths() {
		administratedSoundPath = new HashMap<String, ArrayList<String>>();
		administratedMusicPath = new HashMap<String, ArrayList<String>>();
		loadAudioPaths(directorySounds, directorySounds, Assets.SOUNDS);
		loadAudioPaths(directoryMusic, directoryMusic, Assets.MUSIC);
	}

	/**
	 * 
	 * Arbeitet rekursiv, durchsucht Pfad path nach Audiodateien, ruft bei
	 * Auffinden von Audiodatei chooseAudioMap auf.
	 * 
	 * @param path
	 *            Pfad zum Verzeichnis dessen Inhalt nach Audiodateien
	 *            durchsucht wird.
	 * @param levelID
	 *            Name des aktuellen Verzeichnisses.
	 * @param type
	 *            Assets.SOUND oder Assets.MUSIC
	 */
	private static void loadAudioPaths(final String path, final String levelID, final Assets type) {
		// Nur ein einfacher kleiner Thread. Da es nicht sehr schlimm ist wenn
		// Audio erst etwas später zur Verfügung steht.

		FileHandle[] files = Gdx.files.internal(path).list();
		for (final FileHandle file : files) {

			if (file.isDirectory()) {
				Gdx.app.log("AssetManager", "AssetManager.loadAudioPaths: file.name(): " + file.name() + " is dir");

				loadAudioPaths(path + FILESEPARATOR + file.name(), file.name(), type);

			} else {
				if (file.name().toLowerCase().matches(".*(\\.mp3|\\.wav|\\.ogg)$")) {
					chooseAudioMap(levelID, path + FILESEPARATOR + file.name(), type);
				}
			}
		}

	}

	/**
	 * Fuer type == Assets.Sound wird (levelID, Path) als (Key, Value) in
	 * administratedSoundPaths abgelegt. Fuer type == Assets.Music analog.
	 * 
	 * @param levelID
	 *            Name des Ordners
	 * @param path Pfad des jeweiligen Assets
	 * @param type Ein AssetType
	 */
	private static void chooseAudioMap(final String levelID, final String path, final Assets type) {
		String pathNew = "";
		if (type == Assets.SOUNDS) {
			if (!administratedSoundPath.containsKey(levelID)) {
				administratedSoundPath.put(levelID, new ArrayList<String>());
			}
			int startIdx = path.indexOf(FOLDERNAME_SOUNDS);
			pathNew = path.substring(startIdx, path.length()).replace("\\", "/");
			administratedSoundPath.get(levelID).add(pathNew);
		} else if (type == Assets.MUSIC) {
			if (!administratedMusicPath.containsKey(levelID)) {
				administratedMusicPath.put(levelID, new ArrayList<String>());
			}
			int startIdx = path.indexOf(FOLDERNAME_MUSIC);
			pathNew = path.substring(startIdx, path.length()).replace("\\", "/");
			administratedMusicPath.get(levelID).add(pathNew);
		}
	}

	/**
	 * Laedt alle Dateien von levelID die im sounds Ordner sind.
	 * 
	 * @param levelID
	 *            ID des Levels.
	 */
	public static void loadSounds(final String levelID) {
		Gdx.app.log("AssetManager", "loadSounds: " + levelID);
		ArrayList<String> soundsPaths = administratedSoundPath.get(levelID);
		if (soundsPaths == null) {
			Gdx.app.log("AssetManager", "Sounds fuer levelID: " + levelID + " nicht gefunden.");
		} else {
			for (String path : soundsPaths) {
				Gdx.app.log("AssetManager", "loadSounds: " + path);
				assetManager.load(path, Sound.class);
				Gdx.app.log("AssetManager", "" + path + " geladen? " + assetManager.isLoaded(path));
			}
		}
		assetManager.finishLoading();
	}

	/**
	 * Laedt alle Dateien von levelID die im music Ordner sind.
	 * 
	 * @param levelID
	 *            ID des Levels.
	 */
	public static void loadMusic(final String levelID) {
		Gdx.app.log("AssetManager", "loadMusic: " + levelID);
		ArrayList<String> musicPaths = administratedMusicPath.get(levelID);
		if (musicPaths == null) {
			Gdx.app.log("AssetManager", "Music fuer levelID: " + levelID + " nicht gefunden.");
		} else {
			for (String path : musicPaths) {
				Gdx.app.log("AssetManager", "loadMusic: " + path);
				assetManager.load(path, Music.class);
				Gdx.app.log("AssetManager", "" + path + " geladen? " + assetManager.isLoaded(path));
			}
		}
		assetManager.finishLoading();
	}

	/**
	 * Liefert ein LibGDX Sound Objekt. Sollte nur im AudioManager Modul genutzt
	 * werden.
	 * 
	 * @param levelID LevelID des Levels
	 * @param name Bezeichner der Sounddatei
	 * @return Sound-Objekt in Abhaengigkeit der LevelID und der Bezeichnung des Sounds
	 */
	public static Sound getSound(final String levelID, final String name) {
		return assetManager.get(FOLDERNAME_SOUNDS + "/" + levelID + "/" + name, Sound.class);
	}

	/**
	 * Liefert ein LibGDX Music Objekt. Sollte nur im AudioManager Modul genutzt
	 * werden.
	 * 
	 * @param levelID LevelID des Levels
	 * @param name Bezeichner der Musikdatei
	 * @return Music-Objekt in Abhaengigkeit der LevelID und der Bezeichnung der Musik
	 */
	public static Music getMusic(final String levelID, final String name) {
		return assetManager.get(FOLDERNAME_MUSIC + "/" + levelID + "/" + name, Music.class);
	}

	/**
	 * Laedt einmal beim Start der Anwendung alle Pfade der .atlas-Dateien in
	 * eine Hashmap. Erst beim ersten Nutzen der Hashmap wird der Speicher
	 * Reserviert.
	 * 
	 * Es wird aus Performancegründen nur die erste Ebenene des pictures
	 * Verzeichnisses gescannt. Außerdem wurden möglichst wenig Funktionenen des
	 * FileHandlers benutzt, da diese oft sehr langsam auf Android Geräten sind.
	 * 
	 * @param path
	 *            Pfad zum Parent-Directory der Bilder.
	 * @param levelID
	 *            path Pfad zum Parent-Directory der Bilder.
	 */
	private static void loadAtlases(final String path, final String levelID) {
		FileHandle[] dirs = Gdx.files.internal(path).list();

		for (final FileHandle dir : dirs) {
			// Erstellen einenes neuen Threads um parallel die
			// unterverzeichnisse zu laden. macht das Sinn?

			final String dirName = dir.name();
			// Nutzung der List Methode für eine Art ForEach um nicht erstmal
			// eine Liste von
			// File Handlern erstellen zu müssen.
			dir.list(new FilenameFilter() {
				@Override
				public boolean accept(final File arg0, final String filename) {
					if (filename.matches(".*\\.atlas")) {
						String relativeFilePath = path.replace("\\", "/") + "/" + dirName + "/" + filename;
						administratedAtlasesPath.put(dirName, relativeFilePath);
						Gdx.app.log("Lade Grafik", "AssetGefunden" + (System.currentTimeMillis()));
					}
					return false;
				}

			});
		}
	}

	/**
	 * Liefert BitmapFont, die gut geeignet sind fuer Ueberschriften.
	 * 
	 * @param size
	 *            vom Typ FontSize umd die Groesse der Schriftart zu definieren
	 * @return b BitmapFont
	 */
	@SuppressWarnings("deprecation")
	public static BitmapFont getHeadlineFont(final FontSize size) {
		if (headlineFonts == null){
			headlineFonts = new BitmapFont[FontSize.values().length];
		}
		
		if (headlineFonts[size.ordinal()] == null) {
			int startIdx = directoryFonts.indexOf(FOLDERNAME_FONTS);
			String relativeFilePath = directoryFonts.substring(startIdx, directoryFonts.length()).replace("\\", "/") + FILESEPARATOR + "headlinefont/GetVoIP Grotesque.ttf";
	
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(relativeFilePath));
			headlineFonts[size.ordinal()] = generator.generateFont(size.getVal());
		}
		headlineFonts[size.ordinal()].setScale(1f);
		
		return headlineFonts[size.ordinal()];
	}

	/**
	 * Liefert BitmapFont, die gut geeignet sind fuer Texte.
	 * 
	 * @param size
	 *            vom Typ FontSize umd die Groesse der Schriftart zu definieren
	 * @return b BitmapFont
	 */
	@SuppressWarnings("deprecation")
	public static BitmapFont getTextFont(final FontSize size) {
		if (textFonts == null){
			textFonts = new BitmapFont[FontSize.values().length];
		}
		
		if (textFonts[size.ordinal()] == null) {
			int startIdx = directoryFonts.indexOf(FOLDERNAME_FONTS);
			String relativeFilePath = directoryFonts.substring(startIdx, directoryFonts.length()).replace("\\", "/") + FILESEPARATOR + "textfont/Grundschrift-Bold.ttf";
	
			FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(relativeFilePath));
			textFonts[size.ordinal()] = gen.generateFont(size.getVal());
			gen.dispose();
		}
		textFonts[size.ordinal()].setScale(1f);
		
		return textFonts[size.ordinal()];
	}

	/**
	 * Zerstoert den Assetmanager, disposed alle Assets.
	 */
	public static void disposeAll() {
		assetManager.dispose();
		assetManager.clear();
		textFonts = null;
		headlineFonts = null;
	}

	/**
	 * Disposed die Assets die durch den Atlas referenziert sind.
	 * 
	 * @param levelID
	 *            Die Level ID
	 * @param atlas
	 *            Der Atlas, den man beim load(...) erhalten hat.
	 */
	public static void disposeAtlas(final String levelID, final String atlas) {
		assetManager.unload(atlas);
		administratedAtlases.remove(levelID);
	}

	/**
	 * Greift auf den TextrueAtlas der levelID zu und liefert die TextureRegion
	 * "filename".
	 * 
	 * @param levelID
	 *            ID des Levels
	 * @param filename
	 *            Dateiname
	 * @return result Ist die TextureRegion, sonst ein rotes Kreuz zur
	 *         Fehleranzeige
	 */
	public static TextureRegion getTextureRegion(final String levelID, final String filename) {

		TextureAtlas atlas = lookUpForAtlas(levelID);

		if (atlas == null){
			return errorPic;
		}
		try {
			AtlasRegion atlasRegion = atlas.findRegion(filename);
			if (atlasRegion != null) {
				Texture page = atlasRegion.getTexture();
				TextureRegion result = new TextureRegion(page, atlasRegion.getRegionX(), atlasRegion.getRegionY(), atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight());
				return result;
			} else {
				throw new TextureNotFoundException("Bild " + filename + " nicht gefunden.");
			}
		} catch (TextureNotFoundException e) {
			e.printStackTrace();
		}
		return errorPic;
	}

	/**
	 * Diese Methode läd einen TexturAtlas falls dieser noch nicht geladen
	 * wurde. Es wird nicht auf die Fertigstellung des Ladens gewartet.
	 * 
	 * @param levelID LevelID des Levels
	 */
	public static void loadTexturRegionsAsync(final String levelID) {
		if (!administratedAtlasesPath.containsKey(levelID)) {
			Gdx.app.error("AssetManager", "TextureAtlas + " + levelID + " existiert nicht.");
		} else {

			boolean isLoaded = assetManager.isLoaded(administratedAtlasesPath.get(levelID), TextureAtlas.class);
			if (isLoaded) { // Grafik ist schon geladen
				Gdx.app.log("AssetManager", levelID + " ist schon geladen");

			} else {
				Gdx.app.log("AssetManager", "Lade " + levelID + " from File");
				assetManager.load(administratedAtlasesPath.get(levelID), TextureAtlas.class);
			}
		}
	}

	/**
	 * Ermittelt alle TextureRegions des TextureAtlas "levelID" und speichert
	 * diese in einer Map<Dateiname, TextureRegion> ab (Dateiname = orignaler
	 * Dateiname der Bilddatei).
	 * 
	 * @param levelID
	 *            ID des Levels.
	 * @return m Map<String, TextureRegion> wenn m.size() > 0, sonst null.
	 */
	public static Map<String, TextureRegion> getAllTextureRegions(final String levelID) {
		administratedAtlases.put(levelID, lookUpForAtlas(levelID));

		Map<String, TextureRegion> m = new HashMap<String, TextureRegion>();
		Array<AtlasRegion> a = administratedAtlases.get(levelID).getRegions();

		for (int i = 0; i < a.size; i++) {
			m.put(a.get(i).name, new TextureRegion(a.get(i).getTexture(), a.get(i).getRegionX(), a.get(i).getRegionY(), a.get(i).getRegionWidth(), a.get(i).getRegionHeight()));
		}

		if (m.size() > 0){
			return m;
		}

		return null;
	}

	/**
	 * Ermittelt alle originalen Dateinamen des TextureAtlas "levelID" und
	 * speichert diese in einem String[] ab.
	 * 
	 * @param levelID
	 *            ID des Levels.
	 * @return s String[] wenn s.length > 0, sonst null.
	 */
	public static String[] getAllFileNames(final String levelID) {
		lookUpForAtlas(levelID);

		Array<AtlasRegion> a = administratedAtlases.get(levelID).getRegions();
		String[] s = new String[a.size];

		for (int i = 0; i < s.length; i++) {
			s[i] = a.get(i).name;
		}

		if (s.length > 0){
			return s;
		}

		return null;
	}

	/**
	 * Dursucht die Map der TextureAtlas-Dateien die vom AssetManagaer administriert wird
	 * und prueft anhand der LevelID, ob eine TextureAtlas existiert. Falls ja, dann wird sie,
	 * wenn nicht schon geschehen, neu geladen.
	 * @param levelID LevelID des Levels
	 * @return gibt den jeweiligen TextureAtlas zurueck oder null, wenn keine passende in der Map existiert
	 */
	private static TextureAtlas lookUpForAtlas(final String levelID) {

		if (!administratedAtlasesPath.containsKey(levelID)) {
			Gdx.app.error("AssetManager", "TextureAtlas + " + levelID + " existiert nicht.");
		} else {

			boolean isLoaded = assetManager.isLoaded(administratedAtlasesPath.get(levelID), TextureAtlas.class);
			if (isLoaded) { // Grafik ist schon geladen
				Gdx.app.log("AssetManager", "Lade " + levelID + " from Cache");
				return assetManager.get(administratedAtlasesPath.get(levelID), TextureAtlas.class);
			} else {
				assetManager.finishLoading(); // es werden alle noch zu ladenen
												// Grafiken geladen
				if (isLoaded) {

					Gdx.app.log("AssetManager", "Lade " + levelID + " from Cache");
					return assetManager.get(administratedAtlasesPath.get(levelID), TextureAtlas.class);
				}
				Gdx.app.log("AssetManager", "Lade " + levelID + " from File");
				assetManager.load(administratedAtlasesPath.get(levelID), TextureAtlas.class);
				assetManager.finishLoading();
				return assetManager.get(administratedAtlasesPath.get(levelID), TextureAtlas.class);
			}
		}
		return null;

	}

	/**
	 * Ermittelt, ob alles Assets vom Manager geladen wurden.
	 * 
	 * @return true, wenn alle Assets geladen wurden, sonst false
	 */
	public static boolean isLoadingFinished() {
		return assetManager.update();
	}

	/**
	 * Assettypen die zur Zeit unterstuetz werden.
	 *
	 */
	public enum Assets {
		SOUNDS, MUSIC, PICTURES
	}

	/***********************************************************************************************.
	 ***********************************************************************************************
	 ********************************* Lizenz-File-Handling*****************************************
	 ***********************************************************************************************
	 ***********************************************************************************************
	 */
	public static void checkLicenses() {
		String[] licenseType = {"cc-0_license", "cc-by_license", "selfmade_license"};
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
	private static Map<String, String[][]> readLicensesAndSplitEntries(final String[] licenseTypes) {
		List<String> stringList = new ArrayList<String>();
		String licenseDir = System.getProperty("user.dir") + FILESEPARATOR + ".." + FILESEPARATOR + "android" + FILESEPARATOR + "assets";
		Map<String, String[][]> stringMap = new HashMap<String, String[][]>();

		// **************************************************
		// Pictures Lizenzen
		// **************************************************

		// Zeilen aus den Lizenztextdateien lesen und
		// einer Liste hinzufuegen
		for (String item : licenseTypes) {
			try {
				BufferedReader bufReadPic = new BufferedReader(new FileReader(licenseDir + FILESEPARATOR 
						+ FOLDERNAME_PICTURES + FILESEPARATOR + item + ".txt"));
				String fileLinePic = bufReadPic.readLine();
				while (fileLinePic != null && !fileLinePic.isEmpty()) {
					stringList.add(fileLinePic);
					fileLinePic = bufReadPic.readLine();
				}
				bufReadPic.close();
			} catch (FileNotFoundException e) {
				Gdx.app.error("AssetManager", "License-file not found");
			} catch (IOException e) {
				Gdx.app.error("AssetManager", "License-file couldn't read");
			}
		}
		stringMap.put(FOLDERNAME_PICTURES, createSeperatedEntries(stringList));
		stringList.clear();

		// **************************************************
		// Sound Lizenzen
		// **************************************************

		stringList = readLicense(licenseDir + FILESEPARATOR + FOLDERNAME_SOUNDS + FILESEPARATOR + FOLDERNAME_SOUNDS + ".txt");
		stringMap.put(FOLDERNAME_SOUNDS, createSeperatedEntries(stringList));
		stringList.clear();

		// **************************************************
		// Music LizenzenfolderName
		// **************************************************

		stringList = readLicense(licenseDir + FILESEPARATOR + FOLDERNAME_MUSIC + FILESEPARATOR + FOLDERNAME_MUSIC + ".txt");
		stringMap.put(FOLDERNAME_MUSIC, createSeperatedEntries(stringList));

		// **************************************************
		// Fonts LizenzenfolderName
		// **************************************************

		stringList = readLicense(licenseDir + FILESEPARATOR + FOLDERNAME_FONTS + FILESEPARATOR + FOLDERNAME_FONTS + ".txt");
		stringMap.put(FOLDERNAME_FONTS, createSeperatedEntries(stringList));
		
		// **************************************************
		// Texte LizenzenfolderName
		// **************************************************

		stringList = readLicense(licenseDir + FILESEPARATOR + FOLDERNAME_TEXTS + FILESEPARATOR + FOLDERNAME_TEXTS + ".txt");
		stringMap.put(FOLDERNAME_TEXTS, createSeperatedEntries(stringList));

		
		return stringMap;
	}

	/**
	 * Hilfsfunktion die die einzelnen Zeilen aus den Lizenzdateien liest.
	 * 
	 * @param licenseDir
	 *            Pfad zur Lizenzdatei
	 * @return Liste der gelesenen Zeilen aus der Lizenzdatei
	 */
	private static List<String> readLicense(final String licenseDir) {
		List<String> stringList = new ArrayList<String>();

		try {
			BufferedReader bufRead = new BufferedReader(new FileReader(licenseDir));
			String fileLine = bufRead.readLine();
			while (fileLine != null && !fileLine.isEmpty()) {
				stringList.add(fileLine);
				fileLine = bufRead.readLine();
			}
			bufRead.close();
		} catch (FileNotFoundException e) {
			Gdx.app.error("AssetManager", "License-file not found");
		} catch (IOException e) {
			Gdx.app.error("AssetManager", "License-file couldn't read");
		}
		return stringList;
	}

	/**
	 * Hilfsfunktion um die mit Semikolon getrennten Eintraege zu seperieren.
	 * 
	 * @param stringList
	 *            Liste der gelesenen Zeilen aus der Lizenzdatei
	 * @return 2D-Array mit seperierten Eintraegen
	 */
	private static String[][] createSeperatedEntries(final List<String> stringList) {
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
	 * Die Ergebnisse werden in einer logfile.txt im Asset-Ordner gespeichert
	 * 
	 * @param stringMap
	 *            Key:=licenseType Values:=2D Array das die Zeilen im txt-File
	 *            repraesentiert
	 */
	private static void checkFiles(final Map<String, String[][]> stringMap) {
		String licenseDir = System.getProperty("user.dir") + FILESEPARATOR + ".." 
				+ FILESEPARATOR + "android" + FILESEPARATOR + "assets";

		String[][] pictures = stringMap.get(FOLDERNAME_PICTURES);
		String[][] sounds = stringMap.get(FOLDERNAME_SOUNDS);
		String[][] music = stringMap.get(FOLDERNAME_MUSIC);
		String[][] fonts = stringMap.get(FOLDERNAME_FONTS);
		String[][] texts = stringMap.get(FOLDERNAME_TEXTS);

		File checkFile = null;

		try {
			FileWriter fWriter = new FileWriter(licenseDir + FILESEPARATOR + "logfile.txt");
			BufferedWriter bWriter = new BufferedWriter(fWriter);
			bWriter.write("Logfile for license check of assets");
			bWriter.newLine();
			bWriter.write("***PICTURES***");
			bWriter.newLine();

			// **************************************************
			// Pictures Eintraege
			// **************************************************
			for (int i = 0; i < pictures.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_PICTURES 
						+ FILESEPARATOR + pictures[i][0] + FILESEPARATOR + pictures[i][1]);
				writeFile(FOLDERNAME_PICTURES, pictures, checkFile, bWriter, i);
			}

			bWriter.write("***SOUNDS**");
			bWriter.newLine();

			// **************************************************
			// Sounds Eintraege
			// **************************************************
			for (int i = 0; i < sounds.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_SOUNDS
						+ FILESEPARATOR + sounds[i][0] + FILESEPARATOR + sounds[i][1]);
				writeFile(FOLDERNAME_SOUNDS, sounds, checkFile, bWriter, i);
			}

			bWriter.write("***MUSIC***");
			bWriter.newLine();

			// **************************************************
			// Music Eintraege
			// **************************************************
			for (int i = 0; i < music.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_MUSIC 
						+ FILESEPARATOR + music[i][0] + FILESEPARATOR + music[i][1]);
				writeFile(FOLDERNAME_MUSIC, music, checkFile, bWriter, i);
			}

			bWriter.write("***FONTS***");
			bWriter.newLine();
			
			// **************************************************
			// Fonts Eintraege
			// **************************************************
			for (int i = 0; i < fonts.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_FONTS
						+ FILESEPARATOR + fonts[i][0] + FILESEPARATOR + fonts[i][1]);
				writeFile(FOLDERNAME_FONTS, fonts, checkFile, bWriter, i);
			}
			
			bWriter.write("***TEXTS***");
			bWriter.newLine();
			
			// **************************************************
			// InfoTexte Eintraege
			// **************************************************
			for (int i = 0; i < texts.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_TEXTS
						+ FILESEPARATOR + texts[i][0] + FILESEPARATOR + texts[i][1]);
				writeFile(FOLDERNAME_TEXTS, texts, checkFile, bWriter, i);
			}

			bWriter.close();
			fWriter.close();

		} catch (IOException e) {
			Gdx.app.error("AssetManager", "Couldn't generate a logfile!");
		}
	}

	/**
	 * Hilfsfunktion um in die log-Datei zu schreiben.
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
	private static void writeFile(final String type, final String[][] stringFile, final File checkFile, 
			final BufferedWriter bWriter, final int i) {
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
			if (stringFile[i][2].toLowerCase().matches("cc-0|cc-by|cc-by-sa 3.0|selfmade")) {
				bWriter.write(stringFile[i][2] + " ENTRY OK!");
			} else {
				bWriter.write(stringFile[i][2] + " NOT VALID!");
			}
			bWriter.newLine();
		} catch (IOException e) {
			Gdx.app.error("AssetManager", "Couldn't write to logfile!");
		}
	}
}
