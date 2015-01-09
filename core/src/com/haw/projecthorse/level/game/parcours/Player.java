package com.haw.projecthorse.level.game.parcours;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.player.PlayerImpl;
import com.haw.projecthorse.player.actions.Direction;
import com.haw.projecthorse.player.actions.AnimationAction;
import com.haw.projecthorse.player.race.HorseRace;

/**
 * Die Klasse Player enthält neben den in PlayerImpl implementierten Methoden
 * weitere Funktionalitäten die für das Verhalten des Pferdes benötigt werden.
 * @author Francis
 * @version 1.0
 */
public class Player extends PlayerImpl {

	private float playerJumpspeed; //x-Wert, den sich das Pferd beim Sprung pro Frame bewegt.
	private float a, b, c; //Variablen zur Berechnung der Sprungfunktion
	private float playerJumpheight; //Sprunghöhe des Pferds
	private float playerJumpwidth; //Sprungweite des Pferds
	private boolean jumpDirectionRight = true; //Benötigt für Berechnung d. 3 Punkte d. Sprungfunktion
	private Rectangle r; //Benötigt für Collision-Detection
	private float x, y; //x- und y-Koordinaten des Pferds
	private float swipemove; //x-Wert den sich das Pferd bei Swipe bewegt.
	private float swipeDuration = 0.2f; //Geschwindigkeit mit dem sich das Pferd bei Swipe bewegt
	private float playerHeight, playerWidth, gameWidth, gameHeight;
	private float duration; //Geschwindigkeit mit dem sich das Pferd bei eingeschaltetem Accelerometer bewegt
	private int shouldMove; //Werte 0, 1 o. 2. 0 := Nicht bewegen, 1 := nach rechts bewegen, 2 := nach links bewegen.
	private float velocityInc;
	private Direction jumpDirection;
	private SwipeListener listener;
	private float defaultAnimationSpeed;

	/**
	 * Liefert die Höhe des Spiels.
	 * @return gameHeight Spielhöhe
	 */
	public float getGameHeight() {
		return gameHeight;
	}
	
	/**
	 * Setzt die Geschwindigkeit bei eingeschaltetem Accelerometer.
	 * @param d Die Geschwindigkeit des Pferds.
	 */
	public void setDuration(final float d){
		this.duration = d;
	}

	/**
	 * Liefert die Breite des Spiels.
	 * @return gameWidth Die Spielbreite.
	 */
	public float getGameWidth() {
		return gameWidth;
	}

	/**
	 * Erzeugt ein neues Pferd u. schaltet den Swipe-Listener ein, wenn das Accelerometer
	 * nicht eingeschaltet ist.
	 * @param gameWidth Spielbreite
	 * @param gameHeight Spielhöhe
	 * @param race Die Pferderasse von HorseRace
	 */
	public Player(final float gameWidth, final float gameHeight, final HorseRace race) {
		super(race);
		toFront();
		shouldMove = 0;
		velocityInc = 1;
		jumpDirection = Direction.RIGHT;
		r = new Rectangle(getX(), getY(), getWidth()*this.getScaleX(), getHeight()*this.getScaleY());
		
		if(!GameManagerFactory.getInstance().getSettings().getAccelerometerState()){
			initSwipeListener();
		}

		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.swipemove = (getGameWidth() * 15 / 100) + athletic();
	}
	
	public float getDefaultAnimationSpeed(){
		return defaultAnimationSpeed;
	}
	
	public void setDefaultAnimationSpeed(float f){
		this.defaultAnimationSpeed = f;
	}
	
	/**
	 * Konstruktor.
	 * @param gameWidth Die Breite des Spiels.
	 * @param gameHeight Die Höhe des Spiels.
	 */
	public Player(final float gameWidth, final float gameHeight) {
		super();
		toFront();
		shouldMove = 0;
		velocityInc = 1;
		jumpDirection = Direction.RIGHT;
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		if(!GameManagerFactory.getInstance().getSettings().getAccelerometerState()){
			initSwipeListener();
		}

		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		this.swipemove = (getGameWidth() * 15 / 100) + athletic();
	}

	/**
	 * Greift auf die Eigenschaft "Athletic" des Pferds zu und liefert diese mit 5 multipliziert.
	 * @return pferd.athletic*5
	 */
	private float athletic() {
		return getAthletic() * 5;
	}


	@Override
	public void act(final float delta) {
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
	public void setX(final float x){
		r.x = x;
		this.x = x;
		super.setX(x);
	}
	
	@Override
	public void setY(final float y){
		r.y = y;
		this.y = y;
		super.setY(y);
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
	 * @param y Der Neigungswert des Devices zw. -10 und +10.
	 */
	public void shouldMove(final int m, final float y){
		this.shouldMove = m;
		this.velocityInc = y;
	}

	/**
	 * Initialisiert das Rechteck zur Collision-Detection.
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
	 * @return playerHeight Die Höhe des Pferds.
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
		return this.playerJumpspeed;
	}

	/**
	 * Prüft ob das Pferd bei Swipe nach links außerhalb des sichtbaren Bereichs des
	 * Spielfeds sein würde und liefert 0 falls dies so ist, sonst pferd.x - SWIPEMOVE.
	 * @return pferd.x-SWIPEMOVE die neue x-Koordinate des Pferds
	 */
	private float getLeftSwipePosition() {
		if (getX() - swipemove < 0) {
			return 0;
		}
		return getX() - swipemove;
	}

	/**
	 * Berechnung von nächstem Punkt (x,y) des Spielersprunges.
	 * @return v Vector mit x- und y-Koordinaten der nächsten Position des Pferds.
	 */
	public Vector2 getNextJumpPosition() {
		Vector2 v = new Vector2();
		float x = 0;

		if (isJumpDirectionRight()) {
			x = getX() + playerJumpspeed;
			v.x = x;
			v.y = a * (x * x) + b * x + c;
		} else {
			x = getX() - playerJumpspeed;
			v.x = x;
			v.y = a * (x * x) + b * x + c;
		}
		return v;
	}

	/**
	 * Liefert das Rechteck zur Collision-Detection.
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
		if (getX() + getWidth() + swipemove > getGameWidth()) {
			//Hier Beachtung d. Scaling da draw in Superklasse impl. unter Verwendung von Scaling.
			//Ohne Scaling an dieser Stelle: Methode arbeitet korrekt, doch Pferd bewegt sich außerhalb
			//d. Spielfeldrandes da berechnete Position sich nicht auf die Position auswirkt, an der das Pferd gemalt
			//wird.
			return getGameWidth() - getWidth()*getScaleX();
		}
		return getX() + swipemove;
	}

	/**
	 * Lifert die Breite des Pferds.
	 * @return playerWidth Die Breite des Pferds.
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
			public void swiped(final SwipeEvent event, final Actor actor) {
				if (getDirection() == event.getDirection()) {
					
					if (getDirection() == Direction.RIGHT) {
						setJumpDirection(Direction.RIGHT);
						addAction(Actions.moveTo(getRightSwipePosition(),
								getY(), swipeDuration));
					} else {
						setJumpDirection(Direction.LEFT);
						addAction(Actions.moveTo(getLeftSwipePosition(),
								getY(), swipeDuration));
					}
				} else {
					setAnimationSpeed(getDefaultAnimationSpeed());
					clearActions();
					addAction(new AnimationAction(event.getDirection()));
					setJumpDirection(event.getDirection());
				}
			}
		};
		this.addListener(listener);
	}
	
	/**
	 * Entfernt den Listener der auf Wisch-Bewegungen reagiert.
	 */
	public void removeSwipeListener(){
		this.removeListener(listener);
	}
	
	/**
	 * Fügt den Listener der auf Wischbewegungen reagiert, dem Pferd hinzu.
	 */
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
	 * @param h Die Höhe des Pferds ohne scale.
	 */
	@Override
	public void setHeight(final float h) {
		r.height = h*this.getScaleY();
		this.playerHeight = h;
	}

	/**
	 * Setzt die Sprungrichtung des Pferds.
	 * @param d Direction.LEFT oder Direction.RIGHT
	 */
	public void setJumpDirection(final Direction d) {
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
	private void setJumpDirectionRight(final boolean b) {
		this.jumpDirectionRight = b;
	}

	/**
	 * Setzt die Sprunghöhe des Pferds.
	 * @param y Sprunghöhe des Pferds.
	 */
	public void setJumpHeight(final float y) {
		this.playerJumpheight = y;
	}

	/**
	 * Setzt den Wert um den sich das Pferd pro Frame beim Sprung bewegt und addiert
	 * die "Athletic"-Eigenschaft des Pferds drauf.
	 * @param duration Wert um den sich das Pferd pro Frame bewegt.
	 */
	public void setJumpSpeed(final float duration) {
		this.playerJumpspeed = duration + athletic();
	}

	/**
	 * Setzt die Sprungweite des Pferds.
	 * @param x x-Wert um den sich das Pferd beim Sprung bewegt.
	 */
	public void setJumpWitdh(final float x) {
		this.playerJumpwidth = x;
	}

	/**
	 * Setzt die x- u. y-Koordinaten des Pferds und passt die x- und y-Koordinaten des
	 * Rechtecks, das zur Collision-Detection verwendet wird, an.
	 * @param x Die x-Koordinate des Pferds.
	 * @param y Die y-Koordinate des Pferds.
	 */
	@Override
	public void setPosition(final float x, final float y) {
		checkIfRectangleIsInitialized();
		r.x = x;
		r.y = y;
		this.x = x;
		this.y = y;
		super.setPosition(x, y);
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
			x2 = getX() + (playerJumpwidth / 2f);
			y2 = getY() + playerJumpheight;
			x3 = getX() + playerJumpwidth;
			y3 = getY();
		} else {

			x1 = getX();
			y1 = getY();
			x2 = getX() - (playerJumpwidth / 2f);
			y2 = getY() + playerJumpheight;
			x3 = getX() - playerJumpwidth;
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
	 * @param w Die Breite des Pferds.
	 */
	@Override
	public void setWidth(final float w) {
		r.width = w*this.getScaleX() - (w*this.getScaleX() * 20 / 100);
		this.playerWidth = w;
	}
	
	public float getJumpWidth(){
		return playerJumpwidth;
	}
}
	
