import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Created by user on 07.07.2016.
 */
public class LevelSpliltter {
    public static void main(String[] args) {
        System.out.println("Start split");
        try {
            // получаем xml парсер с настройками по умолчанию
            DocumentBuilder xml = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // разбираем demo.xml и создаем Document
            Document doc = xml.parse(new File("levels.xml"));
            // корневой элемент документа
            Element rootel = doc.getDocumentElement();


            // имя корневого элемента
            System.out.println(rootel.getNodeName());

            // список имен дочерних элементов
            System.out.println("Child elements: ");
            NodeList lst = rootel.getChildNodes();
            for (int i = 0; i < lst.getLength(); i++) {
                if( lst.item(i).getNodeName().equals("level") ) {
                    NamedNodeMap attrs = lst.item(i).getAttributes();
                    System.out.println(lst.item(i).getNodeName() + " " + attrs.getNamedItem("name") + "  " + attrs.getNamedItem("key") );

                    File file = new File("level_"+attrs.getNamedItem("key").getTextContent()+".xml");

                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    transformer.transform(new DOMSource(lst.item(i)), new StreamResult(file));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

