package com.karachevtsevuu.mayaghosts;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public interface IGameAction {
	public void Run(Sprite sprite, TouchEvent pSceneTouchEvent);
	public boolean RunCollision(Sprite self, GButton button);
}
