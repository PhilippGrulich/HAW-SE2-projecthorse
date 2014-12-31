package com.haw.projecthorse.assetmanager;

/**
 * 
 * Definition der Schriftgroessen.
 * @author Francis Opoku und Fabian Reiber
 * @version 1.0
 */
public enum FontSize {
	THIRTY(30), FORTY(40), SIXTY(60);
	
	private final int val;
	
	/**
	 * Konstruktor zum festlegen der Schriftgroesse.
	 * @param val Wert der Schriftgroesse 
	 */
	private FontSize(final int val){
		this.val = val;
	}
	
	public int getVal(){
		return this.val;
	}
}
