package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by root on 27.01.2017.
 */

public class SceneLogo implements IScene {
    private Texture logo;

    public SceneLogo() {
        logo = new Texture("badlogic.jpg");
    }

    @Override
    public boolean render(SpriteBatch sb) {
        sb.draw(logo, 0, 0);
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void create() {
    }

    @Override
    public void dispose() {
        logo.dispose();
    }
}
