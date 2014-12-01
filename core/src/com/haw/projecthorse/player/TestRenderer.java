package com.haw.projecthorse.player;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.haw.projecthorse.assetmanager.AssetManager;
import com.haw.projecthorse.level.Level;
import com.haw.projecthorse.level.util.background.EndlessBackground;

public class TestRenderer extends Level {
//	Pixmap pixmap;
//	Texture texture;
	private Stage stage;
	private EndlessBackground bg;
	private float lastPos = 0;

	@Override
	protected void doRender(float delta) {
//		SpriteBatch b = getSpriteBatch();
//
//		b.begin();
//		b.setColor(Color.PINK);
//		b.draw(texture, 100, 100);
//		b.end();
		stage.act(delta);
		stage.draw();
	}

	@Override
	protected void doDispose() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doResize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doShow() {
//		TextureRegion horse = AssetManager.getTextureRegion("notChecked",
//				"white_sprites"), circle = AssetManager.getTextureRegion(
//				"notChecked", "kreis");
//		int horseWidth = horse.getRegionWidth(), horseHeight = horse
//				.getRegionHeight(), diameter = Math.max(
//				circle.getRegionHeight(), circle.getRegionWidth()), distance = diameter * 2;
//		TextureData td = horse.getTexture().getTextureData();
//		td.prepare();
//		Pixmap atlasPixmap = td.consumePixmap();
//
//		pixmap = new Pixmap(horseWidth, horseHeight, atlasPixmap.getFormat());
//		pixmap.drawPixmap(atlasPixmap, 0, 0, horse.getRegionX(),
//				horse.getRegionY(), horseWidth, horseHeight);
//
//		Pixmap black = new Pixmap(3, 1, atlasPixmap.getFormat());
//		black.setColor(Color.BLACK);
//		black.drawRectangle(0, 0, 3, 1);
		// black.drawPixel(0, 0, Color.rgba8888(Color.BLACK));
		// overlayPixmap(black, 0, 0,
		// 3, 1, 5, 0);

//		texture = new Texture(pixmap, false);
		
		stage = new Stage(getViewport(), getSpriteBatch());
		bg = new EndlessBackground(width, AssetManager.getTextureRegion("notChecked", "testbg"), 1);
		stage.addActor(bg);
	}

	/*private void overlayPixmap(Pixmap overlay, int regX, int regY,
			int regWidth, int regHeight, int distanceX, int distanceY) {
		Color c;
		float gRed, gGreen, gBlue, gAlpha, oRed, oGreen, oBlue, oAlpha;
		for (int posX = 0; (posX + regWidth) < pixmap.getWidth(); posX += regWidth
				+ distanceX) {
			for (int posY = 0; (posY + regHeight) < pixmap.getHeight(); posY += regHeight
					+ distanceY) {
				for (int x = 0; x < regWidth; x++) {
					for (int y = 0; y < regHeight; y++) {
						c = new Color(pixmap.getPixel(posX + x, posY + y));
						gAlpha = c.a;

						if (gAlpha > 0) {
							gRed = c.r;
							gGreen = c.g;
							gBlue = c.b;

							c = new Color(overlay.getPixel(regX + x, regY + y));
							oRed = c.r;
							oGreen = c.g;
							oBlue = c.b;
							oAlpha = c.a;

							c = new Color((gRed * gAlpha + oRed * oAlpha)
									/ (gAlpha + oAlpha),
									(gGreen * gAlpha + oGreen * oAlpha)
											/ (gAlpha + oAlpha), (gBlue
											* gAlpha + oBlue * oAlpha)
											/ (gAlpha + oAlpha), 1);
							pixmap.drawPixel(posX + x, posY + y,
									Color.rgba8888(c));
						}
					}
				}
			}
		}
	}*/

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
