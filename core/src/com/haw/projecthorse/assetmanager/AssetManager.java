/**
 * @author Francis Opoku, Fabian Reiber 
 * */

package com.haw.projecthorse.assetmanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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
			findAssetFolder(levelID, directory_pictures, Assets.PICTURES);
		}
		assetManager.finishLoading();
		
		if(assets == null){
			System.out.println("Atlas couldn't load!");
		}
		return assets;
	}
	
	/**
	 * Setzen des root-Verzeichnisses. Wird für den Zugriff auf Assets benötigt.
	 * Weiterhin werden die Sound, Music und Texture Pfade gesetzt
	 */
	private static void setApplicationRoot(){
		if (Gdx.app.getType() == ApplicationType.Android) {
			assetDir = System.getProperty("user.dir") + FILESEPARATOR + "assets";
			setApplicationType();
			} 
		else if (Gdx.app.getType() == ApplicationType.Desktop) {
			assetDir = System.getProperty("user.dir") + FILESEPARATOR + "bin";
			setApplicationType();
			}
		else{
			System.out.println("In AssetManager: No android or desktop device");
		}
		
		directory_sounds = assetDir + FILESEPARATOR + FOLDERNAME_SOUNDS;
		directory_music = assetDir + FILESEPARATOR + FOLDERNAME_MUSIC;
		directory_pictures = assetDir + FILESEPARATOR + FOLDERNAME_PICTURES;
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
		File dir = new File(path);
		ArrayList<String> content = new ArrayList<String>(Arrays.asList(dir.list()));
		
		if(content.contains(levelID)){
				loadAssets(levelID, path + FILESEPARATOR + levelID, type);
			
		}else {
			System.out.println("In AssetManager: Asset not found");
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
		File dir = new File(path);
		String[] content = dir.list();
		for(int i = 0; i< content.length; i++){
			if(new File(content[i]).isDirectory()){
				loadAssets(levelID, path + FILESEPARATOR + content[i], type);
			}else{
				if(type == Assets.SOUNDS || type == Assets.MUSIC){
					loadAudio(path, content[i], type);
				}else if(type == Assets.PICTURES){
					loadTextureAtlas(levelID, path, content[i]);
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
	 * Assettypen die zur Zeit unterstütz werden.
	 * @author Francis
	 *
	 */
	public enum Assets{
		SOUNDS, MUSIC, PICTURES
	}
	
}
