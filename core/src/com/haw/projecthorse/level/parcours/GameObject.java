package com.haw.projecthorse.level.parcours;

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
	
	private Texture t;
	private TextureRegionDrawable tr;
	private float width;
	private float height;
	private Image image;
	private ShapeRenderer renderer;
	private SpriteBatch b;
	
	public GameObject(Texture t){
		this.t = t;
		width = t.getWidth();
		height = t.getHeight();
	}
	
	public GameObject(TextureRegionDrawable tr){
		this.tr = tr;
		width = tr.getRegion().getRegionWidth();
		height = tr.getRegion().getRegionHeight();
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
	
	public Texture getTexture(){
		return t;
	}
	
	
	
	  @Override
	    public void draw (Batch batch, float parentAlpha) {
		   
		  batch.draw(t, getX(), getY(), this.getScaleX() * this.getWidth(), this.getScaleY() * this.getHeight());
	     
}
	  
}
