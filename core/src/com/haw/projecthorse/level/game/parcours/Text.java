package com.haw.projecthorse.level.game.parcours;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor{
	
	private BitmapFont font;
	private String message;
	private float x;
	private float y;
	
	public Text(BitmapFont f, String message, float x, float y){
		font = f;
		this.message = message;
		this.x=x;
		this.y=y;
	}
	
	public void setText(String text){
		this.message = text;
	}
	
	public String getText(){
		return message;
	}
	
	public void setX1(float x){
		this.x = x;
	}
	
	public void setY1(float y){
		this.y = y;
	}
	
	@Override
	public void draw (Batch batch, float parentAlpha) {
		/*batch.end dann eigenen batch.begin, fond.draw batch.end dann batch.begin*/
		
		font.draw(batch, message, x, y);
	}
	
	public void setColor(float r, float g, float b, float a){
		font.setColor(r, g, b, a);
	}
	
	public void setColor(Color c){
		font.setColor(c);
	}
}
