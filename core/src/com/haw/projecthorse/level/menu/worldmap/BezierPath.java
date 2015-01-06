package com.haw.projecthorse.level.menu.worldmap;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class BezierPath extends Widget {

	private Drawable drawable;
	private Bezier<Vector2> bezier;
	private ArrayList<Vector2> poins = new ArrayList<Vector2>();
	private Vector2 source;
	private Vector2 target;

	public BezierPath(Drawable drawable, Vector2 source, Vector2 target) {
		this.drawable = drawable;
		this.source = source;
		this.target = target;
		Vector2 zwischenPunkt1 = new Vector2(source.x + ((target.x - source.x) * 0.50f), source.y
				+ ((target.y - source.y) * 0.50f));
		Vector2 zwischenPunkt2;
		if (Math.abs(target.x - source.x) > Math.abs(target.y - source.y)) {
			zwischenPunkt2 = new Vector2(source.x + ((target.x - source.x) * 0.75f), source.y);
		} else {
			zwischenPunkt2 = new Vector2(source.x, source.y + ((target.y - source.y) * 0.75f));
		}

		Vector2[] tempPoins = new Vector2[] { source, zwischenPunkt1, zwischenPunkt2, target };

		bezier = new Bezier<Vector2>(tempPoins, 0, 4);

		for (float i = 0f; i <= 1; i = i + 0.05f) {
			Vector2 out = new Vector2();
			bezier.valueAt(out, i);
			poins.add(out);
		}
	}

	public void draw(Batch batch, float parentAlpha) {
		validate();

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		for (Vector2 point : poins) {
			drawable.draw(batch, point.x, point.y, 10, 10);
		}
	}

	public Vector2 getSource() {
		return source;
	}

	public Vector2 getTarget() {
		return target;
	}

	public ArrayList<Vector2> getPoins() {
		return poins;
	}

	public void setDrawable(Drawable drawable) {
		if (this.drawable == drawable)
			return;
		if (drawable != null) {
			if (getPrefWidth() != drawable.getMinWidth() || getPrefHeight() != drawable.getMinHeight())
				invalidateHierarchy();
		} else
			invalidateHierarchy();
		this.drawable = drawable;
	}

	public Drawable getDrawable() {
		return drawable;
	}

}
