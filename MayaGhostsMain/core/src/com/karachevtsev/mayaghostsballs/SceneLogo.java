package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by root on 27.01.2017.
 */

public class SceneLogo implements IScene {
    private final Sprite logo;
    private final InputProcessor inputProc;
    private RetCommand retCom;
    private ResKeeper res;

    public SceneLogo(ResKeeper _res) {
        this.res = _res;
        logo = this.res.getSprite("btn_play");

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
                retCom = RetCommand.LOAD_MAIN_SCENE;
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
        logo.draw(sb);
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
        logo.setOriginCenter();
        logo.setX(width/2-logo.getWidth()/2);
        logo.setY(height/2-logo.getHeight()/2);
    }
}
