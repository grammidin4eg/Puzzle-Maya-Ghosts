package com.karachevtsev.matrixeditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Created by karachevtsev on 06.03.2016.
 */
public class LevelManager {
    private final EditorConfig editorConfig;
    private final float dialogHeight;
    private final float dialogWidth;
    private String levelName = "level";
    private boolean saveDialogShow = false;
    private boolean loadDialogShow = false;

    public LevelManager(EditorConfig ec) {
        editorConfig = ec;
        dialogHeight = Gdx.graphics.getHeight()/4;
        dialogWidth = Gdx.graphics.getWidth()/2;
    }

    public void newLevel() {
        editorConfig.getLevelItems().clear();
    }

    public void showSaveDialog() {
        saveDialogShow = true;
    }

    public void showLoadDialog() {
        saveDialogShow = true;
        loadDialogShow = true;
    }

    public void saveLevel() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            Document doc = factory.newDocumentBuilder().newDocument();

            Element root = doc.createElement("level");
            doc.appendChild(root);

            for(LevelItem cur_item : editorConfig.getLevelItems() ) {
                Element item1 = doc.createElement("item");
                item1.setAttribute("row", String.valueOf(cur_item.getRow()));
                item1.setAttribute("col", String.valueOf(cur_item.getCol()));
                item1.setAttribute("type", cur_item.getType());

                //проперти
                for(ItemProperty ip : cur_item.getProperties()) {
                    Element ival = doc.createElement("ival");
                    ival.setAttribute("name", ip.getName());
                    ival.setAttribute("value", ip.getValue());
                    item1.appendChild(ival);
                }

                root.appendChild(item1);
            }

            File file = new File(levelName+".xml");

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(file));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void loadLevel() {
        newLevel();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(levelName+".xml", new DefaultHandler() {

                public LevelItem curLevelItem = null;

                public void startElement(String uri, String localName, String qName,
                                         Attributes attributes) throws SAXException {

                    if( qName == "item") {
                        int row = Integer.valueOf(attributes.getValue("row"));
                        int col = Integer.valueOf(attributes.getValue("col"));
                        String id = attributes.getValue("type");
                        curLevelItem = editorConfig.createLevelItembyID(row, col, id);
                    }

                    if( (qName == "ival") && curLevelItem != null ) {
                        String ivalName = attributes.getValue("name");
                        String ivalValue = attributes.getValue("value");

                        curLevelItem.addProperty(ivalName, ivalValue);
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

    public void keyTyped(char character) {
        int keycode = Integer.valueOf(character);
        System.out.println("keycode: "+String.valueOf(keycode));
        if(!saveDialogShow || keycode<1 || keycode==13 || keycode == 27) {
            return;
        }

        levelName+=character;
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.N:
                if(!saveDialogShow) {
                    newLevel();
                }
                break;

            case Input.Keys.S:
                if(!saveDialogShow) {
                    showSaveDialog();
                }
                break;

            case Input.Keys.L:
                if(!saveDialogShow) {
                    showLoadDialog();
                }
                break;

            case Input.Keys.A:
                if(!saveDialogShow) {
                    for(int i=0; i < editorConfig.getLevelItemsCount(); i++ ) {
                        editorConfig.getLevelItems().get(i).setAnimated(false);
                    }
                }
                break;

            case Input.Keys.ESCAPE:
                if(saveDialogShow) {
                    saveDialogShow = false;
                    loadDialogShow = false;
                }
                break;

            case Input.Keys.BACKSPACE:
                if( saveDialogShow && levelName.length()>1 ) {
                    levelName = levelName.substring(0, levelName.length()-2);
                }
                break;

            case Input.Keys.ENTER:
                if( saveDialogShow && levelName.length()>1 ) {
                    saveDialogShow = false;
                    if( loadDialogShow ) {
                        loadDialogShow = false;
                        loadLevel();
                    } else {
                        saveLevel();
                    }
                }
        }

    }

    public void renderDialog(ShapeRenderer sr) {
        if(!saveDialogShow) {
            return;
        }
        sr.setColor(Color.BROWN);
        sr.rect(Gdx.graphics.getWidth()/2-dialogWidth/2 , Gdx.graphics.getHeight()/2-dialogHeight/2, dialogWidth, dialogHeight);
    }

    public void renderDialogBatch(SpriteBatch batch, BitmapFont font) {
        if(!saveDialogShow) {
            return;
        }
        font.draw(batch, levelName, Gdx.graphics.getWidth()/2-dialogWidth/2.1f, Gdx.graphics.getHeight()/2);
        String dialogTitle = "========== save file ===========";
        if(loadDialogShow) {
            dialogTitle = "========== load file ===========";
        }
        font.draw(batch, dialogTitle, Gdx.graphics.getWidth()/2-dialogWidth/2, Gdx.graphics.getHeight()/2+dialogHeight/2.5f);
    }
}
