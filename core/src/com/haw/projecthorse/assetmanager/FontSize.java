package com.haw.projecthorse.assetmanager;

/**
 * 
 * @author Francis Opoku und Fabian Reiber
 * Definiert die Schriftgroesse
 */
public enum FontSize {
	DREISSIG(30), VIERZIG(40), SECHZIG(60);
	
	private final int val;
	
	private FontSize(int val){
		this.val = val;
	}
	
	public int getVal(){
		return this.val;
	}
}
