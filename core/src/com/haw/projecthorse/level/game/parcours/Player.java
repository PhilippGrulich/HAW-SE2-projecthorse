package com.haw.projecthorse.level.game.parcours;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.haw.projecthorse.player.PlayerImpl;

public class Player extends PlayerImpl {

	

	private float jumpSpeed;
	private float a,b,c;
	private float player_jumpspeed = 10;
	private float player_jumpheight = 150;
	private float player_jumpwidth = 300;
	private Rectangle r;
	float x, y;

	
	public Player() {
		super();
		toFront();
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	

	/**
	 * Berechnung von n�chstem Punkt (x,y) des Spielersprunges
	 */
	public Vector2 getNextJumpPosition() {
		float x = getX() + player_jumpspeed;
		Vector2 v = new Vector2();
		v.x = x;
		v.y = a*(x*x)+b*x+c;
		return v;
	}
	
	/**
	 * Berechnung der Sprungfunktion in abh�ngigkeit des aktuellen x und y.
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
	
	public void applyRactangle(){
		r = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void setX(float x){
		r.setX(x);
		this.x = x;
	}
	
	@Override 
	public void setY(float y){
		r.setY(y);
		this.y = y;
	}
	
	@Override
	public float getY(){
		return y;
	}
	
	@Override
	public float getX(){
		return x;
	}
	
	@Override
	public void setPosition(float x, float y){
		setX(x);
		setY(y);
	}
	
	public Rectangle getRectangle(){
		return r;
	}


}
