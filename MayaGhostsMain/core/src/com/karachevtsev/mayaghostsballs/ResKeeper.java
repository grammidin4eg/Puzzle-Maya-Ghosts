package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

/**
 * Created by root on 27.01.2017.
 */

public class ResKeeper {
    private final TextureAtlas atlas;
    private final TextureAtlas ball;
    private final TextureAtlas spirit;
    private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    public ResKeeper() {
        atlas = new TextureAtlas("maya_ghosts.txt");
        ball = new TextureAtlas("ball.txt");
        spirit = new TextureAtlas("spirit.txt");
    }

    public Sprite getSprite(String name) {
        if( !sprites.containsKey(name) ) {
            Sprite newSprite = atlas.createSprite(name);
            sprites.put(name, newSprite);
        }
        return sprites.get(name);
    }

    public void dispose() {
        atlas.dispose();
        ball.dispose();
        spirit.dispose();
    }
}
