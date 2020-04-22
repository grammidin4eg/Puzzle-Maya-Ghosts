package com.karachevtsevuu.mayaghosts;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackerTextureRegion;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import android.util.Log;

public class TitleRoom extends Room {
	public Rectangle rect;
	public static TitleRoom titleRoom;

	public TitleRoom() {
		super();
		titleRoom = this;
		setBackgroundEnabled(false);
		rect = new Rectangle(10, 10, 100, 100, MainActivity.mainActivity.getVertexBufferObjectManager()) 
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				this.setColor(Color.RED);
				MainActivity.roomMaster.ShowRoom(RoomMaster.ROOM_MAIN);
				return true;
			}
		};
		rect.setColor(Color.BLUE);
		rect.setVisible(false);
		attachChild(rect);
		
		final float main_logo_width = 449*MainActivity.screenWidth/600;
		final float main_logo_height = 449/391*main_logo_width;
		//TODO: определять нужно так - пропорция по минимальной стороне. и потом пропорция для другого размера (чтобы соблюсти пропорции картинки)
		//ширина = 
		final Sprite sMainLogo = MainActivity.mainActivity.sLogo.genSprite(MainActivity.screenWidth/2-main_logo_width/2, MainActivity.screenHeight/2-main_logo_height/2-MainActivity.screenGrid/2, main_logo_width, main_logo_height);
		attachChild(sMainLogo);
	}
	
	@Override
	public void onShow() {
		Thread background = new Thread(new Runnable() {
			public void run() {
				Log.d("GameDebug", "load res start");
				MainActivity.mainActivity.loadGameRes();
				MainActivity.roomMaster.initRooms();
				Log.d("GameDebug", "load res done");
				//анимация
				//появление кнопок
				TitleRoom.titleRoom.rect.setVisible(true);
				TitleRoom.titleRoom.registerTouchArea(rect);
				TexturePackerTextureRegion up_panel_area = MainActivity.mainActivity.tp_titlescene_lib.get(tp_title.UP_PANEL_ID);
				final float up_panel_height = up_panel_area.getHeight()*MainActivity.screenWidth/up_panel_area.getWidth();
				final Sprite up_sprite = MainActivity.genSprite(MainActivity.mainActivity.tp_titlescene_lib, tp_title.UP_PANEL_ID, MainActivity.screenWidth, up_panel_height, 0, -up_panel_height, null);
				final Sprite down_sprite = MainActivity.genSprite(MainActivity.mainActivity.tp_titlescene_lib, tp_title.DOWN_PANEL_ID, MainActivity.screenWidth, up_panel_height, 0, MainActivity.screenHeight, null);
				final float corr_height = MainActivity.screenGrid/2;
				
				TitleRoom.titleRoom.attachChild(up_sprite);
				TitleRoom.titleRoom.attachChild(down_sprite);
				
				up_sprite.registerEntityModifier(
						new SequenceEntityModifier(
									new MoveModifier(3, up_sprite.getX(), up_sprite.getX(), up_sprite.getY(),3*corr_height)
								)
						);
				
				down_sprite.registerEntityModifier(
						new SequenceEntityModifier(
									new MoveModifier(3, up_sprite.getX(), up_sprite.getX(), down_sprite.getY(),MainActivity.screenHeight/2+corr_height)
								)
						);
			}
		});
		background.start();

	}
	@Override
	public void onHide() {
		if( (MainActivity.mainActivity.mTitleMusic!=null)  && (MainActivity.mainActivity.mTitleMusic.isPlaying()) ) {
			MainActivity.mainActivity.mTitleMusic.stop();
		}
	}
}

