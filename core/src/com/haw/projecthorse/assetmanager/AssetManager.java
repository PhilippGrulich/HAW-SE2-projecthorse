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
import com.haw.projecthorse.audiomanager.AudioManager;
import com.haw.projecthorse.audiomanager.AudioManagerImpl;

public final class AssetManager {

	private static String assetDir = "";
	private static final String FILESEPARATOR = System.getProperty("file.separator");
	private static final String FOLDERNAME_SOUNDS = "sounds";
	private static final String FOLDERNAME_MUSIC = "music";
	private static final String FOLDERNAME_PICTURES = "pictures";
	private static final String FOLDERNAME_FONTS = "fonts";
	private static String directory_sounds;
	private static String directory_music;
	private static String directory_pictures;
	private static String directory_fonts;
	public static com.badlogic.gdx.assets.AssetManager assetManager ;
	private static float soundVolume = 1;
	private static float musicVolume = 1;
	// Mapped mit LevelID auf TextureAtlas - Haelt AtlasObjekte Vorraetig zwecks
	// Performance
	private static Map<String, TextureAtlas> administratedAtlases;
	// Mapped mit LevelID auf Pfade von Atlas Objekten im Falle von Verlust
	// eines Atlas bei dispose (Performance)
	private static Map<String, String> administratedAtlasesPath ;
	// Mapped mit LevelID auf Pfade von Sound / Music sofern 1 Mal vorher darauf
	// zugegriffen wurde.
	// da laden aller Sounds / Music am Anfang, wie bei Atlas Dateien, nicht
	// Sinnvoll.
	private static Map<String, ArrayList<String>> administratedSoundPath;
	private static Map<String, ArrayList<String>> administratedMusicPath;
	
	private static AudioManager audioManager;

	private static TextureRegion errorPic;
	
	private static BitmapFont[] textFonts, headlineFonts;

	public static void initialize() {
		assetManager = new com.badlogic.gdx.assets.AssetManager();
		administratedAtlases = new HashMap<String, TextureAtlas>();
		administratedAtlasesPath = new HashMap<String, String>();
		audioManager= AudioManagerImpl.getInstance();
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

			assetDir = System.getProperty("user.dir") + FILESEPARATOR + "bin" + FILESEPARATOR;
		} else {
			System.out.println("In AssetManager: No android or desktop device");
		}

		directory_sounds = assetDir + FOLDERNAME_SOUNDS;
		directory_music = assetDir + FOLDERNAME_MUSIC;
		directory_pictures = assetDir + FOLDERNAME_PICTURES;
		directory_fonts = assetDir + FOLDERNAME_FONTS;

	}

	/**
	 * HashMap fuer Pfade zu Sound- und Musikdateien erzeugen, Methode fuer
	 * rekursiven Abstieg in Verzeichnisstruktur aufrufen.
	 */
	private static void loadAudioPaths() {
		administratedSoundPath = new HashMap<String, ArrayList<String>>();
		administratedMusicPath = new HashMap<String, ArrayList<String>>();
		loadAudioPaths(directory_sounds, directory_sounds, Assets.SOUNDS);
		loadAudioPaths(directory_music, directory_music, Assets.MUSIC);
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
	 * @param path
	 * @param type
	 */
	private static void chooseAudioMap(String levelID, String path, Assets type) {
		if (type == Assets.SOUNDS) {
			if (!administratedSoundPath.containsKey(levelID)) {
				administratedSoundPath.put(levelID, new ArrayList<String>());
			}
			int startIdx = path.indexOf(FOLDERNAME_SOUNDS);
			path = path.substring(startIdx, path.length()).replace("\\", "/");
			administratedSoundPath.get(levelID).add(path);
		} else if (type == Assets.MUSIC) {
			if (!administratedMusicPath.containsKey(levelID)) {
				administratedMusicPath.put(levelID, new ArrayList<String>());
			}
			int startIdx = path.indexOf(FOLDERNAME_MUSIC);
			path = path.substring(startIdx, path.length()).replace("\\", "/");
			administratedMusicPath.get(levelID).add(path);
		}
	}

	/**
	 * Laedt alle Dateien von levelID die im sounds Ordner sind.
	 * 
	 * @param levelID
	 *            ID des Levels.
	 */
	public static void loadSounds(String levelID) {
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
	public static void loadMusic(String levelID) {
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
	 * @param levelID
	 * @param name
	 * @return
	 */
	public static Sound getSound(String levelID, String name) {
		return assetManager.get(FOLDERNAME_SOUNDS + "/" + levelID + "/" + name, Sound.class);
	}

	/**
	 * Liefert ein LibGDX Music Objekt. Sollte nur im AudioManager Modul genutzt
	 * werden.
	 * 
	 * @param levelID
	 * @param name
	 * @return
	 */
	public static Music getMusic(String levelID, String name) {
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
	private static void loadAtlases(final String path, String levelID) {
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
				public boolean accept(File arg0, String filename) {
					if (filename.matches(".*\\.atlas")) {
						
						String relativeFilePath = path.replace("\\", "/") + "/" + dirName + "/" + filename;

						// assets = new
						// TextureAtlas(Gdx.files.internal(relativeFilePath));
						// #Changed
						// TextureAtlas atlas = new
						// TextureAtlas(Gdx.files.internal(relativeFilePath));
						// assetManager.load(relativeFilePath,
						// TextureAtlas.class);
						administratedAtlasesPath.put(dirName, relativeFilePath);
						Gdx.app.log("Lade Grafik", "AssetGefunden" + (System.currentTimeMillis()));
						// administratedAtlases.put(levelID, atlas);
					}
					return false;
				}

			});
		}
		;

	}

	// TODO: OLD kann spaeter raus
	/**
	 * Liefert BitmapFont einer .fnt Datei
	 * 
	 * @param levelID
	 * @param filename
	 * @return b BitmapFont
	 */
	/*
	 * public static BitmapFont getFont(String levelID, String filename){ int
	 * startIdx = directory_pictures.indexOf(FOLDERNAME_PICTURES); String
	 * relativeFilePath = directory_pictures.substring(startIdx,
	 * directory_pictures.length()).replace("\\", "/") + FILESEPARATOR + levelID
	 * + "/" + filename + ".fnt"; System.out.println(relativeFilePath);
	 * TextureRegion t = getTextureRegion(levelID, filename); BitmapFont b = new
	 * BitmapFont(Gdx.files.internal(relativeFilePath), t);
	 * assetManager.finishLoading(); return b; }
	 */

	/**
	 * Liefert BitmapFont, die gut geeignet sind fuer Ueberschriften
	 * 
	 * @param size
	 *            vom Typ FontSize umd die Groesse der Schriftart zu definieren
	 * @return b BitmapFont
	 */
	@SuppressWarnings("deprecation")
	public static BitmapFont getHeadlineFont(FontSize size) {
		if (headlineFonts == null){
			headlineFonts = new BitmapFont[FontSize.values().length];
		}
		
		if (headlineFonts[size.ordinal()] == null) {
		int startIdx = directory_fonts.indexOf(FOLDERNAME_FONTS);
		String relativeFilePath = directory_fonts.substring(startIdx, directory_fonts.length()).replace("\\", "/") + FILESEPARATOR + "headlinefont/GetVoIP Grotesque.ttf";

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(relativeFilePath));
		headlineFonts[size.ordinal()] = generator.generateFont(size.getVal());
		}
		return headlineFonts[size.ordinal()];
	}

	/**
	 * Liefert BitmapFont, die gut geeignet sind fuer Texte
	 * 
	 * @param size
	 *            vom Typ FontSize umd die Groesse der Schriftart zu definieren
	 * @return b BitmapFont
	 */
	@SuppressWarnings("deprecation")
	public static BitmapFont getTextFont(FontSize size) {
		if (textFonts == null){
			textFonts = new BitmapFont[FontSize.values().length];
		}
		
		if (textFonts[size.ordinal()] == null) {
		int startIdx = directory_fonts.indexOf(FOLDERNAME_FONTS);
		String relativeFilePath = directory_fonts.substring(startIdx, directory_fonts.length()).replace("\\", "/") + FILESEPARATOR + "textfont/Grundschrift-Bold.ttf";

		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(relativeFilePath));
		textFonts[size.ordinal()] = gen.generateFont(size.getVal());
		gen.dispose();
		}
		
		return textFonts[size.ordinal()];
	}

	/**
	 * Zerstoert den Assetmanager, disposed alle Assets.
	 */
	public static void disposeAll() {
		assetManager.dispose();
		assetManager.clear();
		audioManager = null;
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
	 *            
	 * @deprecated Stattdessen "this.audioManager" aus Level Interface benutzen 
	 */
	@Deprecated
	public static void playSound(String levelID, String name) {
		audioManager.getSound(levelID, name).play(soundVolume);
	}

	/**
	 * Aendert die Soundlautst�rke, indem die Spieltlautstaerke fuer Sounds neu
	 * gesetzt wird
	 * 
	 * @param volume
	 *            Die Lautstärke des Sounds im Bereich [0,1]. 0 ist stumm.
	  * @deprecated Stattdessen "this.audioManager" aus Level Interface benutzen 
	 */
	@Deprecated
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
	  * @deprecated Stattdessen "this.audioManager" aus Level Interface benutzen 
	 */
	@Deprecated
	public static void playMusic(String levelID, String name) { 
		Music music = audioManager.getMusic(levelID, name);
		music.setVolume(musicVolume);
		music.play();
	}

	/**
	 * Looping einer Musikdatei setzen. Wenn Musikstück zu Ende ist, wird dieses
	 * wiederholt abgespielt, wenn looping auf true gesetzt wurde.
	 * 
	 * @param levelID
	 *            ID des Levels
	 * @param name
	 *            Name der Datei ink. relativem Pfad (ohne Oberordner mit Namen
	 *            "levelID"
	 * @param looping
	 *            true um Looping zu setzen, sonst false
	 * @deprecated Stattdessen "this.audioManager" aus Level Interface benutzen 
	 */
	@Deprecated
	public static void setMusicLooping(String levelID, String name,
			boolean looping) {
		audioManager.getMusic(levelID, name).setLooping(looping);
	}

	/**
	 * Schaltet die Musik aus, indem sie angehalten wird.
	 * 
	 * @param levelID
	 *            ID des Levels
	 * @param name
	 *            Name der Datei inkl. relativem Pfad (ohne Oberordner mit Namen
	 *            "levelID").
	 * @deprecated Stattdessen "this.audioManager" aus Level Interface benutzen 
	 */
	@Deprecated
	public static void turnMusicOff(String levelID, String name) {
		audioManager.getMusic(levelID, name).stop();
	}

	/**
	 * Aendert die Musiklautstärke und setzt die Spiellautstaerke auf neuen Wert
	 * 
	 * @param levelID
	 *            ID des Levels
	 * @param name
	 *            Name der Datei inkl. relativem Pfad (ohne Oberordner mit Namen
	 *            "levelID").
	 * @param volume
	 *            Float-Wert zwischen [0,1]. 0 ist Stumm, 1 volle Lautstärke.
	 * @deprecated Stattdessen "this.audioManager" aus Level Interface benutzen 
	 */
	@Deprecated
	public static void changeMusicVolume(String levelID, String name,
			float volume) {
		audioManager.getMusic(levelID, name).setVolume(volume);
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

		TextureAtlas atlas = lookUpForAtlas(levelID);

		if (atlas == null)
			return errorPic;
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
	 * Diese Methode läd einen TexturAdlas falls dieser noch nicht geladen
	 * wurde. Es wird nicht auf die Fertigstellung des Ladens gewartet.
	 * 
	 * @param levelID
	 */
	public static void loadTexturRegionsAsync(String levelID) {
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
	public static Map<String, TextureRegion> getAllTextureRegions(String levelID) {
		administratedAtlases.put(levelID, lookUpForAtlas(levelID));

		Map<String, TextureRegion> m = new HashMap<String, TextureRegion>();
		Array<AtlasRegion> a = administratedAtlases.get(levelID).getRegions();

		for (int i = 0; i < a.size; i++) {
			m.put(a.get(i).name, new TextureRegion(a.get(i).getTexture(), a.get(i).getRegionX(), a.get(i).getRegionY(), a.get(i).getRegionWidth(), a.get(i).getRegionHeight()));
		}

		if (m.size() > 0)
			return m;

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
	public static String[] getAllFileNames(String levelID) {
		lookUpForAtlas(levelID);

		Array<AtlasRegion> a = administratedAtlases.get(levelID).getRegions();
		String[] s = new String[a.size];

		for (int i = 0; i < s.length; i++) {
			s[i] = a.get(i).name;
		}

		if (s.length > 0)
			return s;

		return null;
	}

	private static TextureAtlas lookUpForAtlas(String levelID) {

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
	 * Ermittelt, ob alles Assets vom Manager geladen wurden
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

	/***********************************************************************************************
	 ***********************************************************************************************
	 ********************************* Lizenz-File-Handling******************************************
	 ***********************************************************************************************
	 ***********************************************************************************************
	 */
	public static void checkLicenses() {
		String[] licenseType = { "cc-0_license", "cc-by_license", "selfmade_license" };
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
				BufferedReader bufReadPic = new BufferedReader(new FileReader(licenseDir + FILESEPARATOR + FOLDERNAME_PICTURES + FILESEPARATOR + item + ".txt"));
				String fileLinePic = null;
				while ((fileLinePic = bufReadPic.readLine()) != null) {
					stringList.add(fileLinePic);
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
			BufferedReader bufRead = new BufferedReader(new FileReader(licenseDir));
			String fileLine = null;
			while ((fileLine = bufRead.readLine()) != null) {
				stringList.add(fileLine);
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
		String licenseDir = System.getProperty("user.dir") + FILESEPARATOR + ".." + FILESEPARATOR + "android" + FILESEPARATOR + "assets";

		String[][] pictures = stringMap.get(FOLDERNAME_PICTURES);
		String[][] sounds = stringMap.get(FOLDERNAME_SOUNDS);
		String[][] music = stringMap.get(FOLDERNAME_MUSIC);

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
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_PICTURES + FILESEPARATOR + pictures[i][0] + FILESEPARATOR + pictures[i][1]);
				writeFile(FOLDERNAME_PICTURES, pictures, checkFile, bWriter, i);
			}

			bWriter.write("***SOUNDS**");
			bWriter.newLine();

			// **************************************************
			// Sounds Eintraege
			// **************************************************
			for (int i = 0; i < sounds.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_SOUNDS + FILESEPARATOR + sounds[i][0] + FILESEPARATOR + sounds[i][1]);
				writeFile(FOLDERNAME_SOUNDS, sounds, checkFile, bWriter, i);
			}

			bWriter.write("***MUSIC***");
			bWriter.newLine();

			// **************************************************
			// Music Eintraege
			// **************************************************
			for (int i = 0; i < music.length; i++) {
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_MUSIC + FILESEPARATOR + music[i][0] + FILESEPARATOR + music[i][1]);
				writeFile(FOLDERNAME_MUSIC, music, checkFile, bWriter, i);
			}

			bWriter.close();
			fWriter.close();

		} catch (IOException e) {
			Gdx.app.error("AssetManager", "Couldn't generate a logfile!");
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
	private static void writeFile(String type, String[][] stringFile, File checkFile, BufferedWriter bWriter, int i) {
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
				if (stringFile[i][2].toLowerCase().matches("cc-0|cc-by|selfmade")) {
					bWriter.write(stringFile[i][2] + " ENTRY OK!");
				} else {
					bWriter.write(stringFile[i][2] + " NOT VALID!");
				}
				bWriter.newLine();
			}
		} catch (IOException e) {
			Gdx.app.error("AssetManager", "Couldn't write to logfile!");
		}
	}
}