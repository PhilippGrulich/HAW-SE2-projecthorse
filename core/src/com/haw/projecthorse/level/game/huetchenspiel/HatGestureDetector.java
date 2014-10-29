package com.haw.projecthorse.level.game.huetchenspiel;

import com.badlogic.gdx.input.GestureDetector;

/**
 * Fuer die "Wisch"-Bewegung in alle Richtungen wird hier ein Detector und Listener
 * implementiert. Weiterhin werden die aktuellen X- und Y-Koordinaten ermittelt,
 * um ermitteln zu koennen, ob ein Hut ausgewaehlt wurde
 * @author Fabian Reiber
 *
 */
public class HatGestureDetector extends GestureDetector{
	
	/**
	 * Richtungen, deren Logik jedes Modul selber implementieren kann
	 * @author Fabian Reiber
	 *
	 */
	public interface IOnDirection{
		/**
		 * Methode für Bewegung nach links
		 */
 		void onLeft();
		/**
		 * Methode für Bewegung nach rechts
		 */
 		void onRight();
		/**
		 * Methode für Bewegung nach unten
		 */
 		void onDown();
		/**
		 * Methode für Bewegung nach oben
		 */
		void onUp(float actualX, float actualY);
 	}
	
	public HatGestureDetector(IOnDirection onDirection) {
		super(new HatGestureListener(onDirection));
	}
	
	/**
	 * innere Klasse um die jeweiligen Swipe-Typen zu implementieren
	 * @author Fabian Reiber
	 *
	 */
	private static class HatGestureListener extends GestureAdapter{

		private float actualX;
		private float actualY;
 		private IOnDirection onDirection;
 		
 		public HatGestureListener(IOnDirection directionListener){
 			this.onDirection = directionListener;
 		}
 		
 		/**
 		 * aktuelle Position des Cursors ermittelt, da touchDown vor fling aufgerufen wird
 		 */
 		@Override
 		public boolean touchDown(float x, float y, int pointer, int button) {
 			this.actualX = x;
 			this.actualY = y;
 			return false;
 		}

 		/**
 		 * erkennt eine "Wisch"-Bewegung in eine jeweilige Richtung und
 		 * delegiert an die entsprechende Methode
 		 */
 		@Override
 		public boolean fling (float velocityX, float velocityY, int button)  {
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