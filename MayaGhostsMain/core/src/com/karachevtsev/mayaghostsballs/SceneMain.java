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
    private final Sprite bottomImg;
    private final Sprite topBorder;
    private final Sprite bottomBorder;
    private final Grid mainGrid ;
    private RetCommand retCom;

    public SceneMain(ResKeeper _res) {
        this.res = _res;
        topImg = this.res.getSprite("canvas_up");
        bottomImg = res.getSprite("canvas_down");
        topBorder = res.getSprite("canvas_border_up");
        bottomBorder = res.getSprite("canvas_border_down");

        mainGrid = new Grid(res, 10, Gdx.graphics.getWidth()/10);

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
        topBorder.draw(sb);
        bottomImg.draw(sb);
        bottomBorder.draw(sb);
        mainGrid.render(sb);
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
        float newHeight = getHeightForWidthWide(topImg, width);
        topImg.setSize(width, newHeight);
        topImg.setY(height-topImg.getHeight());

        bottomImg.setSize(width, getHeightForWidthWide(bottomImg, width));

        topBorder.setSize(width, getHeightForWidthWide(topBorder, width));
        topBorder.setY(height-topImg.getHeight()-topBorder.getHeight());

        bottomBorder.setSize(width, getHeightForWidthWide(bottomBorder, width));
        bottomBorder.setY(bottomImg.getHeight());

        mainGrid.setCellSize(width/10);
        mainGrid.setMarginY(bottomImg.getHeight()+bottomBorder.getHeight());
    }

    private float getHeightForWidthWide(Sprite spr, float screenWidth) {
        return screenWidth*spr.getHeight()/spr.getWidth();
    }
}
