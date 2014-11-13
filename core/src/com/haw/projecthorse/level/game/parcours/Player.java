package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.haw.projecthorse.player.PlayerImpl;

public class Player extends PlayerImpl {

	private Action vorwaerts;
	private float vorwaertsPos;
	private Action springen;
	private Action fallen;
	private float runUp;
	private float playersPointOfView;
	private float fallenPos;
	private float jumpHeight;
	private float jumpUpTime;
	private float jumpDownTime;
//	private List<Vector2> jumpVectors;
	private float jumpSpeed;
	private float a,b,c;
	private float player_jumpspeed = 10;
	private float player_jumpheight = 150;
	private float player_jumpwidth = 300;

	
	public Player() {
		super();
		toFront();
		jumpHeight = 50;
		jumpUpTime = 0.4f;
		jumpDownTime = 0.4f;
	}
	

	/**
	 * Berechnung von nächstem Punkt (x,y) des Spielersprunges
	 */
	public Vector2 getNextJumpPosition() {
		float x = getX() + player_jumpspeed;
		Vector2 v = new Vector2();
		v.x = x;
		v.y = a*(x*x)+b*x+c;
		return v;
	}
	
	/**
	 * Berechnung der Sprungfunktion in abhängigkeit des aktuellen x und y.
	 */
	public void setupJumpFunction(){
		float x1 = getX();
		float y1 = getY();
		float x2 = getX() + (player_jumpwidth / 2f);
		float y2 = getY() + player_jumpheight;
		float x3 = getX() + player_jumpwidth;
		float y3 = getY();
		
		 a = (x1*(y2-y3)+x2*(y3-y1)+x3*(y1-y2))/((x1-x2)*(x1-x3)*(x3-x2));
		 b = ((x1*x1)*(y2-y3)+(x2*x2)*(y3-y1)+(x3*x3)*(y1-y2))/((x1-x2)*(x1-x3)*(x2-x3));
		 c = ((x1*x1)*(x2*y3-x3*y2)+x1*((x3*x3)*y2-(x2*x2)*y3)+x2*x3*y1*(x2-x3))/((x1-x2)*(x1-x3)*(x2-x3));
	}
	
	public void setJumpHeight(float y){
		this.player_jumpheight = y;
	}
	
	public void setJumpWitdh(float x){
		this.player_jumpwidth = x;
	}
	
	public void setJumpSpeed(float duration){
		this.jumpSpeed = duration;
	}
	
	public float getJumpSpeed(){
		return this.jumpSpeed;
	}

	/*
	 * public void jump() {
	 * 
	 * if (this.getX() + this.getWidth() + 2 * vorwaertsPos + fallenPos >
	 * GameField.width) {
	 * 
	 * /*for(Action a : getJumpActions()){ this.addAction(a); }
	 */
	/*
	 * cutPlayerActions(); // Vorwaerts bewegen this.addAction(vorwaerts);
	 * 
	 * // Springen this.addAction(springen);
	 * 
	 * 
	 * // Fallen this.addAction(fallen);
	 * 
	 * } else { initPlayerActions(); // Vorwaerts bewegen
	 * this.addAction(vorwaerts); this.setX(vorwaertsPos);
	 * 
	 * // Springen this.addAction(springen); this.setX(getX() + vorwaertsPos);
	 * 
	 * // Fallen this.addAction(fallen);
	 * 
	 * /*for(Action a : getJumpActions()){ this.addAction(a); }
	 */
	// }

	// }

	private List<Action> getJumpActions() {
		List<Action> actions = new ArrayList<Action>();

		for (int i = 0; i < 60; i = i + 5) {
			if (i < 20) {
				actions.add(Actions.moveTo(getX() + i, getY() + i));
				setPosition(getX() + i, getY() + i);
			} else if (i < 40) {
				actions.add(Actions.moveTo(getX() + i, getY()));
				setPosition(getX() + i, getY());
			} else if (i < 60) {
				actions.add(Actions.moveTo(getX() - i, getY() - i));
				setPosition(getX() - i, getY() - i);
			}
		}
		return actions;
	}

	private void cutPlayerActions() {
		// vorwaertsPos = GameField.width - this.getWidth()*2;
		// fallenPos = GameField.width - this.getWidth()*2;
		vorwaertsPos = GameField.width;
		fallenPos = GameField.width;

		// Vorwaerts Action
		vorwaerts = Actions.moveTo(GameField.width - (getWidth()), 0, 0.5f,
				new Interpolation() {

					@Override
					public float apply(float a) {
						// TODO Auto-generated method stub
						return a;
					}
				});

		// Springen Action
		springen = Actions.moveTo(GameField.width - (getWidth()), this.getY()
				+ this.getHeight() * jumpHeight, jumpUpTime,
				new Interpolation() {

					@Override
					public float apply(float a) {
						// TODO Auto-generated method stub
						return a;
					}
				});

		// Fallen Action
		// Fallen
		fallen = Actions.moveTo(GameField.width - (getWidth()), 0,
				jumpDownTime, new Interpolation() {

					@Override
					public float apply(float a) {
						// TODO Auto-generated method stub
						return a;
					}
				});

	}

	/**
	 * player erzeugen, x,y, breite, höhe setzen, skalieren
	 */
	public void initialize() {
		// this.setScale(1.5F);
		runUp = this.getWidth() * 1.3f;
		this.setPosition(playersPointOfView, 1);

		initPlayerActions();
	}

	private void initPlayerActions() {
		vorwaertsPos = this.getX() + runUp;
		fallenPos = vorwaertsPos + runUp / 1.5f;

		// Vorwaerts Action
		vorwaerts = Actions.moveBy(vorwaertsPos, 0, 0.5f, new Interpolation() {

			@Override
			public float apply(float a) {
				// TODO Auto-generated method stub
				return a;
			}
		});

		// Springen Action
		springen = Actions.moveTo(vorwaertsPos, this.getY() + this.getHeight()
				* jumpHeight, jumpUpTime, new Interpolation() {

			@Override
			public float apply(float a) {
				// TODO Auto-generated method stub
				return a;
			}
		});

		// Fallen Action
		// Fallen
		fallen = Actions.moveTo(fallenPos, 0, jumpDownTime,
				new Interpolation() {

					@Override
					public float apply(float a) {
						// TODO Auto-generated method stub
						return a;
					}
				});
	}

}
