package com.haw.projecthorse.level.game.farm;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.haw.projecthorse.intputmanager.InputManager;
import com.haw.projecthorse.level.util.swipehandler.StageGestureDetector;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener;
import com.haw.projecthorse.level.util.swipehandler.SwipeListener.SwipeEvent;

public class PlayGround extends Stage {
	
	Map<UserModel,User> userSet = new HashMap<UserModel,User>();
	
	PlayGround(Viewport viewport, Batch batch){
		super( viewport,  batch);
		InputManager.addInputProcessor(this);
	}
	
	private void addBackground(){
		
	}
	
	void addUser(UserModel u){
		if(userSet.containsKey(u)){
			userSet.get(u).update(u);
		}else{
			User user = new User(u);
			this.addActor(user);
			userSet.put(u, user);
		}		
		
		
	}
	void addRootUser(final UserModel u, final FireBaseManager f){
	
			User user = new User(u);
			this.addActor(user);
			userSet.put(u, user);
			this.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					
					u.x  =  String.valueOf(x);
					u.y = String.valueOf(y);
					updateUser(u);
					f.addUser(u);
					return true;
				}
			});
			
			
//			InputManager.addInputProcessor(new StageGestureDetector(this, true));
//			user.addListener(new SwipeListener() {
//				
//				
//						@Override
//						public void swiped(SwipeEvent event, Actor actor) {
//							System.out.println("Event");
//							switch (event.getDirection()) {
//							case DOWN:
//								u.y  = String.valueOf(Integer.parseInt(u.y)-50);
//								updateUser(u);
//								f.addUser(u);
//								return;
//							case UP:
//								u.y  = String.valueOf(Integer.parseInt(u.y)+50);
//								updateUser(u);
//								f.addUser(u);
//								return;
//							case LEFT:
//								u.x  = String.valueOf(Integer.parseInt(u.x)-50);
//								updateUser(u);
//								f.addUser(u);
//								return;
//								
//							case RIGHT:
//								u.x  = String.valueOf(Integer.parseInt(u.x)+50);
//								updateUser(u);
//								f.addUser(u);
//								return;
//							default:
//								return;
//							}
//						}
//						
//					});
//			
		
		
		
	}
	 void updateUser(UserModel u){
		 if(userSet.containsKey(u))
			userSet.get(u).update(u);
	}

	public void removeUser(UserModel u) {
		if(userSet.containsKey(u)){
			User user = userSet.get(u);
			user.remove();
			userSet.remove(u);
			
		}
		
	}
}
