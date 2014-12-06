package com.haw.projecthorse.assetmanager;

/**
 * 
 * @author Francis Opoku und Fabian Reiber
 * Definiert die Schriftgroesse
 */
public enum FontSize {
	THIRTY(30), FORTY(40), SIXTY(60);
	
	private final int val;
	
	private FontSize(int val){
		this.val = val;
	}
	
	public int getVal(){
		return this.val;
	}
}
