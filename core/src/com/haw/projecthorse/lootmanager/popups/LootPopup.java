package com.haw.projecthorse.lootmanager.popups;

import java.util.ArrayList;
import java.util.HashSet;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import com.haw.projecthorse.level.util.overlay.popup.Popup;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.level.util.uielements.ButtonSmall;
import com.haw.projecthorse.level.util.uielements.ButtonSmall.ButtonType;
import com.haw.projecthorse.lootmanager.Lootable;

/**
 * Popup, das alle gesammelten Loots aus der {@link com.haw.projecthorse.lootmanager.Chest}Chest anzeigt.
 * 
 * @author Oliver
 * @version 1.0
 */

public class LootPopup extends Popup {

	private int lootPopupHeight = height / 3;

	/**
	 * Konstruktor.
	 * 
	 * @param loots
	 *            Liste alles Loots, die angezeigt werden sollen.
	 */
	public LootPopup(final HashSet<Lootable> loots) {
		super();

		Label label = createLabel("Deine gesammelten Gegenstände");
		// label.setWidth(popupWidth - 80);
		// label.setWrap(true);
		// label.layout();
		addActor(label);

		LootDisplay button = new LootDisplay(loots, lootPopupHeight,
				popupWidth - 80);
		addActor(button);

		Button ok = createButton("OK");
		ok.addListener(new ChangeListener() {

			@Override
			public void changed(final ChangeEvent event, final Actor actor) {
				LootPopup.this.getOverlay().disposePopup();
				event.cancel();
			}
		});
		addActor(ok);
	}

	/**
	 * Display in dem jeweils ein Loot dargestellt wird. Man kann zwischen den
	 * einzelnen Loots hin und her wechseln.
	 * 
	 * @author Oliver
	 */
	private class LootDisplay extends Group {
		private ArrayList<Lootable> lootList;
		private int i, max;
		private float imageX, imageY, imageWidth, imageHeight;
		private Image currentImage = null;
		private Label lootName = null;
		private boolean refreshed; // zeigt an, ob sich der Index vom Image
									// geändert hat und somit die Daten dafür
									// neu berechnet werden müssen

		/**
		 * Konstruktor.
		 * 
		 * @param loots
		 *            Alle Loots, die angezeigt werden sllen
		 * @param lootHeight
		 *            Höhe des Displays
		 * @param lootWidth
		 *            Breite es Displays
		 */
		public LootDisplay(final HashSet<Lootable> loots, final int lootHeight,
				final int lootWidth) {
			// Instanzvariable instazieren
			lootList = new ArrayList<Lootable>(loots);
			i = 0;
			max = lootList.size();
			refreshed = false;

			setBounds(getX(), getY(), lootWidth, lootHeight);
			if (max > 1) {
				createArrowButtons();
			}
			
			currentImage = new Image();
			currentImage.setScaling(Scaling.fit);
			addActor(currentImage);
			
			createNameLabel();
			addSwipeListener();
		}

		/**
		 * Fügt die Swipe-Steuerung hinzu.
		 */
		private void addSwipeListener() {
			addListener(new SwipeListener() {
				@Override
				protected boolean handleSwiped(final SwipeEvent event,
						final Actor actor) {
					swiped(event, actor);
					return true;
				}

				@Override
				public void swiped(final SwipeEvent event, final Actor actor) {
					switch (event.getDirection()) {
					case LEFT:
						nextLoot();
						break;
					case RIGHT:
						prevLoot();
						break;
					default:
						break;
					}

					// Da wir das Popup sind, sollten wir das Event canceln, um
					// ein Weiterreichen an die unterliegendes Stages zu
					// vermeiden
					event.cancel();
				}
			});
		}

		/**
		 * Erstellt das Name-Label.
		 */
		private void createNameLabel() {
			lootName = createLabel("");
			addActor(lootName);
		}

		/**
		 * Erstellt die Pfeil-Buttons zum Wechseln.
		 */
		private void createArrowButtons() {
			ButtonSmall left = new ButtonSmall(ButtonType.LEFT);
			left.addListener(new ChangeListener() {
				@Override
				public void changed(final ChangeEvent event, final Actor actor) {
					prevLoot();
				}
			});
			left.setHeight(getHeight() / 4);
			left.setWidth(getHeight() / 4);

			left.setPosition(0, (getHeight() - left.getHeight()) / 2);
			addActor(left);

			ButtonSmall right = new ButtonSmall(ButtonType.RIGHT);
			right.addListener(new ChangeListener() {
				@Override
				public void changed(final ChangeEvent event, final Actor actor) {
					nextLoot();
				}
			});
			right.setHeight(getHeight() / 4);
			right.setWidth(getHeight() / 4);

			right.setPosition(getWidth() - right.getWidth(),
					(getHeight() - right.getHeight()) / 2);
			addActor(right);
		}

		/**
		 * Wechselt zum nächsten Loot.
		 */
		private void nextLoot() {
			i = (i + 1) % max;
			refreshed = false;
		}

		/**
		 * Wechselt zum vorherigen Loot.
		 */
		private void prevLoot() {
			i = (i == 0) ? max - 1 : i - 1;
			refreshed = false;
		}

		/**
		 * Aktualisiert das Bild und den Namen des Loots.
		 */
		private void refreshLoot() {
			lootName.setText(lootList.get(i).getName());
			lootName.setPosition((getWidth() - lootName.getPrefWidth()) / 2f,
					10);

			Drawable d = lootList.get(i).getImage();
			currentImage.setDrawable(d);

			imageWidth = (d.getMinWidth() > (getWidth() - 200)) ? (getWidth() - 200)
					: d.getMinWidth();
			imageHeight = (d.getMinHeight() > (getHeight() - 60)) ? (getHeight() - 60)
					: d.getMinHeight();

			imageX = (getWidth() - imageWidth) / 2;
			imageY = (getHeight() - 60 - imageHeight) / 2;

			currentImage.setBounds(imageX, imageY, imageWidth, imageHeight);
			
			refreshed = true;
		}

		@Override
		public void draw(final Batch batch, final float parentAlpha) {
			if (!refreshed) {
				refreshLoot();
			}

			super.draw(batch, parentAlpha);
		}
	}
}
