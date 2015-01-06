package com.haw.projecthorse.assetmanager.exceptions;

/**
 * Ausnahme fuer Texturen die nicht gefunden werden konnten.
 * @author Francis Opoku und Fabian Reiber
 * @version 1.0
 *
 */

public class TextureNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * @param cause Fehlermeldung die angegeben werden soll
	 */
	public TextureNotFoundException(final String cause){
		super(cause);
	}

}
