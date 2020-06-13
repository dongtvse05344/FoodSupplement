/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.contanst.ContanstCrawler;
import dongtv.dto.ProductDTO;
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
public class DemxanhProductsCrawler extends BaseCrawler {

    private static String[] IGNORE_TEXTS = {};

    public DemxanhProductsCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, ProductDTO> getProducts(String url) {
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
                if (!isFound && document.length() == 0 && line.contains("<ul class=\"ul_hot\">")) {
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
//                    line = line.replace("png\">", "png\"></img>");

                    if (line.trim().length() > 0) {
                        document += line.trim() + "\n";

                    }
                }
            }
            document = document.replaceAll("\"></a>", "\"></img></a>");
            document = document.replaceAll("&", "và");
//            System.out.println("DOCUMENT :" + document);

            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DemxanhProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DemxanhProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(DemxanhProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } 
//        catch (XMLStreamException ex) {
//            Logger.getLogger(DemxanhProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Map<String, ProductDTO> DOMHandler(String documentString) throws XPathExpressionException {
//        System.out.println(documentString);
        Map<String, ProductDTO> products = new HashMap<String, ProductDTO>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return products;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = "//li";
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

            expression = ".//img/@src";
            String image = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//a[@class='product-image']/@href";
            String link = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//div[@class='p_it_info_name']"; 
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//div[@class='p_it_info_price']";
            String price = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            price = price.substring(0,price.length()-4).replace(".", "");
            ProductDTO productDTO = new ProductDTO(Integer.parseInt(price), name.trim(), image.trim(), link.trim());
            products.put(link, productDTO);
        }
        return products;

    }

    public Map<String, ProductDTO> stAXParserForProducts(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, ProductDTO> products = new HashMap<String, ProductDTO>();
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("li".equals(tagName)) {
                    String href ="", name="", img = "";
                    while (true) {
                        boolean isBreak = false;
                        event = (XMLEvent) eventReader.next();
                        if (event.isStartElement()) {
                            startElement = event.asStartElement();
                            tagName = startElement.getName().getLocalPart();
                            switch (tagName) {
                                case "a":
                                    Attribute hrefLink = startElement.getAttributeByName(new QName("href"));
                                    href = hrefLink.getValue();
                                    break;
                                case "img":
                                    Attribute imgLink = startElement.getAttributeByName(new QName("src"));
                                    img = imgLink.getValue();
                                    break;
                                case "div":
                                    Attribute className = startElement.getAttributeByName(new QName("class"));
                                    if (className != null && className.getValue().equals("p_it_info_name")) {
                                        event = (XMLEvent) eventReader.next();
                                        if (event.isCharacters()) {
                                            name = event.asCharacters().getData();
                                            isBreak = true;
                                        }
                                    }
                                    break;
                            }
                        }
                        if (isBreak) {
                            break;
                        }
                    }
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setName(name);
                    productDTO.setOriginalLink(ContanstCrawler.DEMXANH +href);
                    productDTO.setImage( img);
                    products.put(href, productDTO);
                }
            }
        }
        return products;
    }
}
