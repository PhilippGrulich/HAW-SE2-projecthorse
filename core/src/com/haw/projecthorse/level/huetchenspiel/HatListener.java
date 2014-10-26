package com.haw.projecthorse.level.huetchenspiel;



import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class HatListener extends InputListener{
	
	private int id;
	private HuetchenSpiel hs;
	private boolean found = false;
	private boolean pressed = false;
	
	/**
	 * Konstruktor
	 * @param hs Instanz des HuetchenSpiels, um auf die generierte
	 * 			 Id zugreifen zu koennen
	 * @param id Hut-Id
	 */
	public HatListener(HuetchenSpiel hs, int id){
		super();
		this.hs = hs;
		this.id = id;
	}

	/**
	 * setzt "gefunden"-Variable auf true, wenn bei korrekter
	 * Hut gewaehlt wurde
	 */
	@Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		if(!hs.getRoundFinished()){
			if(this.id == hs.rightNum){
				this.found = true;
			}
			else{
				this.pressed = true;
			}
		}
		
        return true;
    }
	
	/**
	 * tut nix
	 */
	@Override
    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
      
    }

	/**
	 * Info, ob Pferd gefunden wurde
	 * @return true fuer Horse gefunden, sonst false
	 */
	protected boolean getFound(){
		return this.found;
	}
	
	/**
	 * Setzen der "gefunden"-Variable
	 * @param found Wert festlegen, ob korrekter Hut gewaehlt
	 */
	protected void setFound(boolean found){
		this.found = found;
	}

	/**
	 * Info, ob Hut gewaehlt wurde
	 * @return true fuer gedrueckt, sonst false
	 */
	protected boolean getPressed(){
		return this.pressed;
	}

	/**
	 * Setzen des "gewaehlt"-Wertes
	 * @param pressed Wert festlegen, ob Hut gewaehlt wurde
	 */
	protected void setPressed(boolean pressed){
		this.pressed = pressed;
	}
}
