package com.haw.projecthorse.parcours;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;


public class GameObjectCollidable extends GameObject {


	private Polygon polygon; //Das Polygon für die Kollisionsermittlung

	
	
	/**
	 * Erzeugt ein GameObject und das dazugehörige Polygon für die Kollisionsermittlung
	 * wobei jeder Punkt des Polygons durch ein Tupel (x,y) gesetzt wird.
	 * Das Polygon besteht aus 4 Punkten, wobei 3 Punkte durch die Parameter width und height bestimmt sind,
	 * falls das Polygon nicht die exakte Größe des Objekts haben soll.
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
	public GameObjectCollidable(Texture t, String name, float velocity, float acceleration, float maxVelocity, int x, int y, int width, int height){
		super(t, name, velocity, acceleration, maxVelocity, x, y);

		this.polygon = new Polygon(new float[]{0,0,
				0, height,
				0, width,
				width, height});
		int[] positions = this.getPostions();
		this.polygon.setPosition(positions[0], positions[1]);
	}
	
	/**
	 * Erzeugt ein GameObjekt ohne x und y Koordinaten mit einem standard Polygon,
	 * das so groß wie das Objekt selbst ist.
	 * @param t
	 * @param name
	 * @param velocity
	 */
	public GameObjectCollidable(Texture t, String name, float velocity, float maxVelocity){
		super(t,name,velocity, maxVelocity);
		this.polygon = new Polygon(new float[]{0,0,
				0, t.getHeight(),
				0, t.getWidth(),
				t.getWidth(), t.getHeight()});
	}
	
	/**
	 * Setzt die Position des Objekts sowie die Positions des Polygons
	 * @param x Die x-Koordinate
	 * @param y Die y-Koordinate
	 */
	public void setPosition(int x, int y){
		this.setPosition(x, y);
		if(this.polygon == null){
			this.polygon = new Polygon(new float[]{0,0,
					0,this.getTexture().getHeight(),
					0, this.getTexture().getWidth(),
					this.getTexture().getWidth(), this.getTexture().getHeight()});
		}
		this.polygon.setPosition(x, y);
	}
	
	/**
	 * Setzt das Polygon des Objekts.
	 * @param p Das Polygon
	 */
	public void setPolygon(Polygon p){
		this.polygon = p;
	}	



	/**
	 * Liefert das Polygon zum Objekt
	 * @return
	 */
	public Polygon getPolygon() {
		return polygon;
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
