package com.haw.projecthorse.level.util.textWrapper;

import com.badlogic.gdx.utils.StringBuilder;

/**
 * Über diese Klasse kann ein Text so Formatiert werden das er immer nur n
 * Zeichen pro Zeile hat.
 * 
 * @author Philipp Grulich
 * @version 1.0
 */
public class TextWrapper extends StringBuilder {
	private int charPerLine;

	/**
	 * Erstellt einen neuen TextWrappt der n Zeichen pro Zeile hat.
	 * 
	 * @param charPerLinePara
	 *            CharPerLine.
	 */
	public TextWrapper(final int charPerLinePara) {
		this.charPerLine = charPerLinePara;
	}

	/**
	 * Fügt eine neue Zeile zum den Text an.
	 * 
	 * @param s
	 *            neue Zeile
	 */
	public void appendLine(final String s) {

		String[] words = s.split(" ");
		int charsInLine = 0;
		for (String word : words) {
			if (charsInLine + word.length() > charPerLine) {
				charsInLine = 0 + word.length() + 1;
				append(System.getProperty("line.separator"));
			} else {
				charsInLine += word.length() + 1;
			}
			append(" " + word);

		}
		append(System.getProperty("line.separator"));

	}

	/**
	 * Fügt eine formatierte Zeile zu dem Text.
	 * 
	 * @param s
	 *            Zeile
	 * @param args
	 *            Formatierung.
	 */
	public void appendLine(final String s, final Object... args) {
		String formatetString = String.format(s, args);
		appendLine(formatetString);
	}
}
