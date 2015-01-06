package com.haw.projecthorse.level.menu.credits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.assetmanager.FontSize;
import com.haw.projecthorse.inputmanager.InputManager;
import com.haw.projecthorse.level.menu.Menu;
import com.haw.projecthorse.level.menu.credits.LicenseFileParser.LicenseInfo;
import com.haw.projecthorse.level.util.uielements.DefaultScrollPane;

/**
 * Stellt einen einfachen Credit Screen dar, bestehend aus einem automatisch
 * herabscrollenden Scrolleelement. Die Lizenzdanksagungen werden automatsich
 * aus den Lizenzfiles eingelesen.
 * 
 * @author Viktor
 *
 */
public class Credits extends Menu {

	private Music music;

	private final Stage stage;

	private final Image background;

	private final VerticalGroup textContainer;
	private final ScrollPane scroller;

	public Credits() {
		// Laden und Abspielen der Hintergrundmusik
		AssetManager.loadMusic("mainMenu");
		music = audioManager.getMusic("mainMenu", "belotti.mp3");
		if (!music.isPlaying()) {
			music.setLooping(true);
			music.play();
		}

		// stage initialisieren
		stage = new Stage(getViewport());
		InputManager.addInputProcessor(stage);
		background = new Image(AssetManager.getTextureRegion("ui",
				"panel_beige"));
		background.setSize(width, height);
		stage.addActor(background);

		// Scroll Panel initialisieren
		textContainer = new VerticalGroup();
		initializeText();

		scroller = new DefaultScrollPane(textContainer, height * 0.8f, width);
		scroller.setPosition(0, height * 0.08f);
		scroller.setupFadeScrollBars(1f, 2f);
		scroller.setFadeScrollBars(true);
		stage.addActor(scroller);

	}

	// Liest den Inhalt der Licensefiles ein und erzeugt den credit Text
	private void initializeText() {

		addHeadLine("\n\n\n\n\nDas Entwicklerteam");
		addText("Fabian\nFrancis\nHuyen\nLars\nMaria\nOlli\nPhilipp\nViktor\n");

		addHeadLine("\"Projektinitiator\"");
		addText("Prof. Dr. Olaf Zukunft\n");

		addHeadLine("\n-- Danksagungen --\n");

		addHeadLine("Grafiken (CC-0)");
		addText(getEntries("pictures/cc-0_license.txt"));

		addHeadLine("Grafiken (CC-BY)");
		addText(getEntries("pictures/cc-0_license.txt"));

		addHeadLine("Musik");
		addText(getEntries("music/music.txt"));

		addHeadLine("Sound Effekte");
		addText(getEntries("sounds/sounds.txt"));

		addHeadLine("Ein besonderer Dank\n geht an das \nLibGDX Team!");

	}

	// fügt eine formatierte Kopfzeile mit Zeilenumbruch davor und danach hinzu
	private void addHeadLine(String text) {
		LabelStyle style = new LabelStyle(
				AssetManager.getHeadlineFont(FontSize.FORTY), Color.GRAY);
		Label label = new Label("\n" + text + "\n\n", style);
		label.setAlignment(Align.center);

		textContainer.addActor(label);
	}

	// fügt eine formatierte Textzeile mit Zeilenumbruch danach hinzu
	private void addText(String text) {
		LabelStyle style = new LabelStyle(
				AssetManager.getTextFont(FontSize.FORTY), Color.GRAY);
		Label label = new Label(text, style);
		label.setAlignment(Align.center);

		textContainer.addActor(label);

	}

	// liest die Authoren innerhalb eines License files mit Zeilenumbruch
	// getrennt in einen String ein
	private String getEntries(String filePath) {
		StringBuilder entries = new StringBuilder();
		LicenseFileParser parser = new LicenseFileParser(
				Gdx.files.internal(filePath));
		for (String line : parser.getLicenseInfos(
				new LicenseInfo[] { LicenseInfo.AUTHOR }, true)) {
			if (line.length() > 1) {
				entries.append(line);
				entries.append("\n");
			}

		}

		return entries.toString();
	}

	// realisiert automatisches Abwärts Scrollen
	private void autoScroll(float delta) {
		if (!scroller.isPanning() && scroller.getScrollY() < scroller.getMaxY()) {
			scroller.setScrollY(scroller.getScrollY() + delta * 80);
		}
	}

	@Override
	protected void doRender(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		autoScroll(delta);

		stage.act(delta);
		stage.draw();

	}

	@Override
	protected void doDispose() {
		music.pause();

	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doShow() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doHide() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doPause() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doResume() {
		// TODO Auto-generated method stub

	}

}
