package com.haw.projecthorse.level.menu.worldmap;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.popup.Popup;
import com.haw.projecthorse.level.util.textWrapper.TextWrapper;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;
import com.haw.projecthorse.level.util.uielements.DefaultScrollPane;
import com.haw.projecthorse.savegame.SaveGameManager;

public class TutorialPopup extends Popup {

	private float tutorialPopupHeight = height / 3;
	private String gameTitle = GameManagerFactory.getInstance().getGameConfig().getGameTitle();
	private String playerName = SaveGameManager.getLoadedGame().getPlayerName();

	public TutorialPopup() {

		Label label = createLabel(gennerateText());
		ScrollPane scollContent = new DefaultScrollPane(label, tutorialPopupHeight, popupWidth * 0.8f);

		this.addActor(createLabel("Anleitung :)"));

		this.addActor(scollContent);
		Button btn = new ButtonLarge("Los");
		btn.addListener(new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				getOverlay().disposePopup();
			}

		});
		this.addActor(btn);

	}

	private String gennerateText() {

		TextWrapper wrapper = new TextWrapper(23);
		wrapper.appendLine("Hi %s", playerName);
		wrapper.appendLine("wir hoffen das du viel Spaß mit %s hast.", gameTitle);
		wrapper.appendLine("Auf der Weltkarte kannst du mit deinem Pferd von Stadt"
				+ " zu Stadt reisen und dort tolle Spiele spielen.");
		wrapper.appendLine("Je mehr Spiele du spielst, umso schönere Pferde kannst du gewinnen.");

		return wrapper.toString();
	}

}
