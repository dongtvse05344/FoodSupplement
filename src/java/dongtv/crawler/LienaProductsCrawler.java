/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.dto.ProductDTO;
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
public class LienaProductsCrawler extends BaseCrawler {

    private static String[] IGNORE_TEXTS = {"alt=\".*\"", "tabindex=\"-1\"","style=\".*\""};

    public LienaProductsCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, ProductDTO> getProducts(String url) throws XPathExpressionException {
        BufferedReader reader = null;
        try {
            String beginTag = "<ol class=\"products list items product-items same-height\">";
            String tag = "ol";
            isDebug = false;
            reader = getBufferedReaderForURL(url);
            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            document = document.replaceAll("\"></a>", "\"></img></a>");

            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LienaProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LienaProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } 
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
        System.out.println(documentString);
        Map<String, ProductDTO> products = new HashMap<String, ProductDTO>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return products;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = "//div[contains(@class,'product-item-info')]";
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        System.out.println(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

            expression = ".//img[contains(@class,'product-image-photo')]/@data-src";
            String image = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//a[contains(@class,'product-item-link')]/@href";
            String link = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//a[contains(@class,'product-item-link')]";
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//span[@class='price']";
            String price = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            price = price.substring(0, price.length() - 2).replace(".", "");
            ProductDTO productDTO = new ProductDTO(Integer.parseInt(price), name.trim(), image.trim(), link.trim());
            products.put(link, productDTO);
        }
        return products;

    }

    public Map<String, String> getPages(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String line = "";
            String document = "";
            boolean isFound = false;
            //make sure we don't have case like this <div><div></div></div>
            while ((line = reader.readLine()) != null) {
                //check the begin tag
                if (!isFound && line.contains("<ul class=\"items pages-items\" aria-labelledby=\"paging-label\">")) {
                    isFound = true;
                }
                if (isFound) {
                    for (String ignore_text : IGNORE_TEXTS) {
                        line = line.replaceAll(ignore_text, "").trim();
                    }
                    document += line + "\n";
                }
                if (isFound && line.contains("</ul>")) {
                    break;
                }
            }
            document = document.replaceAll("&", "và");
            if (document.length() == 0) {
                return null;
            }
            return stAXParserForPages(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LienaProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LienaProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(LienaProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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

    public Map<String, String> stAXParserForPages(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> pages = new HashMap<String, String>();
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    Attribute className = startElement.getAttributeByName(new QName("class"));
                    if (className != null && className.getValue().equals("page")) {
                        Attribute hreflink = startElement.getAttributeByName(new QName("href"));
                        pages.put(hreflink.getValue(), "1");
                    }
                }
            }
        }
        return pages;
    }

}
