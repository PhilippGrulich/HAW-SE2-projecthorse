package com.haw.projecthorse.android;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.haw.projecthorse.gamemanager.CoreGameMain;


public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //Disable standbye mode

		AndroidPlatform platform = new AndroidPlatform(this);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CoreGameMain(platform), config);
	}
	
	@Override
	protected void onResume() {
		// Hiermit wird die Android Navigation Bar ausgeblendet, zur�ckholen
		// geht ab Android 4.4 mit einem Swipe vom oberen Bildschirmrand nach unten
		if (Build.VERSION.SDK_INT >= 19) {
			Window window = getWindow();
			if(window!=null){
			try {
				View decorView = window.getDecorView();
				decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
			} catch (Exception e) {
				// No navbar on device
			}
			
			}
		}

		super.onResume();
	}
}
