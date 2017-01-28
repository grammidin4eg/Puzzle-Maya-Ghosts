package com.karachevtsev.mayaghostsballs;

import com.badlogic.gdx.math.Vector;

/**
 * Created by root on 28.01.2017.
 */

public class Cell  {
    private final int cx;
    private final int cy;
    private CellType type;

    public Cell(int _cx, int _cy, CellType _type) {
        cx = _cx;
        cy = _cy;
        type = _type;
    }

    public int getCX() {
        return cx;
    }

    public int getCY() {
        return cy;
    }
}
