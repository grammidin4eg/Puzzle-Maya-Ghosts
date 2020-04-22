package com.karachevtsevuu.mayaghosts;

import java.util.HashMap;
import java.util.Map;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class SpriteObject {
	
	private BitmapTextureAtlas mTexture = null;
	private ITextureRegion mRegion = null;
	private TiledTextureRegion mTiledRegion = null;
	private Sprite mSprite = null;
	private float mWidth = 0;
	private float mHeight = 0;
	private Map<Integer, IGameAction> mActions = null;
	
	public SpriteObject(final int width, final int height, final String name, final boolean tiled, final int tilecolumns, final int tilerows) {
		mTexture = new BitmapTextureAtlas(MainActivity.mainActivity.getTextureManager(), width, height, TextureOptions.BILINEAR);
		if( !tiled )
			mRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mTexture, MainActivity.mainActivity, name, 0, 0);
		else
			mTiledRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTexture, MainActivity.mainActivity, name, 0, 0, tilecolumns, tilecolumns);
		
		mTexture.load();
		mActions = new HashMap<Integer, IGameAction>();
	}
	
	public void initTouchAction(IGameAction action) {
		initTouchAction(0, action);
	}
	
	public void initTouchAction(int sprite_id, IGameAction action) {
		mActions.put(Integer.valueOf(sprite_id) , action);
	}
	
	public Sprite genSprite(float x, float y, float width, float height) {
		mSprite = new Sprite(x, y, width, height, mRegion,
				MainActivity.mainActivity.getVertexBufferObjectManager()) {
						
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (!mActions.isEmpty()) {
					IGameAction action = mActions.get(Integer.valueOf(0));	
					if(action!=null)
						action.Run(this, pSceneTouchEvent);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}

		};
		setWidth(width);
		mHeight = height;
		return mSprite;
	}
	
	public GSprite genGSprite(float x, float y, float width, float height) {
		GSprite gsprite = new GSprite(x, y, mTiledRegion,
				MainActivity.mainActivity.getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (!mActions.isEmpty()) {
					IGameAction action = mActions.get(Integer.valueOf(this.getSpriteId()));
					if(action!=null)
						action.Run(this, pSceneTouchEvent);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX,
						pTouchAreaLocalY);
			}

		};
		gsprite.setSize(width, height);
		setWidth(width);
		mHeight = height;
		return gsprite;
	}
	
	public void setMod() {
		SequenceEntityModifier mod = new SequenceEntityModifier(
				new ParallelEntityModifier(
						new ScaleModifier(3, 0.5f, 5),
						new RotationByModifier(3, 90)));
		mSprite.registerEntityModifier(mod);
	}
	
	public ITextureRegion getRegion() {
		return mRegion;
	}

	public float getWidth() {
		return mWidth;
	}

	public void setWidth(float mWidth) {
		this.mWidth = mWidth;
	}

}
