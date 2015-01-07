package com.haw.projecthorse.audiomanager;

import java.util.Observer;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

/**
 * Der AudioManager ist für die Verwaltung von Sounds (Audioeffekten) und Musik
 * zuständig. Er berücksichtigt dabei selbständig die aktuellen jeweiligen
 * Einstellungen zur App weiten Lautstärke. Er ist Observer, um das Settings
 * Objekt beobachten zu können.
 * 
 * @author Viktor
 * @version 1
 */
public interface AudioManager extends Observer, Disposable {

	/**
	 * Spielt einen Sound in der gewünschten Lautstärke ab und gibt eine
	 * Referenz auf das gemanagete Sound Objekt zurück.
	 * 
	 * @param levelId
	 *            Level ID
	 * @param name
	 *            Name der Sounddatei
	 * @return Das gemanagete Sound Objekt.
	 */
	public Sound getSound(String levelId, String name);

	/**
	 * Spielt einen Musiktitel in der gewünschten Lautstärke ab und gibt eine
	 * Referenz auf das gemanagete Music Objekt zurück. Der Musiktitel wird in
	 * einer Loop abgespielt.
	 * 
	 * @param levelId
	 *            Level ID
	 * @param name
	 *            Name der Musikdatei
	 * @return Das gemanagete Music Objekt.
	 */
	public Music getMusic(String levelId, String name);

}
