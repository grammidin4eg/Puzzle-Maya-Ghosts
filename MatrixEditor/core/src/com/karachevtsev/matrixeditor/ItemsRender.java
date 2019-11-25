package com.karachevtsev.matrixeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.Input.*;

/**
 * Created by karachevtsev on 05.03.2016.
 */
public class ItemsRender {
    private final EditorConfig editorConfig;
    private final float rectSize;
    private final int screenMargin;
    private final int itemMargin;
    private int curSelected = 0;

    private boolean propertyDialogShow = false;
    private float dialogWidth;
    private float dialogHeight;
    private LevelItem curPropItem = null;
    private int propertyDialogMarker = 0;

    public ItemsRender(EditorConfig ec) {
        editorConfig = ec;
        rectSize = Gdx.graphics.getHeight() / ec.getHeight();
        screenMargin = (Gdx.graphics.getWidth() - Gdx.graphics.getHeight())/2;
        itemMargin = 4;

        dialogWidth = Gdx.graphics.getWidth()/2;
        dialogHeight = Gdx.graphics.getHeight()/2;
    }

    public void render(SpriteBatch batch) {
        for(int i=0;i<editorConfig.getItemsListCount();i++) {
            editorConfig.getItemList().get(i).draw(batch, itemMargin/2, i * rectSize + itemMargin/2, rectSize-itemMargin, rectSize-itemMargin, 0, 0);
        }
    }

    public void renderSelected(ShapeRenderer renderer) {
        renderer.setColor(Color.GREEN);
        renderer.rect(1, curSelected*rectSize+1, rectSize, rectSize);
    }

    public void renderPropDialog(SpriteBatch batch, BitmapFont font) {
        if(propertyDialogShow && curPropItem!=null ) {
            for(int i=0;i<curPropItem.getPropertiesCount();i++) {
                ItemProperty dprop = curPropItem.getProperties().get(i);
                font.draw(batch, dprop.toString(), Gdx.graphics.getWidth()/2-dialogWidth/2+20, Gdx.graphics.getHeight()/2+dialogHeight/2-(i+1)*20);
            }
        }
    }

    public void renderPropDialogShape(ShapeRenderer renderer) {
        if(propertyDialogShow && curPropItem!=null ) {
            renderer.setColor(Color.BROWN);
            renderer.rect(Gdx.graphics.getWidth()/2-dialogWidth/2 , Gdx.graphics.getHeight()/2-dialogHeight/2, dialogWidth, dialogHeight);

            //выделенная строка

            renderer.setColor(Color.GREEN);
            renderer.rect(Gdx.graphics.getWidth()/2-dialogWidth/2+10, Gdx.graphics.getHeight()/2+dialogHeight/2-(propertyDialogMarker+2)*19, dialogWidth-20, 20);
        }
    }


    public void scrolled(int amount) {
        if( propertyDialogShow && curPropItem!=null ) {
            if( amount > 0 ) {
                propertyDialogMarker++;
            } else {
                propertyDialogMarker--;
            }
            if( (propertyDialogMarker > (curPropItem.getPropertiesCount()-1)) ) {
                propertyDialogMarker = (curPropItem.getPropertiesCount()-1);
            }

            if( (propertyDialogMarker<0) ) {
                propertyDialogMarker = 0;
            }
        }
    }

    public void touchDown(int screenX, int screenY, int pointer, int button) {
        if( propertyDialogShow ) {
            if( button == Buttons.RIGHT ) {
                propertyDialogShow = false;
            }

            if( button == Buttons.LEFT ) {
                curPropItem.splitParamValue(propertyDialogMarker);
            }
        } else {
            float rely = Gdx.graphics.getHeight() - screenY;
            for (int i = 0; i < editorConfig.getItemsListCount(); i++) {
                if ((screenX < rectSize) && (rely > i * rectSize) && ((rely < (i + 1) * rectSize))) {
                    curSelected = i;
                    return;
                }
            }

            int frow = 0;
            int fcol = 0;

            frow = Math.floorDiv((screenX - screenMargin), Math.round(rectSize));
            fcol = Math.floorDiv(Math.round(rely), Math.round(rectSize));

            if ((button == Input.Buttons.LEFT) && (frow >= 0) && (fcol >= 0) && (frow < editorConfig.getWidth()) && (fcol < editorConfig.getHeight())) {
                editorConfig.removeLevelItemsFrom(frow, fcol);
                editorConfig.createLevelItem(frow, fcol, curSelected);
            }

            if (button == Buttons.MIDDLE) {
                editorConfig.removeLevelItemsFrom(frow, fcol);
            }

            if (button == Buttons.RIGHT) {
                propertyDialogShow = false;
                curPropItem = editorConfig.getItemsIn(frow, fcol);
                if (curPropItem != null) {
                    propertyDialogMarker = 0;
                    propertyDialogShow = true;
                }
            }
        }
    }

}
