package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

public class MayaGhostsMain extends ApplicationAdapter {
	SpriteBatch batch;
	private IScene curScene;
	private Vector<IScene> allScenes = new Vector<IScene>();

	@Override
	public void create () {
		batch = new SpriteBatch();
		allScenes.add(new SceneLogo());
		curScene = allScenes.elementAt(0);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		curScene.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for( IScene curScene : allScenes ) {
			curScene.dispose();
		}
	}
}
