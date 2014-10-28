package com.haw.projecthorse.level.game.parcours;

/**
 * @author Francis Opoku
 */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class GameObject extends Actor {
	
	private TextureRegion tr;
	private float width;
	private float height;
	private Image image;
	private ShapeRenderer renderer;
	private SpriteBatch b;
	private int gameWidth;
	private int gameHeight;
	
	public GameObject(TextureRegion t, int gameWidth, int gameHeight){
		this.tr = t;
		width = t.getRegionWidth();
		height = t.getRegionHeight();
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
	}
	
	public GameObject(TextureRegion tr){
		this.tr = tr;
		width = tr.getRegionWidth();
		height = tr.getRegionHeight();
	}
	
	public float getWidth1(){
		return width;
	}
	
	public void setScale1(float scale){
		image.setScale(scale);
	}
	
	public float getHeight1(){
		return height;
	}
	
	public TextureRegion getTextureRegion(){
		return this.tr;
	}
	
	public int getGameWidth(){
		return gameWidth;
	}
	
	public int getGameHeight(){
		return gameHeight;
	}
	
	
	  @Override
	    public void draw (Batch batch, float parentAlpha) {
		   
		  batch.draw(tr, getX(), getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
	     
}
	  
}
