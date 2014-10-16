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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.haw.projecthorse.assetmanager.exceptions.*;
public final class AssetManager {
	
	private static AssetManager ownAssetManager;
	private static String assetDir = "";
	private static final String FILESEPARATOR = 
			System.getProperty("file.separator");
	private static final String FOLDERNAME_SOUNDS = "sounds";
	private static final String FOLDERNAME_MUSIC = "music";
	private static final String FOLDERNAME_PICTURES = "pictures";
	private static String directory_sounds;
	private static String directory_music;
	private static String directory_pictures;
	private static com.badlogic.gdx.assets.AssetManager assetManager = 
			new com.badlogic.gdx.assets.AssetManager();
	private static TextureAtlas assets = null;
	private static boolean isApplicationTypeChoosen = false;
	private static float soundVolume = 1;
	private static float musicVolume = 1;
	private static Map<String, TextureAtlas> administratedAtlases = new HashMap<String, TextureAtlas>();
	private static Texture errorPic;
	
	private AssetManager(){};
	
	public static AssetManager getInstance(){
		if(AssetManager.ownAssetManager == null){
			AssetManager.ownAssetManager = new AssetManager();
		}
		return ownAssetManager;
	}
	
	/**
	 * Lädt Sounds, Musik, Bilder. Bilder werden als TextureAtlas-Objekte zurückgegeben. 
	 * @param levelID
	 * @param loadSounds true, wenn Soundfiles geladen werden sollen, sonst false
	 * @param loadMusic true, wenn Musikfiles geladen werden sollen, sonst false
	 * @param loadPictures true, wenn Bilder geladen werden sollen, sonst false
	 * @return assets Alle Textures zusammengefasst als TextureAtlas
	 */
	public static TextureAtlas load(String levelID, boolean loadSounds, boolean loadMusic, boolean loadPictures){
		if(!isApplicationChoosen()){
			setApplicationRoot();
		}
		
		if(loadSounds){
			findAssetFolder(levelID, directory_sounds, Assets.SOUNDS);
		}
		if(loadMusic){
			findAssetFolder(levelID, directory_music, Assets.MUSIC);
		}
		if(loadPictures){	
			String[] licenseType = {"cc-0_license", "cc-by_license","selfmade_license"};
			checkFiles(readLicensesAndSplitEntries(licenseType));
			findAssetFolder(levelID, directory_pictures, Assets.PICTURES);
		}
		assetManager.finishLoading();
		
	
			
		if(assets == null){
			System.out.println("Bilder konnten nicht geladen werden," + 
					"da kein TextureAtlas erstellt wurde. TexturePacker.main() nicht ausgef�hrt?");
			
		}
		
		return assets;
	}
	

	/**
	 * Setzen des root-Verzeichnisses. Wird für den Zugriff auf Assets benötigt.
	 * Weiterhin werden die Sound, Music und Texture Pfade gesetzt
	 */
	private static void setApplicationRoot(){
		if (Gdx.app.getType() == ApplicationType.Android) {
		
			assetDir = "" ;
			setApplicationType();
			} 
		else if (Gdx.app.getType() == ApplicationType.Desktop) {
		
			assetDir = System.getProperty("user.dir") + FILESEPARATOR+"bin"+FILESEPARATOR ;
			setApplicationType();
			}
		else{
			System.out.println("In AssetManager: No android or desktop device");
		}
		
		directory_sounds = assetDir+FOLDERNAME_SOUNDS+ FILESEPARATOR;
		directory_music = assetDir+FOLDERNAME_MUSIC+ FILESEPARATOR;
		directory_pictures = assetDir+FOLDERNAME_PICTURES + FILESEPARATOR;
		
		//errorPic = new Texture(Gdx.files.internal(FOLDERNAME_PICTURES + FILESEPARATOR + "errorPic.png"));
		System.out.println("test");
	}
	
	private static boolean isApplicationChoosen(){
		return isApplicationTypeChoosen;
	}
	
	private static void setApplicationType(){
		isApplicationTypeChoosen = true;
	}
	
	/**
	 * Sucht im Ordner FOLDERNAME_type den Ordner mit der levelID. 
	 * @param levelID
	 * @param path assetDir + FOLDERNAME_type 
	 * @param type Assets.SOUNDS oder Assets.MUSIC oder Assets.PICTURES
	 */
	private static void findAssetFolder(String levelID, String path, Assets type){
	
		FileHandle[] files = Gdx.files.internal(path).list();
		ArrayList<String> content = new ArrayList<String>();

		for(FileHandle file: files) {
			content.add(file.name());
		}
		
		try{
			if(content.contains(levelID)){
				loadAssets(levelID, path + FILESEPARATOR + levelID, type);			
		}else {
			throw new LevelDirectoryNotFoundException("Ordner mit LevelID " + levelID + " in " + path + " nicht gefunden");
		
		}
		}catch(LevelDirectoryNotFoundException e){
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Geht für type== Assets.Sound, type == Assets.Music etc. rekursiv durch die Verzeichnisse
	 * ab path. Bei auffinden einer Datei Audio oder Bilddatei wird loadAudio oder
	 *  loadTextureAtlas aufgerufen.
	 * @param levelID 
	 * @param path assetDir + FOLDERNAME_type ODER Unterverzeichnis (rekursiver Aufruf)
	 * @param type Assets.SOUNDS oder Assets.MUSIC oder Assets.PICTURES
	 */
	private static void loadAssets(String levelID, String path, Assets type){
		
		FileHandle[] files = Gdx.files.internal(path).list();
	
		for(FileHandle file: files) {
			if(file.isDirectory()){
				loadAssets(levelID, path + FILESEPARATOR +file.name(), type);
			}else{
				if(type == Assets.SOUNDS || type == Assets.MUSIC){
					loadAudio(path, file.name(), type);
				}else if(type == Assets.PICTURES){
					loadTextureAtlas(levelID, path, file.name());
				}
			}
		}		
		
		
	}
	
	/**
	 * Prüft ob die gefundene Datei im path eine .atlas Datei ist und lädt diese.
	 * Ruft anschließend insertAtlasMap().
	 * @param levelID
	 * @param path
	 * @param filename 
	 */
	private static void loadTextureAtlas(String levelID, String path, String filename){
		if(filename.toLowerCase().matches(".*(\\.atlas)$")){
			int startIdx = path.indexOf(FOLDERNAME_PICTURES);
			String relativeFilePath = path.substring(startIdx, path.length()).replace("\\", "/") + "/" + filename;
			assetManager.load(relativeFilePath, TextureAtlas.class);
			assets = new TextureAtlas(Gdx.files.internal(relativeFilePath));
			administratedAtlases.put(levelID, new TextureAtlas(Gdx.files.internal(relativeFilePath)));
		}
	}
	
	/**
	 * Prüft ob File eine Audiodatei ist die unterstützt wird. Falls ja, wird bei
	 * type == Assets.SOUND die Datei als SOUND geladen. Analog bei type == Assets.MUSIC
	 * @param path
	 * @param filename
	 * @param type
	 */
	private static void loadAudio(String path, String filename, Assets type){
		if(filename.toLowerCase().matches(".*(\\.mp3|\\.wav|\\.ogg)$")){
			if(type == Assets.SOUNDS){
				int startIdx = path.indexOf(FOLDERNAME_SOUNDS);
				path = path.substring(startIdx, path.length()).replace("\\", "/") + "/" + filename;
				assetManager.load(path, Sound.class);
			}else if(type == Assets.MUSIC){
				int startIdx = path.indexOf(FOLDERNAME_MUSIC);
				path = path.substring(startIdx, path.length()).replace("\\", "/") + "/" + filename;
				
				assetManager.load(path, Music.class);
			}
		}
	}

	/**
	 * Zerstört den Assetmanager, disposed alle Assets.
	 */
	public static void disposeAll(){
		assetManager.dispose();
		assets = null;
	}
	
	/**
	 * Disposed die Assets die durch den Atlas referenziert sind.
	 * @param levelID Die Level ID
	 * @param atlas Der Atlas, den man beim load(...) erhalten hat.
	 */
	public static void disposeAtlas(String levelID, String atlas){
		assetManager.unload(atlas);
		administratedAtlases.remove(levelID);
		assets = null;
	}
	
	
	/**
	 * Spielt Sound ab, indem der Sound in den Arbeitsspeicher geladen wird.
	 * Der Pfad zur Sounddatei startet relativ zum Oberordner mit der levelID.
	 * Z.B. liegt die Datei in sounds/mainMenu/asdfa/flap.wav dann ist der name als asdfa/flap.wav
	 * anzugeben. 
	 * @param name Pfad zur Sounddatei
	 * @param levelID Die ID des Levels
	 */
	public static void playSound(String levelID, String name){
		assetManager.get(FOLDERNAME_SOUNDS + "/" + levelID + "/" + name, Sound.class).play(soundVolume);
	}
	
	/**
	 * Ändert die Soundlautstärke, indem die Spieltlautstaerke fuer Sounds neu gesetzt wird
	 * @param volume Die Lautstärke des Sounds im Bereich [0,1]. 0 ist stumm.
	 */
	public static void changeSoundVolume(float volume){
		soundVolume = volume;
	}
	
	/**
	 * Spielt die Musikdatei ab, indem der Sound in den Arbeitsspeicher geladen wird.
	 * Der Pfad zur Sounddatei startet relativ zum Oberordner mit der levelID.
	 * Z.B. liegt die Datei in music/mainMenu/asdfa/flap.wav dann ist der name als asdfa/life.wav
	 * anzugeben. 
	 * @param leveldID ID des Levels
	 * @param name Name der Datei ink. relativem Pfad (ohne Oberordner mit Namen "levelID"
	 */
	public static void playMusic(String levelID, String name){
		assetManager.get(FOLDERNAME_MUSIC + "/" + levelID + "/" + name, 
				Music.class).play();
		assetManager.get(FOLDERNAME_MUSIC + "/" + levelID + "/" + name,
				Music.class).setVolume(musicVolume);
	}
	
	/**
	 * Schaltet die Musik aus, indem sie angehalten wird.
	 * @param levelID ID des Levels
	 * @param name Name der Datei inkl. relativem Pfad (ohne Oberordner mit Namen "levelID").
	 */
	public static void turnMusicOff(String levelID, String name){
		assetManager.get(FOLDERNAME_MUSIC + "/" + levelID + "/" + name, Music.class).stop();
	}
	
	public static String getFileseparator(){
		return FILESEPARATOR;
	}
	
	/**
	 * Ändert die Musiklautstärke und setzt die Spiellautstaerke auf neuen Wert
	 * @param levelID ID des Levels
	 * @param name Name der Datei inkl. relativem Pfad (ohne Oberordner mit Namen "levelID").
	 * @param volume Float-Wert zwischen [0,1]. 0 ist Stumm, 1 volle Lautstärke.
	 */
	public static void changeMusicVolume(String levelID, String name, float volume){
		assetManager.get(FOLDERNAME_MUSIC + "/" + levelID + "/" + name, Music.class).setVolume(volume);
		musicVolume = volume;
	}
	
	/**
	 * Greift auf den TextrueAtlas der levelID zu und liefert die Texture
	 * "filename".
	 * @param levelID ID des Levels
	 * @param filename Dateiname
	 * @return result Ist die Texture, sonst ein rotes Kreuz zur Fehleranzeige
	 */
	public static Texture getTexture(String levelID, String filename){
		Texture result;
		
		try{
			if(administratedAtlases.get(levelID).findRegion(filename) != null){
				result = administratedAtlases.get(levelID).findRegion(filename).getTexture();
				return result;
			}else{
				throw new TextureNotFoundException("Bild " + filename + " nicht gefunden.");
			}
		}catch(TextureNotFoundException e){
			e.printStackTrace();
		}
		return errorPic;
	}
	
	/**
	 * Assettypen die zur Zeit unterstütz werden.
	 * @author Francis
	 *
	 */
	public enum Assets {
		SOUNDS, MUSIC, PICTURES
	}
	
	/**
	 * Liest aus den Lizenzdateien die Zeilen aus und splittet
	 * deren Eintraege getrennt nach dem Semikolon. Somit
	 * kann auf das 2D Array, auf jede Zeichenkette die in
	 * der Textdatei durch das Semikolon getrennt ist, zugegriffen werden
	 * @param licenseTypes cc-0, cc-by oder selfmade wird bei Aufruf
	 * automatisch uebergeben
	 * @return Key:=licenseType Values:=2D Array das die Zeilen im txt-File
	 * repraesentiert
	 */
	private static Map<String, String[][]> readLicensesAndSplitEntries(final String[] licenseTypes) {
		List<String> stringList = new ArrayList<String>();
		String licenseDir = System.getProperty("user.dir") + FILESEPARATOR + ".." 
				+ FILESEPARATOR + "android" + FILESEPARATOR + "assets";
		Map<String, String[][]> stringMap = new
				HashMap<String, String[][]>();

		//**************************************************
		//Pictures Lizenzen
		//**************************************************
		
		//Zeilen aus den Lizenztextdateien lesen und
		//einer Liste hinzufuegen
		for (String item : licenseTypes) {
			try {
				BufferedReader bufReadPic = new BufferedReader(new
						FileReader(licenseDir + FILESEPARATOR + FOLDERNAME_PICTURES
								+ FILESEPARATOR + item + ".txt"));
				String fileLinePic = null;
				while((fileLinePic = bufReadPic.readLine()) != null) {
					stringList.add(fileLinePic.toLowerCase());
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
		
		//**************************************************
		//Sound Lizenzen
		//**************************************************

		stringList = readLicense(licenseDir + FILESEPARATOR
				+ FOLDERNAME_SOUNDS + FILESEPARATOR + FOLDERNAME_SOUNDS + ".txt");
		stringMap.put(FOLDERNAME_SOUNDS, createSeperatedEntries(stringList));
		stringList.clear();
	
		//**************************************************
		//Music LizenzenfolderName
		//**************************************************

		stringList = readLicense(licenseDir + FILESEPARATOR
				+ FOLDERNAME_MUSIC + FILESEPARATOR + FOLDERNAME_MUSIC + ".txt");
		stringMap.put(FOLDERNAME_MUSIC, createSeperatedEntries(stringList));
		
		return stringMap;
	}
	
	/**
	 * Hilfsfunktion die die einzelnen Zeilen aus den Lizenzdateien liest
	 * @param licenseDir Pfad zur Lizenzdatei
	 * @return Liste der gelesenen Zeilen aus der Lizenzdatei
	 */
	private static List<String> readLicense(String licenseDir){
		List<String> stringList = new ArrayList<String>();

		try{
			BufferedReader bufRead = new BufferedReader(new
					FileReader(licenseDir));
			String fileLine = null;
			while((fileLine = bufRead.readLine()) != null) {
				stringList.add(fileLine.toLowerCase());
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
	 * @param stringList Liste der gelesenen Zeilen aus der Lizenzdatei
	 * @return 2D-Array mit seperierten Eintraegen
	 */
	private static String[][] createSeperatedEntries(List<String> stringList){
		final int maxLicenseLineSize = 5;
		String[] listLine;
		//aufsplitten der Zeilen, anhand des Semikolons
		//und abspeichern in ein 2D-Array. Anschließendes
		//einfuegen in die HashMap
		String[][] seperatedEntries = new String[stringList.size()][maxLicenseLineSize];
		for(int i = 0; i < stringList.size(); i++){
			listLine = stringList.get(i).split(";");
			for (int j = 0; j < listLine.length; j++) {
				seperatedEntries[i][j] = listLine[j].trim();
			}
		}
		return seperatedEntries;
	}
	
	/**
	 * Geht durch die jeweiligen Level Ordner und prueft, ob die Dateien,
	 * welche in den Lizenztextdateien aufgelistet sind, vorhanden sind.
	 * Weiterhin wird geprueft, ob ein Lizenzeintrag in den
	 * Lizenztextdateien enthalten ist. Die Ergebnisse werden in einer
	 * logfile.txt im pictures Ordner gespeichert
	 * @param stringMap Key:=licenseType Values:=2D Array das die Zeilen
	 * im txt-File repraesentiert
	 */
	private static void checkFiles(final Map<String, String[][]> stringMap) {
		String licenseDir = System.getProperty("user.dir") + FILESEPARATOR + ".." 
				+ FILESEPARATOR + "android" + FILESEPARATOR + "assets";

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
			
			//**************************************************
			//Pictures Eintraege
			//**************************************************
			for(int i = 0; i < pictures.length; i++){
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_PICTURES
						+ FILESEPARATOR + pictures[i][0] + FILESEPARATOR + pictures[i][1]);		
				writeFile(FOLDERNAME_PICTURES, pictures, checkFile, bWriter, i);					
			}

			bWriter.write("***SOUNDS**");
			bWriter.newLine();	

			//**************************************************
			//Sounds Eintraege
			//**************************************************
			for(int i = 0; i < sounds.length; i++){
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_SOUNDS
						+ FILESEPARATOR + sounds[i][0] + FILESEPARATOR + sounds[i][1]);		
				writeFile(FOLDERNAME_SOUNDS, sounds, checkFile, bWriter, i);					
			}

			bWriter.write("***MUSIC***");
			bWriter.newLine();	

			//**************************************************
			//Music Eintraege
			//**************************************************
			for(int i = 0; i < music.length; i++){
				checkFile = new File(licenseDir + FILESEPARATOR + FOLDERNAME_MUSIC
						+ FILESEPARATOR + music[i][0] + FILESEPARATOR + music[i][1]);		
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
	 * @param type jeweiliger Assettyp
	 * @param stringFile erstelltes 2D-Array mit seperierten Eintraegen
	 * @param checkFile Pfad zu den jeweiligen Asset-Ordnern
	 * @param bWriter BufferedWriter
	 * @param i Laufvariable
	 */
	private static void writeFile(String type, String[][] stringFile, File checkFile,
			BufferedWriter bWriter, int i){

		try{
			//Pruefen, ob Datei im jeweiligen Ordner
			//vorhanden ist		
			if(checkFile.exists()){
				bWriter.write(stringFile[i][1] + " FILE OK!");
			}
			else{
				bWriter.write(stringFile[i][1] + " NOT EXISTS!");
			}
			bWriter.newLine();
			
			//Lizensierung pruefen
			if(type.equals(FOLDERNAME_PICTURES)){
				if(stringFile[i][2].toLowerCase().matches("cc-0|cc-by|selfmade")){
					bWriter.write(stringFile[i][2] + " ENTRY OK!");
				}
				else{
					bWriter.write(stringFile[i][2] + " NOT VALID!");
				}
				bWriter.newLine();
			}
		}
		catch (IOException e) {
			System.out.println("Couldn't write to logfile!");
		}
	}
}