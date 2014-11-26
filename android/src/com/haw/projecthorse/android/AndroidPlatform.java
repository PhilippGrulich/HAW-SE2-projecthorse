package com.haw.projecthorse.android;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Orientation;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.haw.projecthorse.platform.Platform;

public class AndroidPlatform extends AndroidApplication implements Platform {
	
	private Orientation active = Orientation.Portrait;
	
	private Activity activity;

	public AndroidPlatform(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void SetOrientation(Orientation orientation) {
		active = orientation;
		if (orientation == Orientation.Landscape) {
			DisplayMode[] DisplayModes = Gdx.graphics.getDisplayModes();
			Gdx.graphics.setDisplayMode(DisplayModes[0].height, DisplayModes[0].width, false);
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

	}

	@Override
	public Orientation getOrientation() {
		return active;
	}
	
}
