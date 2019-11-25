package com.karachevtsev.matrixeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Vector;

/**
 * Created by karachevtsev on 05.03.2016.
 */
public class LevelItem {
    private int row;
    private int col;
    private String type;
    private GridItem gridItem;
    private Vector<ItemProperty> properties;
    private int currentFrame = 0;
    private float animationElapsed = 0;
    private boolean isAnimated = false;

    public LevelItem(int row, int col, String type, GridItem gi, Vector<ItemProperty> defaultProps) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.gridItem = gi;
        properties = defaultProps;
    }

    public Vector<ItemProperty> getProperties() {
        return properties;
    }

    public void addProperty(String name, String value) {
        //поищем, может, такое уже есть, тогда установим значение
        //если нет, то создадим новую запись
        for( ItemProperty ip : properties ) {
            if(ip.getName().equals(name) ) {
                ip.setValue(value);
                return;
            }
        }

        ItemProperty newProp = new ItemProperty(name);
        newProp.addValue(value, null);
        properties.add(newProp);
    }

    public int getPropertiesCount() {
        return properties.size();
    }

    public void splitParamValue(int param) {
        properties.get(param).splitParamValue();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAnimated() { return this.isAnimated; }

    public void setAnimated(boolean animated) { this.isAnimated = animated; }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        float rotation = 0;
        for ( ItemProperty ip : properties ) {
            if( ip.getName().equals("direction") ) {
                if( ip.getValue().equals("Left") ) {
                    rotation = 180;
                } else if( ip.getValue().equals("Bottom") ) {
                    rotation = 270;
                } else if( ip.getValue().equals("Top") ) {
                    rotation = 90;
                }
                break;
            }
        }
        if( isAnimated && gridItem.isAnimated() ) {
            float dt = Gdx.graphics.getDeltaTime();
            animationElapsed += dt;
            int currentFrameCut = gridItem.getCurrentFrame(currentFrame, animationElapsed);
            if (currentFrameCut != currentFrame) {
                currentFrame = currentFrameCut;
                animationElapsed -= gridItem.getFrameLengtn();
            }
        }
        gridItem.draw(batch, x, y, width, height, rotation, currentFrame);
    }
}
