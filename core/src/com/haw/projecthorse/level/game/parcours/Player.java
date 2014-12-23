package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.race.HorseRace;
import com.haw.projecthorse.player.race.Race;

public class Player extends PlayerImpl {

	private float player_jumpspeed; //x-Wert, den sich das Pferd beim Sprung pro Frame bewegt.
	private float a, b, c; //Variablen zur Berechnung der Sprungfunktion
	private float player_jumpheight; //Sprunghöhe des Pferds
	private float player_jumpwidth; //Sprungweite des Pferds
	private boolean jumpDirectionRight = true; //Benötigt für Berechnung d. 3 Punkte d. Sprungfunktion
	private Rectangle r; //Benötigt für Collision-Detection
	private float x, y; //x- und y-Koordinaten des Pferds
	private float SWIPEMOVE; //x-Wert den sich das Pferd bei Swipe bewegt.
	private float SWIPEDURATION = 0.2f; //Geschwindigkeit mit dem sich das Pferd bei Swipe bewegt
	private float playerHeight, playerWidth, gameWidth, gameHeight;
	private float duration; //Geschwindigkeit mit dem sich das Pferd bei eingeschaltetem Accelerometer bewegt
	private int shouldMove; //Werte 0, 1 o. 2. 0 := Nicht bewegen, 1 := nach rechts bewegen, 2 := nach links bewegen.
	private float velocityInc;
	private Direction jumpDirection;
	private SwipeListener listener;
	private PlayerImpl p;

	/**
	 * Liefert die Höhe des Spiels
	 * @return Spielhöhe
	 */
	public float getGameHeight() {
		return gameHeight;
	}
	
	/**
	 * Setzt die Geschwindigkeit bei eingeschaltetem Accelerometer
	 * @param d Die Geschwindigkeit des Pferds
	 */
	public void setDuration(float d){
		this.duration = d;
	}

	/**
	 * Liefert die Breite des Spiels
	 * @return Die Spielbreite
	 */
	public float getGameWidth() {
		return gameWidth;
	}

	/**
	 * Erzeugt ein neues Pferd u. schaltet den Swipe-Listener ein, wenn das Accelerometer
	 * nicht eingeschaltet ist.
	 * @param gameWidth Spielbreite
	 * @param gameHeight Spielhöhe
	 */
	public Player(float gameWidth, float gameHeight, HorseRace race) {
		super(race);
		
		toFront();
		shouldMove = 0;
		velocityInc = 1;
		jumpDirection = Direction.RIGHT;
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		if(!GameManagerFactory.getInstance().getSettings().getAccelerometerState())
			initSwipeListener();

		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.SWIPEMOVE = (getGameWidth() * 15 / 100) + athletic();
	}
	

	public Player(float gameWidth, float gameHeight) {
		super();
		toFront();
		shouldMove = 0;
		velocityInc = 1;
		jumpDirection = Direction.RIGHT;
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		if(!GameManagerFactory.getInstance().getSettings().getAccelerometerState())
			initSwipeListener();

		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.SWIPEMOVE = (getGameWidth() * 15 / 100) + athletic();
	}

	/**
	 * Greift auf die Eigenschaft "Athletic" des Pferds zu und liefert diese mit 5 multipliziert.
	 * @return pferd.athletic*5
	 */
	private float athletic() {
		return getAthletic() * 5;
	}


	@Override
	public void act(float delta) {
		super.act(delta);
		if(shouldMove == 2){
				setX(getX() - this.duration*delta*velocityInc);
				shouldMove = 0;
				velocityInc = 1;
		}else if(shouldMove == 1){
			setX(getX() + this.duration*delta*velocityInc);
			shouldMove = 0;
			velocityInc = 1;
		}
	}
	
	@Override
	public void setX(float x){
		r.x = x;
		this.x = x;
	}
	
	@Override
	public void setY(float y){
		r.y = y;
		this.y = y;
	}
	
	/**
	 * Liefert 0, 1 o. 2.
	 * 0 := Nicht bewegen
	 * 1 := nach rechts bewegen
	 * 2 := nach links bewegen.
	 * @return shouldMove := 0, 1 o. 2
	 */
	public int getShouldMove(){
		return shouldMove;
	}
	
	/**
	 * m = 0 := Nicht bewegen
	 * m = 1 := nach rechts bewegen
	 * m = 2 := nach links bewegen.
	 * @param m 0, 1 o. 2
	 */
	public void shouldMove(int m, float y){
		this.shouldMove = m;
		this.velocityInc = y;
	}

	/**
	 * Initialisiert das Rechteck zur Collision-Detection
	 */
	public void applyRactangle() {
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	/**
	 * Prüft ob Rechteck zur Collision-Detection initalisiert ist.
	 */
	private void checkIfRectangleIsInitialized() {
		if (r == null) {
			r = new Rectangle();
		}
	}

	/**
	 * Liefert die Höhe des Pferds.
	 */
	@Override
	public float getHeight() {
		return playerHeight;
	}

	/**
	 * Lifert die Sprunggewschwindigkeit.
	 * @return player_jumpspeed := x-Wert den sich das Pferd beim Sprung pro Frame bewegt.
	 */
	public float getJumpSpeed() {
		return this.player_jumpspeed;
	}

	/**
	 * Prüft ob das Pferd bei Swipe nach links außerhalb des sichtbaren Bereichs des
	 * Spielfeds sein würde und liefert 0 falls dies so ist, sonst pferd.x - SWIPEMOVE.
	 * @return pferd.x-SWIPEMOVE die neue x-Koordinate des Pferds
	 */
	private float getLeftSwipePosition() {
		if (getX() - SWIPEMOVE < 0) {
			return 0;
		}
		return getX() - SWIPEMOVE;
	}

	/**
	 * Berechnung von nächstem Punkt (x,y) des Spielersprunges
	 */
	public Vector2 getNextJumpPosition() {
		Vector2 v = new Vector2();
		float x = 0;

		if (isJumpDirectionRight()) {
			x = getX() + player_jumpspeed;
			v.x = x;
			v.y = a * (x * x) + b * x + c;
		} else {
			x = getX() - player_jumpspeed;
			v.x = x;
			v.y = a * (x * x) + b * x + c;
		}
		return v;
	}

	/**
	 * Lifert das Rechteck zur Collision-Detection
	 * @return r Das Rechteck das zur Collision-Detection verwendet wird.
	 */
	public Rectangle getRectangle() {
		return r;
	}
	/**
	 * Prüft ob das Pferd bei Swipe nach rechts außerhalb des sichtbaren Bereichs des
	 * Spielfeds sein würde. Liefert x-Koordinate, sodass Pferd nicht außerhalb des rechten
	 * Spielfeldbereichs sein wird.
	 * @return x Die neue x-Koordinate des Pferds
	 */
	private float getRightSwipePosition() {
		if (getX() + getWidth() + SWIPEMOVE > getGameWidth()) {
			//Hier Beachtung d. Scaling da draw in Superklasse impl. unter Verwendung von Scaling.
			//Ohne Scaling an dieser Stelle: Methode arbeitet korrekt, doch Pferd bewegt sich außerhalb
			//d. Spielfeldrandes da berechnete Position sich nicht auf die Position auswirkt, an der das Pferd gemalt
			//wird.
			return getGameWidth() - getWidth()*getScaleX();
		}

		System.out.println("! " + (getX() + getWidth() + SWIPEMOVE + " > " + getGameWidth()));
		return getX() + SWIPEMOVE;
	}

	/**
	 * Lifert die Breite des Pferds.
	 */
	@Override
	public float getWidth() {
		return playerWidth;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	/**
	 * Initialisiert den Swipe-Listener des Pferds.
	 */
	private void initSwipeListener() {
		 listener = new SwipeListener() {

			@Override
			public void swiped(SwipeEvent event, Actor actor) {
				if (getDirection() == event.getDirection()) {
					
					if (getDirection() == Direction.RIGHT) {
						setJumpDirection(Direction.RIGHT);
						addAction(Actions.moveTo(getRightSwipePosition(),
								getY(), SWIPEDURATION));
					} else {
						setJumpDirection(Direction.LEFT);
						addAction(Actions.moveTo(getLeftSwipePosition(),
								getY(), SWIPEDURATION));
					}
				} else {
					setAnimationSpeed(0.3f);
					clearActions();
					addAction(new AnimationAction(event.getDirection()));
					setJumpDirection(event.getDirection());
				}
			}
		};
		this.addListener(listener);
	}
	
	public void removeSwipeListener(){
		this.removeListener(listener);
	}
	
	public void addSwipeListener(){
		if(listener == null){
			initSwipeListener();
		}else{
		this.addListener(listener);
		}
	}

	private boolean isJumpDirectionRight() {
		return jumpDirectionRight;
	}

	/**
	 * Setzt die Höhe des Pferds und des Rechtecks zur Collision-Detection.
	 */
	@Override
	public void setHeight(float h) {
		r.height = h;
		this.playerHeight = h;
	}

	/**
	 * Setzt die Sprungrichtung des Pferds.
	 * @param d Direction.LEFT oder Direction.RIGHT
	 */
	public void setJumpDirection(Direction d) {
		if (d == Direction.RIGHT) {
			setJumpDirectionRight(true);
		} else {
			setJumpDirectionRight(false);
		}
		jumpDirection = d;
	}
	
	public Direction getJumpDirection(){
		return jumpDirection;
	}

	/**
	 * Setzt ein boolean der verwendet wird um zu Prüfen ob das Pferd nach rechts springt.
	 * @param b true, wenn das Pferd nach rechts springt, sonst false.
	 */
	private void setJumpDirectionRight(boolean b) {
		this.jumpDirectionRight = b;
	}

	/**
	 * Setzt die Sprunghöhe des Pferds.
	 * @param y Sprunghöhe des Pferds.
	 */
	public void setJumpHeight(float y) {
		this.player_jumpheight = y;
	}

	/**
	 * Setzt den Wert um den sich das Pferd pro Frame beim Sprung bewegt und addiert
	 * die "Athletic"-Eigenschaft des Pferds drauf.
	 * @param duration Wert um den sich das Pferd pro Frame bewegt.
	 */
	public void setJumpSpeed(float duration) {
		this.player_jumpspeed = duration + athletic();
	}

	/**
	 * Setzt die Sprungweite des Pferds.
	 * @param x x-Wert um den sich das Pferd beim Sprung bewegt.
	 */
	public void setJumpWitdh(float x) {
		this.player_jumpwidth = x;
	}

	/**
	 * Setzt die x- u. y-Koordinaten des Pferds und passt die x- und y-Koordinaten des
	 * Rechtecks, das zur Collision-Detection verwendet wird, an.
	 */
	@Override
	public void setPosition(float x, float y) {
		checkIfRectangleIsInitialized();
		r.x = x;
		r.y = y;
		this.x = x;
		this.y = y;

	}

	/**
	 * Berechnung der Sprungfunktion in Abhängigkeit des aktuellen x und y.
	 */
	public void setupJumpFunction() {
		float x1 = 0;
		float y1 = 0;
		float x2 = 0;
		float y2 = 0;
		float x3 = 0;
		float y3 = 0;

		if (isJumpDirectionRight()) {
			x1 = getX();
			y1 = getY();
			x2 = getX() + (player_jumpwidth / 2f);
			y2 = getY() + player_jumpheight;
			x3 = getX() + player_jumpwidth;
			y3 = getY();
		} else {

			x1 = getX();
			y1 = getY();
			x2 = getX() - (player_jumpwidth / 2f);
			y2 = getY() + player_jumpheight;
			x3 = getX() - player_jumpwidth;
			y3 = getY();
		}

		a = (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2))
				/ ((x1 - x2) * (x1 - x3) * (x3 - x2));
		b = ((x1 * x1) * (y2 - y3) + (x2 * x2) * (y3 - y1) + (x3 * x3)
				* (y1 - y2))
				/ ((x1 - x2) * (x1 - x3) * (x2 - x3));
		c = ((x1 * x1) * (x2 * y3 - x3 * y2) + x1
				* ((x3 * x3) * y2 - (x2 * x2) * y3) + x2 * x3 * y1 * (x2 - x3))
				/ ((x1 - x2) * (x1 - x3) * (x2 - x3));
	}

	/**
	 * Setzt die Breite des Pferds- und des Rechtecks, das zur Collision-Detection verwendet wird.
	 */
	@Override
	public void setWidth(float w) {
		r.width = w;
		this.playerWidth = w;
	}
	
	public float getJumpWidth(){
		return player_jumpwidth;
	}
}
	
