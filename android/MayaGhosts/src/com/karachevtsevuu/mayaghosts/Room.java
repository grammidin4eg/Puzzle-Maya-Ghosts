package com.karachevtsevuu.mayaghosts;

import org.andengine.entity.scene.CameraScene;

import android.view.KeyEvent;

public class Room extends CameraScene {
	
	private boolean childsAttached = false;
	
	public Room() {
		super(MainActivity.mainCamera);
	}
	
	public void Show() {
		setVisible(true);
		setIgnoreUpdate(false);
		onShow();
	}
		
	public void Hide() {
		setVisible(false);
		setIgnoreUpdate(true);
		onHide();
	}
	
	public void onShow() {
		
	}
	
	public void onHide() {
		
	}

	public void onKeyMenu(int keyCode, KeyEvent event) {
	}

	public boolean isChildsAttached() {
		return childsAttached;
	}

	public void setChildsAttached(boolean childsAttached) {
		this.childsAttached = childsAttached;
	}
}
