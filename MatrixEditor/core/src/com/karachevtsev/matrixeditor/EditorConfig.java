package com.karachevtsev.matrixeditor;
import com.badlogic.gdx.Gdx;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by karachevtsevyy on 05.03.2016.
 */
public class EditorConfig {

    private int gridWidth = 0;
    private int gridHeight = 0;
    private int gridDefault = 0;

    private Vector<GridItem> itemList = new Vector<GridItem>();
    private Vector<LevelItem> levelItems = new Vector<LevelItem>();
    private Vector<ItemProperty> defaultProperty = new Vector<ItemProperty>();
    private HashMap<String, TextureAtlas> picAtlas = new HashMap<String, TextureAtlas>();

    public EditorConfig() {
        loadConfigXml();
    }

    public int getWidth() {
        return gridWidth;
    }

    public int getHeight() {
        return gridHeight;
    }

    public Vector<LevelItem> getLevelItems() {
        return levelItems;
    }

    public int getLevelItemsCount() {
        return levelItems.size();
    }

    public LevelItem createLevelItem(int row, int col, int id) {
        LevelItem newLevelItem = new LevelItem(row, col, itemList.get(id).getId(), itemList.get(id), copyDefaultProperty(itemList.get(id).getId()) );
        levelItems.add(newLevelItem);
        return newLevelItem;
    }

    public LevelItem createLevelItembyID(int row, int col, String id) {
        int item_id = 0;
        for(int i=0;i<itemList.size();i++) {
            if( itemList.get(i).getId().equals(id) ) {
                item_id = i;
                break;
            }
        }
        LevelItem newLevelItem = new LevelItem(row, col, itemList.get(item_id).getId(), itemList.get(item_id), copyDefaultProperty(id));
        levelItems.add(newLevelItem);
        return newLevelItem;
    }

    public void removeLevelItemsFrom(int row, int col) {
        for( LevelItem curitem : levelItems ) {
            if( (curitem.getRow() == row) && (curitem.getCol() == col) ) {
                levelItems.remove(curitem);
                return;
            }
        }
    }

    public LevelItem getItemsIn(int row, int col) {
        for( LevelItem curitem : levelItems ) {
            if( (curitem.getRow() == row) && (curitem.getCol() == col) ) {
                return curitem;
            }
        }
        return null;
    }

    public Vector<GridItem> getItemList() {
        return itemList;
    }

    public int getItemsListCount() {
        return itemList.size();
    }

    private void loadConfigXml() {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();


            saxParser.parse("editor_config.xml", new DefaultHandler() {
                public String curItemId;
                ItemProperty cur_prop = null;

                @Override
                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) throws SAXException {


                    if( qName == "grid" ) {
                        gridWidth = Integer.valueOf(attributes.getValue("width"));
                        gridHeight = Integer.valueOf(attributes.getValue("height"));
                        gridDefault = Integer.valueOf(attributes.getValue("default"));
                    }

                    if( qName == "atlas" ) {
                        String atlas_name = attributes.getValue("name");
                        if( atlas_name != null ) {
                            picAtlas.put(atlas_name, new TextureAtlas(atlas_name+".txt"));
                        }
                    }

                    if( qName == "item") {
                        String id = attributes.getValue("id");
                        String name = attributes.getValue("name");
                        String file = attributes.getValue("file");
                        String itematlas = attributes.getValue("atlas");
                        if( itematlas == null ) {
                            itemList.add(new GridItem(id, name, file));
                        } else {
                            Array<Sprite> cur_sprite = picAtlas.get(itematlas).createSprites(file);
                            if( cur_sprite != null ) {
                                itemList.add(new GridItem(id, name, cur_sprite));
                            }
                        }
                        curItemId = id;
                    }

                    if( qName == "prop") {
                        cur_prop = new ItemProperty(attributes.getValue("name"));
                    }

                    if( qName == "value") {
                        String mark = attributes.getValue("mark");
                        String val = attributes.getValue("val");

                        cur_prop.addValue(val, mark);
                    }

                    if( qName == "ival") {
                        String name = attributes.getValue("name");
                        String value = attributes.getValue("value");

                        ItemProperty newItemProp = new ItemProperty(name);
                        newItemProp.addValue(value, null);
                        newItemProp.forId(curItemId);
                        defaultProperty.add(newItemProp);
                    }
                }

                @Override
                public void endElement (String uri, String localName, String qName) {
                    if( qName == "prop") {
                        defaultProperty.add(cur_prop);
                    }
                }

            });

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector<ItemProperty> copyDefaultProperty(String id) {
        Vector<ItemProperty> res = new Vector<ItemProperty>();
        for(ItemProperty cur : defaultProperty ) {
            if( (id == null) || (cur.getForId() == null) || (id.equals(cur.getForId())) ) {
                //проверим, может это предопределение общего свойства
                boolean replaced = false;
                if( id.equals(cur.getForId()) ) {
                    for(int i=0; i<res.size(); i++) {
                        if (res.get(i).getName().equals( cur.getName() ) ) {
                            res.get(i).setValue( cur.getValue() );
                            replaced = true;
                        }
                    }
                }
                //добавим
                if ( !replaced ) {
                    res.add(cur.cloneProperty());
                }
            }
        }
        return res;
    }
}
