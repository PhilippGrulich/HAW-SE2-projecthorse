package com.haw.projecthorse.level.game.thimblerig;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Fuer die "Wisch"-Bewegung in alle Richtungen wird hier ein Detector und Listener
 * implementiert. Weiterhin werden die aktuellen X- und Y-Koordinaten ermittelt,
 * um ermitteln zu koennen, ob ein Hut ausgewaehlt wurde.
 * @author Fabian Reiber
 * @version 1.0
 *
 */
public class HatGestureDetector extends GestureDetector{
	
	/**
	 * Richtungen, deren Logik jedes Modul selber implementieren kann.
	 * @author Fabian Reiber
	 * @version 1.0
	 *
	 */
	public interface IOnDirection{
		/**
		 * Methode für Bewegung nach links.
		 */
 		void onLeft();
		/**
		 * Methode für Bewegung nach rechts.
		 */
 		void onRight();
		/**
		 * Methode für Bewegung nach unten.
		 */
 		void onDown();
		/**
		 * Methode für Bewegung nach oben.
		 * @param actualX aktuelle x-Position des Cursors
		 * @param actualY aktuelle y-Position des Cursors
		 */
		void onUp(float actualX, float actualY);
 	}
	
	/**
	 * Konstruktor.
	 * @param onDirection Richtungsangabe
	 * @param stage aktuelle Stage auf der der Detector laeuft
	 */
	public HatGestureDetector(final IOnDirection onDirection, final Stage stage) {
		super(new HatGestureListener(onDirection, stage));
	}
	
	/**
	 * innere Klasse um die jeweiligen Swipe-Typen zu implementieren.
	 * @author Fabian Reiber
	 * @version 1.0
	 *
	 */
	private static class HatGestureListener extends GestureAdapter{
		
		private Stage stage;
		private float actualX;
		private float actualY;
 		private IOnDirection onDirection;
 		
 		/**
 		 * Konstruktor.
 		 * @param directionListener Richtungsangabe
 		 * @param stage aktuelle Stage auf der der Detector laeuft
 		 */
 		public HatGestureListener(final IOnDirection directionListener, final Stage stage){
 			this.onDirection = directionListener;
 			this.stage = stage;
 		}
 		
 		/**
 		 * aktuelle Position des Cursors ermittelt, da touchDown vor fling aufgerufen wird
 		 * mit dem Vektor werden immer die aktuellen stage-koordinaten gewaehtl, da bei 
 		 * einem rezsie sich diese aendern.
 		 * @param x erkannte x-Position des Cursors
 		 * @param y erkannte y-Position des Cursors
 		 * @param pointer 
 		 * @param button 
 		 * @return true, falls touchDown erfolgreich, sonst false
 		 */
 		public boolean touchDown(final float x, final float y, final int pointer, final int button) {
			Vector2 stageCoordinates = stage.screenToStageCoordinates(new Vector2(x, y));
 			this.actualX = stageCoordinates.x;
 			this.actualY = stageCoordinates.y;
 			return false;
 		}

 		/**
 		 * erkennt eine "Wisch"-Bewegung in eine jeweilige Richtung und
 		 * delegiert an die entsprechende Methode.
 		 * @param velocityX Geschwindigkeit in x-Richtung
 		 * @param velocityY Geschwindigkeit in y-Richtung
 		 * @param button 
 		 * @return true, falls fling erfolgreich, sonst false
 		 */
 		public boolean fling(final float velocityX, final float velocityY, final int button)  {
			if(Math.abs(velocityX)>Math.abs(velocityY)){
 				if(velocityX>0){
 					onDirection.onRight();
 				}else{
 					onDirection.onLeft();
 				}
 			}else{
 				if(velocityY>0){
 					onDirection.onDown();
 				}else {
 					onDirection.onUp(this.actualX, this.actualY);	
 				}
 			}
 			return super.fling(velocityX, velocityY, button);
 		}
	}
}
