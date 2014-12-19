package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObjectInitializer implements
		IGameObjectInitializerFuerGameObjectLogic {

	private Random randomGenerator;
	private int probability;
	private HashMap<String, TextureRegion> regions;
	private HashMap<String, String> params; //Namen aller Objekte müssen verschieden sein.
	private ArrayList<CollidableGameObject> badCollidables; //Punkte < 0
	private ArrayList<CollidableGameObject> goodCollidables; //Punkte > 0
	
	public GameObjectInitializer(HashMap<String, TextureRegion> r){
		regions = r;
		randomGenerator = new Random();
		params = new HashMap<String, String>();
		goodCollidables = new ArrayList<CollidableGameObject>();
		badCollidables = new ArrayList<CollidableGameObject>();
		probability = 50;
	}
	
	@Override
	public float calcRelativeWidth(float regionHeight, float regionWidth,
			float desiredWidth) {

		return desiredWidth * (regionWidth / regionHeight);
	}

	@Override
	public float calcRelativeHeight(float regionHeight, float regionWidth,
			float desiredHeight) {

		return desiredHeight * (regionHeight / regionWidth);
	}

	//Parameter r entfernen, wenn Objekte hier erzeugt werden.
	@Override
	public GameObject initGameObject(TextureRegion r, String name, int points,
			float height, float width, float duration, float x, float y,
			boolean collidable, boolean isLoot, boolean isMoveable) {
		
		GameObject o;
		
		if (collidable) {
			o = new CollidableGameObject();
			o.setTextureRegion(r);
			o.setName(name);
			o.setPoints(points);
			o.setCollidable(collidable);
			((CollidableGameObject) o).setRectangle(new Rectangle());
			o.setX(x);
			o.setY(y);
			o.setDuration(duration);
			o.setHeight(height);
			o.setWidth(width);
			o.setLoot(isLoot);
			o.setMoveable(isMoveable);
			
			if(!params.containsKey(name)){
				String params = "" + points + ";" + "" + height + ";" + width + "" + duration + ";"
					+ x + ";" + y + ";" + collidable + ";" + isLoot + ";" + isMoveable;
				this.params.put(name, params);
			}
			
			if(points > 0)
				goodCollidables.add((CollidableGameObject) o);
			
			if(points < 0)
				badCollidables.add((CollidableGameObject) o);
			
			return o;
		} else {
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
			return o;
		}
	}
	
	public TextureRegion getTextureRegion(String name){
		return regions.get(name);
	}
	
	public void reset(){
		
	}
	
	/**
	 * Legt ein benutztes und nicht mehr benötigtes GameObject zurück in den Objektpool.
	 * @param o GameObject
	 */
	public void passBack(GameObject o){
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
					initGameObject(regions.get(key), key, points, height, width, duration, x, y, 
							collidable, isLoot, isMoveable);
					break;
				}
			}
			return getObject();
		}else {
			int rand = randomGenerator.nextInt(badCollidables.size());
			CollidableGameObject co = badCollidables.get(rand);
			badCollidables.remove(rand);
			return co;
		}
	}
	
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
					initGameObject(regions.get(key), key, points, height, width, duration, x, y, 
							collidable, isLoot, isMoveable);
					break;
				}
			}
			return getObject();
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
	 * @param probability
	 */
	public void setProbability(int probability){
		this.probability = ((probability < 0) || (probability > 100)) ?  50 : probability;
	}
	
	
}
