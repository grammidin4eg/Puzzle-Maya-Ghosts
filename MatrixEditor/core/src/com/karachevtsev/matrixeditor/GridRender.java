package com.karachevtsev.matrixeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by karachevtsev on 05.03.2016.
 */
public class GridRender {


    private final EditorConfig editorConfig;
    private float rectSize = 1;
    private int screenMargin = 1;
    private Map markColors = new HashMap<String, Color>();

    public GridRender(EditorConfig ec) {
        editorConfig = ec;
        rectSize = Gdx.graphics.getHeight() / ec.getHeight();
        screenMargin = (Gdx.graphics.getWidth() - Gdx.graphics.getHeight())/2;

        markColors.put("red", Color.RED);
        markColors.put("blue", Color.BLUE);
        markColors.put("green", Color.GREEN);
    }

    public void renderShapes(ShapeRenderer shapeRender) {
        shapeRender.setColor(Color.MAROON);
        for(int y =0;y<editorConfig.getHeight();y++) {
            for(int x =0;x<editorConfig.getWidth();x++) {
                shapeRender.rect(screenMargin+x*rectSize, y*rectSize, rectSize, rectSize);
            }
        }

    }

    public void renderMark(ShapeRenderer shapeRender) {
        for(int i=0;i<editorConfig.getLevelItemsCount();i++) {
            LevelItem li = editorConfig.getLevelItems().get(i);
            if( li != null ) {
                String mark = null;
                for (int j = 0; j < li.getPropertiesCount(); j++) {
                    if (li.getProperties().get(j).getMark() != null) {
                        mark = li.getProperties().get(j).getMark();
                        break;
                    }
                }

                if ((mark != null) && (markColors.containsKey(mark))) {
                    shapeRender.setColor((Color) markColors.get(mark));
                    shapeRender.rect(screenMargin + 5 + li.getRow() * rectSize, (li.getCol()+1) * rectSize-10, 5, 5);
                }
            }
        }
    }

    public void renderSprites(SpriteBatch batch) {
        for(int i=0;i<editorConfig.getLevelItemsCount();i++) {
            LevelItem li = editorConfig.getLevelItems().get(i);
            li.draw(batch, li.getRow()*rectSize+screenMargin, li.getCol()*rectSize,rectSize,rectSize);
        }
    }

    public void touchDown(int screenX, int screenY, int pointer) {

    }

    public boolean mouseMoved(int screenX, int screenY) {
        int frow = 0;
        int fcol = 0;

        float rely = Gdx.graphics.getHeight() - screenY;

        frow = Math.floorDiv((screenX - screenMargin), Math.round(rectSize));
        fcol = Math.floorDiv(Math.round(rely), Math.round(rectSize));

        LevelItem curItem = editorConfig.getItemsIn(frow, fcol);
        if(  curItem!= null ) {
            curItem.setAnimated(true);
        }

        return false;
    }
}
