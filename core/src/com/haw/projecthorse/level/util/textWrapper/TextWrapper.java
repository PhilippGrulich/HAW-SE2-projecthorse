package com.haw.projecthorse.level.util.textWrapper;

import com.badlogic.gdx.utils.StringBuilder;

public class TextWrapper extends StringBuilder {
	private int charPerLine;

	public TextWrapper(final int charPerLinePara) {
		this.charPerLine = charPerLinePara;
	}

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

	public void appendLine(final String s, Object... args) {
		String formatetString = String.format(s, args);
		appendLine(formatetString);
	}
}
