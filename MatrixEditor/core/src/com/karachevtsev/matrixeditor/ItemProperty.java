package com.karachevtsev.matrixeditor;

import java.util.Vector;

/**
 * Created by karachevtsev on 11.03.2016.
 */
public class ItemProperty {
    private String name;
    private int cur_value = 0;
    private String forId = null;
    private Vector<PropertyValue> values = new Vector<PropertyValue>();

    public String toString() {
        return name+" :: "+values.get(cur_value).value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return values.get(cur_value).value;
    }

    public String getMark() {
        return values.get(cur_value).mark;
    }

    public void splitParamValue() {
        cur_value++;
        if( cur_value > (values.size()-1) ) {
            cur_value = 0;
        }
    }

    public String getForId() {
        return forId;
    }

    public void forId(String id) {
        forId = id;
    }

    public void setValue(String value) {
        for( int i=0;i<values.size();i++  ) {
            if( values.get(i).value.equals(value) ) {
                cur_value = i;
                return;
            }
        }

        values.add(new PropertyValue(value, null));
    }

    public ItemProperty(String name) {
        this.name = name;
    }

    public void addValue(String value, String mark) {
        values.add(new PropertyValue(value, mark));
    }

    public ItemProperty cloneProperty() {
        ItemProperty res = new ItemProperty(name);
        for( PropertyValue cur : values  ) {
            res.addValue(cur.value, cur.mark);
        }
        return res;
    }
}
