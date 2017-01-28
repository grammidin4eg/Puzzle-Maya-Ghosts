package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by root on 28.01.2017.
 */

public class SceneMain implements IScene {

    private final InputProcessor inputProc;
    private final ResKeeper res;
    private final Sprite topImg;
    private RetCommand retCom;

    public SceneMain(ResKeeper _res) {
        this.res = _res;
        topImg = this.res.getSprite("canvas_up");

        inputProc = new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                //retCom = RetCommand.LOAD_MAIN_SCENE;
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
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };

        breakRetCommand();
    }

    @Override
    public boolean render(SpriteBatch sb) {
        topImg.draw(sb);
        return false;
    }

    @Override
    public void show() {
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(inputProc);

    }

    @Override
    public RetCommand getRetCommand() {
        return retCom;
    }

    @Override
    public void breakRetCommand() {
        retCom = RetCommand.NONE;
    }

    @Override
    public void resize(int width, int height) {
        float newHeight = width*topImg.getHeight()/topImg.getWidth();
        topImg.setSize(width, newHeight);
        topImg.setX(0);
        topImg.setY(height-topImg.getHeight());
    }
}
