package com.haw.projecthorse.level.menu.worldmap;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.StringBuilder;
import com.haw.projecthorse.gamemanager.GameManagerFactory;
import com.haw.projecthorse.level.util.overlay.popup.Popup;
import com.haw.projecthorse.level.util.uielements.ButtonLarge;
import com.haw.projecthorse.savegame.SaveGameManager;

public class TutorialPopup extends Popup {

	private float tutorialPopupHigh = height / 2;
	private String gameTitle = GameManagerFactory.getInstance().getGameConfig().getGameTitle();
	private String playerName = SaveGameManager.getLoadedGame().getPlayerName();

	class MyStringBuilder extends StringBuilder {
		private int charPerLine;

		public MyStringBuilder(final int charPerLinePara) {
			this.charPerLine = charPerLinePara;
		}

		public void appendLine(final String s) {

			String[] words = s.split(" ");
			int line = 0;
			for (String word : words) {
				if (line + word.length() > charPerLine) {
					line = 0;
					append(System.getProperty("line.separator"));
				} else {
					line += word.length();
				}
				append(" " + word);

			}
			append(System.getProperty("line.separator"));

		}
	};

	public TutorialPopup() {

		VerticalGroup table = new VerticalGroup() {
			@Override
			public float getPrefHeight() {
				return tutorialPopupHigh;
			}
		};

		ScrollPaneStyle paneStyle = new ScrollPaneStyle();

		Label label = createLabel(gennerateText());

		ScrollPane scollContent = new ScrollPane(label, paneStyle) {
			@Override
			public float getPrefHeight() {
				return tutorialPopupHigh - 150;
			}
		};

		scollContent.setSize(label.getPrefWidth(), tutorialPopupHigh);
		table.addActor(scollContent);
		Button btn = new ButtonLarge("Los");
		btn.addListener(new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				getOverlay().disposePopup();
			}

		});
		table.addActor(btn);
		table.setSize(label.getPrefWidth(), tutorialPopupHigh);
		this.addActor(table);
	}

	private String gennerateText() {

		MyStringBuilder sb = new MyStringBuilder(18);
		sb.appendLine("Hi " + playerName);
		sb.appendLine("wir hoffen das du viel Spaß mit " + gameTitle + " hast.");
		sb.appendLine("Auf der Weltkarte kannst du mit deinem Pferd von Stadt"
				+ " zu Stadt reisen und dort tolle Spiele spielen.");
		sb.appendLine("Je mehr Spiele du spielst umso schönere Pferde kannst du gewinnen.");

		return sb.toString();
	}

}
