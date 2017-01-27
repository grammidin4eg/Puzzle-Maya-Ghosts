package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by root on 27.01.2017.
 */

public class SceneLogo implements IScene {
    private final Sprite logo;
    private ResKeeper res;

    public SceneLogo(ResKeeper _res) {
        this.res = _res;
        logo = this.res.getSprite("btn_play");
    }

    @Override
    public boolean render(SpriteBatch sb) {
        logo.draw(sb);
        return false;
    }

    @Override
    public void show() {
        logo.setOriginCenter();
        logo.setX(Gdx.graphics.getWidth()/2-logo.getWidth()/2);
        logo.setY(Gdx.graphics.getHeight()/2-logo.getHeight()/2);
    }
}
