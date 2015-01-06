package com.haw.projecthorse.level.menu.credits;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.files.FileHandle;

/**
 * Eine Klasse, die einen Parser für die License Files bereit stellt.
 * 
 * @author Viktor
 * @version 1
 *
 */
public class LicenseFileParser {

	/**
	 * Hierüber kann bestimmt werden, welche Informationen der LicenseFilePaser
	 * liefern soll.
	 *
	 */
	enum LicenseInfo {
		// Die Reihenfolge der Werte muss der der Einträge in den
		// License Files entsprechen, damit die jeweiligen Indizes
		// über getOrdinal() abgerufen werden können
		USAGE, FILENAME, LICENSETYPE, AUTHOR, SOURCE
	}

	/**
	 * Fehlerklasse, mit der bestimmt wird ob die Datei zuende ist.
	 * 
	 * @author Viktor
	 *
	 */
	class FileEndException extends Exception {
		private static final long serialVersionUID = 6913712433069188209L;

	}

	private final Scanner scanner;
	private static final int ENTRYCOUNT = 5; // Anzahl der Spalten in den
												// License Files

	/**
	 * Der Default Konstruktor.
	 * 
	 * @param licenseFile
	 *            Das zu lesende License File
	 */
	public LicenseFileParser(final FileHandle licenseFile) {
		scanner = new Scanner(licenseFile.read());
	}

	/**
	 * Liest die nächste korrekte Zeile in ein ein StringArray ein.
	 * 
	 * @return Das StringArray.
	 * @throws FileEndException
	 *             Wird geworfen, wenn die Datei zuende ist
	 */

	private String[] parseLine() throws FileEndException {
		String[] entries;
		while (scanner.hasNextLine()) {
			entries = scanner.nextLine().split(";");

			// ignoriere Zeile falls sie nicht dem erwarteten Format entspricht
			if (entries.length == ENTRYCOUNT) {
				return entries;
			}
		}
		throw new FileEndException();
	}

	/**
	 * Liefert ein Liste von Strings, die je eine Zeile mit den über den
	 * Parameter angefragten kommaseparierten Informationen enthalten.
	 * 
	 * @param types
	 *            Bestimmt Art und Reihenfolge der Informationen
	 * @param uniqueEntries
	 *            false = Mehrfacheinträge erlaubt, true = keine
	 *            Mehrfacheinträge
	 * @return Kommaseparierte Informationszeilen in einem Array, Null falls
	 *         Parameter ungültig
	 */
	public ArrayList<String> getLicenseInfos(final LicenseInfo[] types,
			final boolean uniqueEntries) {
		if (types == null || types.length < 1) {
			return null;
		}

		ArrayList<String> result = new ArrayList<String>();
		String[] line;

		while (scanner.hasNextLine()) {
			try {
				line = parseLine();
			} catch (FileEndException e) {
				break;
			}

			StringBuilder infos = new StringBuilder();
			for (LicenseInfo info : types) {
				infos.append(line[info.ordinal()]);
				infos.append(", ");
			}

			String infoString = infos.substring(0, infos.length() - 2).trim();
			if (!uniqueEntries || !result.contains(infoString)) {
				result.add(infoString);
			}
		}

		return result;
	}

}
