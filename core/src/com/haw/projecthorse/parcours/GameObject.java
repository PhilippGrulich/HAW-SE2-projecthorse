package com.haw.projecthorse.parcours;

import com.badlogic.gdx.graphics.Texture;

public abstract class GameObject {
	private float acceleration; //Beschleunigung
	private float maxVelocity; //Maximalgeschwindigkeit
	private float velocity; //Geschwindigkeit aktuell
	private String name; //Name des Objekts
	private Texture object; //Das Objekt
	private int[] position = new int[2]; //Die x und y Koordinaten des Objekts und Polygons
	private Movement m; //Die Bewegungsrichtung
	
	
	/**
	 * Erzeugt ein GameObject und das dazugehörige Polygon für die Kollisionsermittlung.
	 * 
	 * @param t Das Bild als Texture
	 * @param name Der Name des Bildes
	 * @param velocity Die Geschwindigkeit des Bildes
	 * @param x Die x Koordinate
	 * @param y Die y Koordinate
	 * @param width Die Breite des Polygons
	 * @param height Die Höhe des Polygons
	 * @param acceleration Die Beschleunigung des Objekts
	 * @param maxVelocity Die Höchstgeschwindigkeit des Objekts
	 */
	public GameObject(Texture t, String name, float velocity, float acceleration, float maxVelocity, int x, int y){
		this.object = t;
		this.name = name;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.maxVelocity = maxVelocity;
		this.position[0] = x;
		this.position[1] = y;
	}
	
	/**
	 * Erzeugt ein GameObjekt ohne x und y Koordinaten und ohne Polygon.
	 * Das Polygon wird beim Setzen der x und y Koordinaten erzeugt oder
	 * manuel über setPolygon.
	 * @param t
	 * @param name
	 * @param velocity
	 */
	public GameObject(Texture t, String name, float velocity, float maxVelocity){
		this.object = t;
		this.name = name;
		this.velocity = velocity;
		this.maxVelocity = maxVelocity;
	}
	
	/**
	 * Setzt die Position des Objekts sowie die Positions des Polygons
	 * @param x Die x-Koordinate
	 * @param y Die y-Koordinate
	 */
	public void setPosition(int x, int y){
		this.position[0] = x;
		this.position[1] = y;
	}
	
	
	/**
	 * Setzt die Geschwindigkeit
	 * @param f Die neue Geschwindigkeit
	 */
	public void setVelocity(float f){
		this.velocity = f;
	}
	
	/**
	 * Liefert die Geschwindigkeit
	 * @return velocity Die aktuelle Geschwindkgeit
	 */
	public float getVelocity(){
		return this.velocity;
	}
	
	/**
	 * Liefert die Beschleunigung
	 * @return acceleration Die Beschleunigung
	 */
	public float getAcceleration() {
		return this.acceleration;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public float getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	/**
	 * Liefert den Namen
	 * @return name Der Name des Objekts
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen
	 * @param name Der Name als String
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Liefert die Bewegungsrichtung
	 * @return
	 */
	public Movement getDirection() {
		return m;
	}
	
	/**
	 * Setzt die Bewegungsrichtung
	 * @param m Enum
	 */
	public void setDirection(Movement m) {
		this.m = m;
	}

	public int[] getPostions(){
		return this.position;
	}
	
	public Texture getTexture(){
		return this.getTexture();
	}
	
	/**
	 * 
	 * @author Francis
	 *
	 */
	public enum Movement{
		UP, DOWN, LEFT, RIGHT
	}
}
