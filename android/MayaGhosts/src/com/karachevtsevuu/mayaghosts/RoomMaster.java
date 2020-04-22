package com.karachevtsevuu.mayaghosts;
import java.util.Vector;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;
import android.view.KeyEvent;

public class RoomMaster extends Scene {

	public static final int ROOM_TITLE = 0;
	public static final int ROOM_MAIN = 1;
	
	public static Vector<Room> roomList = new Vector<Room>();
	public static int room_id = 0;
	
	public RoomMaster() {
		
		roomList.add(new TitleRoom());
		attachChild(roomList.get(room_id));
		roomList.get(room_id).Show();
	}
	public void initRooms() {
		roomList.add(new GameRoom());
		
		/*for( Room cur_scene : roomList ) {
			this.attachChild(cur_scene);
		}*/		
	}
	public Room getRoom(int id) {
		return roomList.get(id);
	}
	
	public void ShowRoom(int id) {
		room_id = id;
		for( Room cur_scene : roomList ) {
			cur_scene.Hide();
		}
		final Room cur_room = roomList.get(room_id);
		if( !cur_room.isChildsAttached() ) {
			this.attachChild(cur_room);
			cur_room.setChildsAttached(true);
		}
		cur_room.Show();
	}
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		roomList.get(room_id).onSceneTouchEvent(pSceneTouchEvent);
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	public void onKeyBack(int keyCode, KeyEvent event) {
		//если в главном окне - выходим
		if( room_id == 0 ) {
			MainActivity.mainActivity.onDestroy();
		} else {				
			//если комнате - обратно (или меню)
			ShowRoom(0);
		}
	}
	public void onKeyMenu(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		roomList.get(room_id).onKeyMenu(keyCode, event);
	}		
}
