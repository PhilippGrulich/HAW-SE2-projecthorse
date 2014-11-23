package com.haw.projecthorse.level.game.farm;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.geofire.GeoFire;

public class FireBaseManager {
	private Firebase postRef;

	public FireBaseManager(final PlayGround p) {
		Firebase f = new Firebase("https://projecthorse.firebaseio.com/");
		postRef = f.child("farmUser");	

		postRef.addChildEventListener(new ChildEventListener() {
			
			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
			}
			
			@Override
			public void onChildAdded(DataSnapshot arg0, String arg1) {
				Gdx.app.log("Firebase", "Add");
				final UserModel userModel = new UserModel();
				final Map<String, String> newPost = (Map<String, String>)arg0.getValue();
				userModel.id = (String) newPost.get("id");
				userModel.name = (String) newPost.get("name");
				userModel.x = (String) newPost.get("x");
				userModel.y = (String) newPost.get("y");
				System.out.println("add "+ userModel.x + " " + userModel.y);
				postRef.child(userModel.id).addValueEventListener(new ValueEventListener() {
				    @Override
				    public void onDataChange(DataSnapshot snapshot) {
				    	Gdx.app.log("Firebase", "Update");
				        if(snapshot.getValue()!=null){
				        	
							final Map<String, String> newPost = (Map<String, String>)snapshot.getValue();
							userModel.id = (String) newPost.get("id");
							userModel.name = (String) newPost.get("name");
							userModel.x = (String) newPost.get("x");
							userModel.y = (String) newPost.get("y");
					        Gdx.app.postRunnable(new Runnable() {
								@Override
								public void run() {
								
									p.addUser(userModel);
								}
							});				        	
				        }else{
				        	p.removeUser(userModel);
				        }
				    	
				    }
				    @Override
				    public void onCancelled(FirebaseError firebaseError) {
				        System.out.println("The read failed: " + firebaseError.getMessage());
				    }
				});
				
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
					
						p.addUser(userModel);
					}
				});
				
			}
			
			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
	
	public void addUser(UserModel u){
		postRef.child(u.id).setValue(u);
	}



	public void removeUser(UserModel rootUser) {
		postRef.child(rootUser.id).removeValue();
		
	}



}
