/**
 * @author Francis Opoku, Fabian Reiber */

package com.haw.projecthorse.assetmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//testkommentar
public class AssetManager {

	private static com.badlogic.gdx.assets.AssetManager assetManager = new com.badlogic.gdx.assets.AssetManager();
	private static SpriteBatch batch;
	
	/**
	 * Laedt alle Assets. Zur Uebersicht wurden mehrere interne Methoden angelegt
	 */
	
	public static void load(){
		batch = new SpriteBatch();
		loadTextures();
		loadSounds();
		loadMusic();
		assetManager.finishLoading();
	}
	
	private static void loadTextures(){
		//assetManager.load("badlogic.jpg", Texture.class);
		assetManager.load("pictures/selfmade/logo.png", Texture.class);
		assetManager.load("pictures/cc-0/worldmap.png", Texture.class);
	}
	

	private static void loadSounds(){
		assetManager.load("sounds/flap.wav", Sound.class);
	}
	

	private static void loadMusic(){
		assetManager.load("music/life.mp3", Music.class);
	}
	
	public void dispose(){
		assetManager.dispose();
	}
	
	/**
	 * Spielt Sound ab, indem der Sound in den Arbeitsspeicher geladen wird.
	 * @param name Name der Sounddatei
	 */
	public static void playSound(String name){
		assetManager.get(name, Sound.class).play();
	}
	
	/**
	 * Spielt Musik ab, indem sie gestreamt wird.
	 * @param name Name der Musikdatei
	 */
	public static void playMusic(String name){
		assetManager.get(name, Music.class).play();
	}
	
	/**
	 * zeigt Texture an
	 * @param name Name der Name des Bildes
	 * @param x	   x-Koordinate
	 * @param y	   y-Koordinate
	 */	
	public static void showTexture(String name, int x, int y){
		batch.begin();
		batch.draw((Texture) assetManager.get(name), x, y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());		
		batch.end();
	}
	
}
