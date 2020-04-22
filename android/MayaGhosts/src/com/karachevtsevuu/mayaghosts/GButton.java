package com.karachevtsevuu.mayaghosts;

import org.andengine.entity.sprite.Sprite;

public class GButton {
	private Sprite mainSprite;
	private Sprite secondSprite;
	private boolean mFlag = false;
	
	public GButton(Sprite main_sprite, Sprite second_sprite) {
		mainSprite = main_sprite;
		secondSprite = second_sprite;
		secondSprite.setVisible(false);
	}
	
	public void setSecondSprite() {
		mainSprite.setVisible(false);
		secondSprite.setVisible(true);
	}
	
	public void setMainSprite() {
		mainSprite.setVisible(true);
		secondSprite.setVisible(false);
	}
	
	public void swapMainSecond() {
		mainSprite.setVisible(!mainSprite.isVisible());
		secondSprite.setVisible(!secondSprite.isVisible());
	}

	public boolean isFlag() {
		return mFlag;
	}

	public void setFlag(boolean mFlag) {
		this.mFlag = mFlag;
	}
	
	public void Hide() {
		mainSprite.setVisible(false);
		secondSprite.setVisible(false);
	}
}
