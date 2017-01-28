package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Vector;

public class MayaGhostsMain extends ApplicationAdapter {
	SpriteBatch batch;
	private IScene curScene;
	private HashMap<String, IScene> allScenes = new HashMap<String, IScene>();
	private ResKeeper resKeeper;

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		curScene.resize(width, height);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		resKeeper = new ResKeeper();
		allScenes.put("Logo", new SceneLogo(resKeeper));
		allScenes.put("Main", new SceneMain(resKeeper));

		gotoScene("Logo");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		curScene.render(batch);
		batch.end();

		RetCommand ret = curScene.getRetCommand();
		switch (ret) {
			case LOAD_MAIN_SCENE:
				gotoScene("Main");
				break;
		}
		curScene.breakRetCommand();
	}

	private void gotoScene(String name) {
		curScene = allScenes.get(name);
		curScene.show();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		resKeeper.dispose();
	}

}
