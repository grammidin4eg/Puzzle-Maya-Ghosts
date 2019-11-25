package com.karachevtsev.matrixeditor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MatrixEditor extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRender;
	private EditorConfig editorConfig;
	private GridRender gridRender;
	private ItemsRender itemsRender;
	private LevelManager levelManager;
	private BitmapFont font12;
	private boolean propertyDialogShow = false;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRender = new ShapeRenderer();

		editorConfig = new EditorConfig();
		gridRender = new GridRender(editorConfig);
		itemsRender = new ItemsRender(editorConfig);
		levelManager = new LevelManager(editorConfig);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Arial-Regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 14;
		font12 = generator.generateFont(parameter);
		generator.dispose();

		Gdx.input.setInputProcessor(new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				levelManager.keyUp(keycode);
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				levelManager.keyTyped(character);
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {

				itemsRender.touchDown(screenX, screenY, pointer, button);
				gridRender.touchDown(screenX, screenY, pointer);

				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				gridRender.mouseMoved(screenX, screenY);
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				itemsRender.scrolled(amount);
				return false;
			}

		});
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRender.begin(ShapeRenderer.ShapeType.Line);
		gridRender.renderShapes(shapeRender);
		itemsRender.renderSelected(shapeRender);
		shapeRender.end();

		batch.begin();
		itemsRender.render(batch);
		gridRender.renderSprites(batch);
		batch.end();

		shapeRender.begin(ShapeRenderer.ShapeType.Filled);
		gridRender.renderMark(shapeRender);
		levelManager.renderDialog(shapeRender);
		itemsRender.renderPropDialogShape(shapeRender);
		shapeRender.end();

		batch.begin();
		levelManager.renderDialogBatch(batch, font12);
		itemsRender.renderPropDialog(batch, font12);
		batch.end();
	}
}
