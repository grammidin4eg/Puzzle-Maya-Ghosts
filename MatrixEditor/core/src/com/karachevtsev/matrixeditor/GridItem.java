package com.karachevtsev.matrixeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.io.Console;

/**
 * Created by karachevtsev on 05.03.2016.
 */
public class GridItem {
    private String id;
    private String name;
    private String file;
    Texture img;
    Array<Sprite> sprites;
    boolean isSprite = false;
    boolean isAnimated = false;
    private float frameLength = 0.05f;

    public GridItem(String _id, String _name, String _file) {
        id = _id;
        name = _name;
        file = _file;
        img = new Texture(file);
    }

    public GridItem(String _id, String _name, Array<Sprite> _sprite) {
        id = _id;
        name = _name;
        sprites = _sprite;
        isSprite = true;
        if( sprites.size > 1 ) {
            isAnimated = true;
        }
    }

    public Texture getTexture() {
        return img;
    }

    public boolean isSprite() { return isSprite; }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getFrameLengtn() { return this.frameLength; }

    public boolean isAnimated() { return (isAnimated && isSprite && sprites.size>0); }

    public int getCurrentFrame(int currentFrame, float animationElapsed) {
        if( sprites.size > 0 ) {
            int resCurrentFrame = currentFrame;
            if (isAnimated && (sprites.size > 1) ) {
                if (animationElapsed > frameLength) {
                    resCurrentFrame = (currentFrame == sprites.size - 1) ? 0 : ++currentFrame;
                    //System.out.println( "currentFrame: " + String.valueOf(currentFrame) );
                }
            }
            return resCurrentFrame;
        } else {
            return 0;
        }
    }

    public Sprite getCurSprite(int currentFrame) {
        if( sprites.size > 0 ) {
            return sprites.get( currentFrame );
        } else {
            return null;
        }
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height, float rotate, int currentFrame) {
        if( isSprite ) {
            Sprite sprite = getCurSprite(currentFrame);
            if( sprite != null) {
                sprite.setX(x);
                sprite.setY(y);
                sprite.setSize(width, height);
                sprite.setOriginCenter();
                sprite.setRotation(rotate);
                sprite.draw(batch);
            }
        } else {
            batch.draw(img, x, y, width, height);
        }
    }
}

