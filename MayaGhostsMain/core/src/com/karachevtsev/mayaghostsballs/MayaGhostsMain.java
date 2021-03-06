package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.HashMap;
import java.util.Vector;

public class MayaGhostsMain extends ApplicationAdapter {
	SpriteBatch batch;
	private IScene curScene;
	private HashMap<String, IScene> allScenes = new HashMap<String, IScene>();
	private ResKeeper resKeeper;
	private OrthographicCamera camera;
	private ExtendViewport viewport;

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height, true);
		batch.setProjectionMatrix(camera.combined);

		curScene.resize(width, height);
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		resKeeper = new ResKeeper();

		allScenes.put("Logo", new SceneLogo(resKeeper));
		allScenes.put("Main", new SceneMain(resKeeper));

		gotoScene("Logo");

		camera = new OrthographicCamera();
		viewport = new ExtendViewport(480, 800, camera);
		batch.setProjectionMatrix(camera.combined);
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
