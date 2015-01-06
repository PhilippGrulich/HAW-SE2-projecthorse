package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * ObjectPool-Klasse.
 * Initialisert und hält GameObjects für weitere Zugriffe bereit.
 * Erzeugt neue GameObjects, wenn der Pool leer ist.
 * @author Francis
 * @version 1.0
 */
public class GameObjectInitializer implements
		IGameObjectInitializerFuerGameObjectLogic {

	private Random randomGenerator;
	private int probability;
	private HashMap<String, TextureRegion> regions;
	private HashMap<String, String> params; //Namen aller Objekte müssen verschieden sein.
	private ArrayList<CollidableGameObject> badCollidables; //Punkte < 0
	private ArrayList<CollidableGameObject> goodCollidables; //Punkte > 0
	private ArrayList<GameObject> nonCollidables;
	
	/**
	 * Konstruktor.
	 * @param r TextureRegions aller Assets aus Parcours.
	 */
	public GameObjectInitializer(final HashMap<String, TextureRegion> r){
		regions = r;
		randomGenerator = new Random();
		params = new HashMap<String, String>();
		goodCollidables = new ArrayList<CollidableGameObject>();
		badCollidables = new ArrayList<CollidableGameObject>();
		nonCollidables = new ArrayList<GameObject>();
		probability = 50;
	}
	
	@Override
	public float calcRelativeWidth(final float regionHeight, final float regionWidth,
			final float desiredWidth) {

		return desiredWidth * (regionWidth / regionHeight);
	}

	@Override
	public float calcRelativeHeight(final float regionHeight, final float regionWidth,
			final float desiredHeight) {

		return desiredHeight * (regionHeight / regionWidth);
	}

	//Parameter r entfernen, wenn Objekte hier erzeugt werden.
	@Override
	public GameObject initGameObject(final TextureRegion r, final String name, final int points,
			final float height, final float width, final float duration, final float x, final float y,
			final boolean collidable, final boolean isLoot, final boolean isMoveable) {
		

		
		if (collidable) {
			CollidableGameObject o = new CollidableGameObject();
			o.setTextureRegion(r);
			o.setName(name);
			o.setPoints(points);
			o.setCollidable(collidable);
			o.setRectangle(new Rectangle());
			o.setX(x);
			o.setY(y);
			o.setDuration(duration);
			o.setHeight(height);
			o.setWidth(width);
			o.setLoot(isLoot);
			o.setMoveable(isMoveable);
			o.setVisible(true);
			if(!params.containsKey(name)){
				String params = "" + points + ";" + height + ";" + width + ";" + duration + ";"
					+ x + ";" + y + ";" + collidable + ";" + isLoot + ";" + isMoveable;
				this.params.put(name, params);
			}
			
			if(points > 0){
				goodCollidables.add((CollidableGameObject) o);
			}
			
			if(points < 0){
				badCollidables.add((CollidableGameObject) o);
			}
			return o;
		} else {
			GameObject o = new GameObject();
			o = new GameObject();
			o.setTextureRegion(r);
			o.setName(name);
			o.setPoints(points);
			o.setCollidable(collidable);
			o.setX(x);
			o.setY(y);
			o.setDuration(duration);
			o.setHeight(height);
			o.setWidth(width);
			o.setLoot(isLoot);
			o.setMoveable(isMoveable);
			nonCollidables.add(o);
			return o;
		}
	}
	
	/**
	 * Getter für TextureRegion der entsprechenden Datei "name".
	 * @param name Name der Datei aus dem TextureAtlas.
	 * @return r TextureRegion.
	 */
	public TextureRegion getTextureRegion(final String name){
		return regions.get(name);
	}
	
	/**
	 * Legt ein benutztes und nicht mehr benötigtes GameObject zurück in den Objektpool.
	 * @param o GameObject
	 */
	public void passBack(final CollidableGameObject o){
		o.setX(-1000);
		if(o.getPoints() < 0){
			badCollidables.add((CollidableGameObject) o);
		}else if(o.getPoints() > 0){
			goodCollidables.add((CollidableGameObject) o);
		}
	}
	
	/**
	 * Liefert ein Kollidierbares-GameObject in Abhängigkeit der Wahrscheinlichkeit
	 * die mit setProbability gesetzt wurde.
	 * @return CollidableGameObject
	 */
	public CollidableGameObject getObject(){
		int prob = randomGenerator.nextInt(101);
		return (prob < 100 - probability) ? getRandomBadCollidable() : getRandomGoodCollidable();
	}
	
	/**
	 * Liefert ein zufälliges Hindernis.
	 * @return g Das CollidableGameObject.
	 */
	private CollidableGameObject getRandomBadCollidable(){
		if(badCollidables.size() == 0){
			int points = 0;
			String param;
			for(String key : params.keySet()){
				param = params.get(key);
				points = Integer.valueOf(param.substring(0, param.indexOf(';')));
				if(points < 0){
					param = param.substring(param.indexOf(';') + 1, param.length());
					float height = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					float width = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					float duration = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					float x = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					float y = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					boolean collidable = Boolean.parseBoolean(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					boolean isLoot = Boolean.parseBoolean(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					boolean isMoveable = Boolean.parseBoolean(param);
					CollidableGameObject co = (CollidableGameObject) initGameObject(regions.get(key), key, points, height, width, duration, x, y, 
							collidable, isLoot, isMoveable);
					badCollidables.remove(0);
					return co;
					//break;
				}
			}
			return getObject(); //never used
		}else {
			int rand = randomGenerator.nextInt(badCollidables.size());
			CollidableGameObject co = badCollidables.get(rand);
			badCollidables.remove(rand);
			return co;
		}
	}
	
	/**
	 * Liefert ein zufälliges GameObject, das bei Berührung den Punktestand erhöht.
	 * @return g Das GameObject.
	 */
	private CollidableGameObject getRandomGoodCollidable(){
		if(goodCollidables.size() == 0){
			int points = 0;
			String param;
			for(String key : params.keySet()){
				param = params.get(key);
				points = Integer.valueOf(param.substring(0, param.indexOf(';')));
				if(points > 0){
					param = param.substring(param.indexOf(';') + 1, param.length());
					float height = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					float width = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					float duration = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					float x = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					float y = Float.valueOf(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					boolean collidable = Boolean.parseBoolean(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					boolean isLoot = Boolean.parseBoolean(param.substring(0, param.indexOf(';')));
					param = param.substring(param.indexOf(';') + 1, param.length());
					boolean isMoveable = Boolean.parseBoolean(param);
					CollidableGameObject co = (CollidableGameObject)initGameObject(regions.get(key), key, points, height, width, duration, x, y, 
							collidable, isLoot, isMoveable);
					goodCollidables.remove(0);
					return co;
				}
			}
			return getObject(); //never used
		}else {
		int rand = randomGenerator.nextInt(goodCollidables.size());
		CollidableGameObject co = goodCollidables.get(rand);
		goodCollidables.remove(rand);
		return co;
		}
	}
	
	/**
	 * Setzt die Wahrscheinlichkeit mit der gute Collidables bei Aufruf von
	 * getObject() zurückgegeben werden. Wenn probability < 0% oder probability > 100% wird
	 * der Default-Wert genommen (50%).
	 * @param probability Die Wahrscheinlichkeit zw. 0% und 100%.
	 */
	public void setProbability(final int probability){
		this.probability = ((probability < 0) || (probability > 100)) ?  50 : probability;
	}
	
	/**
	 * Liefert alle GameObjects.
	 * @return g Alle GameObjects.
	 */
	public ArrayList<GameObject> getObjects(){
		ArrayList<GameObject> g = new ArrayList<GameObject>();
		g.addAll(badCollidables);
		g.addAll(goodCollidables);
		return g;
	}
	
	
}
