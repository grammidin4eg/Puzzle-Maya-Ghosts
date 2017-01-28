package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.karachevtsev.mayaghostsballs.Cell;

/**
 * Created by root on 28.01.2017.
 */

public class Grid {
    private final ResKeeper res;
    private final Sprite floor;
    private float cellSize;
    private Vector<Cell> cells = new Vector<Cell>();
    private float marginY;

    public Grid(ResKeeper _res, int sizex, int sizey, float _cellsize) {
        res = _res;
        floor = res.getSprite("floor");
        cellSize = _cellsize;

        int cx = 0;
        int cy = 0;
        for(int i=0;i<(sizex*sizey);i++) {
            cells.add(new Cell(cx, cy, CellType.FLOOR));
            cx=cx+1;
            if(cx>=sizex) {
                cx=0;
                cy=cy+1;
            }
        }
    }
    public void render(SpriteBatch sb) {
        for( Cell curCell : cells ) {
            float posX = curCell.getCX()*cellSize;
            float posY = marginY + curCell.getCY()*cellSize;
            Sprite sprite = floor;

            //sb.draw(sprite.getTexture(), posX, posY);
            floor.setSize(cellSize, cellSize);
            floor.setX(posX);
            floor.setY(posY);
            floor.draw(sb);
        }
    }

    public void setCellSize(float _newcellsize) {
        cellSize = _newcellsize;
    }

    public void setMarginY(float _marginy) {
        marginY = _marginy;
    }
}
