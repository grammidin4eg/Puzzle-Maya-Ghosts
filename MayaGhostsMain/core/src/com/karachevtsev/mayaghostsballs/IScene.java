package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by root on 27.01.2017.
 */

public interface IScene {
    public boolean render(SpriteBatch sb);
    public void show();
    public void create();
    public void dispose();

}
