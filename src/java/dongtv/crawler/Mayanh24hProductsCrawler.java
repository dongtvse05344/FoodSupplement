/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.dto.raw.ProductRawDTO;
import dongtv.util.XMLUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
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
public class Mayanh24hProductsCrawler extends BaseCrawler {

    private static final String[] IGNORE_TEXTS = {};

    public Mayanh24hProductsCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, ProductRawDTO> getProducts(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String beginTag = "<div class=\"list-product\">";
            String tag = "div";

            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Mayanh24hProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(Mayanh24hProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Mayanh24hProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
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

    public Map<String, ProductRawDTO> DOMHandler(String documentString) throws XPathExpressionException, Exception {
//        System.out.println(documentString);
        Map<String, ProductRawDTO> products = new HashMap<String, ProductRawDTO>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return products;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = "//div[contains(@class, 'product-layout')]";
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

            expression = ".//img/@src";
            String image = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//h4/a/@href";
            String link = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//h4/a";
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//span[@class='price-new']";
            String price = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            Integer priceI = -1;
            try {
                price = price.substring(0, price.length() - 1).replace(",", "");
                priceI = Integer.parseInt(price);
            } catch (Exception e) {
            }
            ProductRawDTO productRawDTO = new ProductRawDTO(name.trim(), image.trim(), link.trim(), priceI);
            products.put(link, productRawDTO);
        }
        return products;
    }

    public Map<String, String> getPages(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String beginTag = "<ul class=\"pagination\">";
            String tag = "ul";

            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            document = document.replaceAll("></a>", "></img></a>");
            return DOMHandlerPages(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Mayanh24hProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(Mayanh24hProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Mayanh24hProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
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

    public Map<String, String> DOMHandlerPages(String documentString) throws XPathExpressionException, Exception {
        Map<String, String> categories = new HashMap<String, String>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return categories;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = "//a";
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

            expression = "@href";
            String href = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = "a";
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            categories.put(href, name);
        }
        return categories;
    }
}
