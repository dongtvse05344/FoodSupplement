/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.util.HTMLUtilities;
import dongtv.util.XMLUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Tran Dong
 */
public class DemxanhCategoryCrawler extends BaseCrawler {

    private static String[] IGNORE_TEXTS = {};

    public DemxanhCategoryCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getCategories(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isFound = false;
            int ulCount = 0;

            //make sure we don't have case like this <ul><ul></ul></ul>
            while ((line = reader.readLine()) != null) {
                //check the begin tag
                if (!isFound && document.length() == 0 && line.contains("<ul class=\"menu_ul\">")) {
                    isFound = true;
                }
                // the first ul contain class, another ul same like this <ul>

                if (isFound && line.contains("<ul")) {
                    ulCount = ulCount + HTMLUtilities.getAllMatches(line, "<ul").size();
                }
                if (ulCount > 0 && line.contains("</ul>")) {
                    ulCount = ulCount - HTMLUtilities.getAllMatches(line, "</ul>").size();
                    if (ulCount == 0) {
                        document += line.trim() + "\n";
                        isFound = false;
                    }
                }
                if (isFound) {

                    for (String ignore_text : IGNORE_TEXTS) {
                        line = line.replace(ignore_text, "");
                    }
                    if (line.trim().length() > 0) {
                        document += line.trim() + "\n";
                    }
                }
            }
            document = document.replaceAll("></a>", "></img></a>");
            document = document.replaceAll("&", "và");
//            System.out.println("DOCUMENT :" + document);
            return DOMHandler(document);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(DemxanhCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DemxanhCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DemxanhCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } //        catch (XMLStreamException ex) {
        //            Logger.getLogger(DemxanhCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    public Map<String, String> stAXParserForCategories(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> categories = new HashMap<String, String>();
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("li".equals(tagName)) {
                    Attribute classLink = startElement.getAttributeByName(new QName("class"));
                    if (classLink != null && classLink.getValue().contains("menu_li")) {
//                        while (eventReader.hasNext()) {
                        eventReader.next();
                        event = (XMLEvent) eventReader.next();
                        if (event.isStartElement()) {
                            startElement = event.asStartElement();
                            if ("a".equals(startElement.getName().getLocalPart())) {
                                Attribute href = startElement.getAttributeByName(new QName("data-url"));
                                eventReader.next();
                                eventReader.next();
                                eventReader.next();
                                eventReader.next();
                                eventReader.next();

                                XMLEvent eventData = (XMLEvent) eventReader.next();

                                String cate = eventData.asCharacters().getData();
                                categories.put(href.getValue(), cate);
                            }
//                            }
                        }
                    }
                }
            }
        }
        return categories;
    }

    public Map<String, String> DOMHandler(String documentString) throws XPathExpressionException {
        Map<String, String> categories = new HashMap<String, String>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return categories;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = "//li[contains(@class,'menu_li')]";
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        System.out.println(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

            expression = "a/@data-url";
            String href = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = "a";
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            categories.put(href, name);
        }
        return categories;

    }
}
