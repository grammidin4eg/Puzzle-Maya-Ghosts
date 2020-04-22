package com.karachevtsevuu.mayaghosts;

import org.andengine.entity.sprite.Sprite;

public class GBlock {
	private Sprite mSprite = null;
	private IGameAction mAction = null;
	private int mRow;
	private int mCol;
	private GButton mButton=null;
	private boolean movedBlock=false;
	private boolean bombBlock=false;
	
	public GBlock(int row, int col) {
		mRow = row;
		mCol = col;
	}
	
	public int getRow() {
		if( movedBlock ) {
			GSprite sp = (GSprite) mSprite;
			return sp.getRowX();
		}
		return mRow;
	}
	
	public int getCol() {
		if( movedBlock ) {
			GSprite sp = (GSprite) mSprite;
			return sp.getRowY();
		}
		return mCol;
	}
	
	public Sprite getSprite() {
		return mSprite;
	}
	public void setSprite(Sprite mSprite) {
		this.mSprite = mSprite;
	}
	public IGameAction getAction() {
		return mAction;
	}
	public boolean runAction() {
		if( mAction == null ) {
			return true;
		}
		return mAction.RunCollision(mSprite, mButton);
	}
	public void setAction(IGameAction mAction) {
		this.mAction = mAction;
	}
	public void remove() {
		mRow = -100;
		mCol = -100;
		if( movedBlock ) {
			GSprite sp = (GSprite) mSprite;
			sp.setRowX(-100);
			sp.setRowY(-100);
		}
		if( mButton!=null ) {
			mButton.Hide();
		}
		MainActivity.mainActivity.runOnUiThread(new Runnable() {							
			@Override
			public void run() {
				mSprite.setX(-100);
				mSprite.setVisible(false);								
			}
		});
	}

	public GButton getButton() {
		return mButton;
	}

	public void setButton(GButton mButton) {
		this.mButton = mButton;
	}

	public boolean isMovedBlock() {
		return movedBlock;
	}

	public void setMovedBlock(boolean movedBlock) {
		this.movedBlock = movedBlock;
	}

	public boolean isBombBlock() {
		return bombBlock;
	}

	public void setBombBlock(boolean bombBlock) {
		this.bombBlock = bombBlock;
	}
}
