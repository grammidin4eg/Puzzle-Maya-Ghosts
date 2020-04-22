package com.karachevtsevuu.mayaghosts;

import java.util.Vector;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.util.modifier.IModifier;

public class MatrixMover {
	private Vector<GBlock> mBlocks;
	private int directionX = 0;
	private int directionY = 0;
	private GSprite movedObj = null;
	private int moveCount = 0;
	
	private static MatrixMover self = null;
	
	public static MatrixMover getSelf() {
		if( self == null ) {
			self = new MatrixMover();
		}
		return self;
	}

	public MatrixMover() {
		self = this;
	}
	public Vector<GBlock> getBlocks() {
		return mBlocks;
	}

	public void setBlocks(Vector<GBlock> mBlocks) {
		this.mBlocks = mBlocks;
	}
	
	public void moveX(int direction, GSprite moved_object) {
		directionX = direction;
		directionY = 0;
		movedObj = moved_object;
		doStep();
	}
	
	public void moveY(int direction, GSprite moved_object) {
		directionY = direction;
		directionX = 0;
		movedObj = moved_object;
		doStep();
	}
	
	private void doStep() {
		if( checkWay() ) {
			moveObject();
		} else {
			movedObj.stopAnimation();
		}
	}
	
	private boolean checkWay() {
		//no solid / solid
		final int next_x = movedObj.getRowX()+directionX;
		final int next_y = movedObj.getRowY()+directionY;
		boolean result = true;
		for( GBlock cur_block : mBlocks ) {
			if( (cur_block.getRow()==next_x) && (cur_block.getCol()==next_y) ) {
				result = !cur_block.runAction();
			}
			if( (cur_block.getRow()==movedObj.getRowX()) && (cur_block.getCol()==movedObj.getRowY()) ) {
				cur_block.runAction();
			}
		}
		if( next_x<0 || next_y<0 || next_y>9 || next_x>9 ) {
			result = false;
		}
		return result;
	}
	
	private void moveObject() {
		movedObj.setRowX(movedObj.getRowX()+directionX);
		movedObj.setRowY(movedObj.getRowY()+directionY);
		IEntityModifierListener end_action = new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				MatrixMover.getSelf().addMoveCount();
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				MatrixMover.getSelf().decMoveCount();
			}
		};
		movedObj.registerEntityModifier(new SequenceEntityModifier( new ParallelEntityModifier(
					new MoveXModifier(0.3f, movedObj.getX(), movedObj.getX()+(directionX*MainActivity.screenGrid), end_action ),
					new MoveYModifier(0.3f, movedObj.getY(), movedObj.getY()+(directionY*MainActivity.screenGrid), end_action )				
				) ) );
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void addMoveCount() {
		this.moveCount++;
	}
	public void decMoveCount() {
		this.moveCount--;
		if( this.moveCount==0 ) {
			this.doStep();
		}
	}
}
