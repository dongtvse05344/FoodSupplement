/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.contanst.ContanstCrawler;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductRawDTO;
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

    public Map<String, ProductRawDTO> getProducts(String url) {
        BufferedReader reader = null;
        try {
            String beginTag = "<ul class=\"ul_hot\">";
            String tag = "ul";
            reader = getBufferedReaderForURL(url);
            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            document = document.replaceAll("\"></a>", "\"></img></a>");
            document = document.replaceAll("&", "và");

            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DemxanhProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DemxanhProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(DemxanhProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
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

    public Map<String, ProductRawDTO> DOMHandler(String documentString) throws XPathExpressionException {
//        System.out.println(documentString);
        Map<String, ProductRawDTO> products = new HashMap<String, ProductRawDTO>();
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
            if (!link.contains(ContanstCrawler.DEMXANH)) {
                link = ContanstCrawler.DEMXANH + link;
            }
            expression = ".//div[@class='p_it_info_name']";
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//div[@class='p_it_info_price']";
            String price = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            price = price.substring(0, price.length() - 4).replace(".", "");
            Integer priceI = null;
            try {
                priceI = Integer.parseInt(price);
            } catch (Exception e) {
            }
            ProductRawDTO productRawDTO = new ProductRawDTO(documentString, name.trim(), image.trim(), link.trim(),priceI);
            products.put(link, productRawDTO);
        }
        return products;
    }

}
