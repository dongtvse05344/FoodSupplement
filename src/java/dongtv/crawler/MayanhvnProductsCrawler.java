/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.contanst.ConstantsCrawler;
import dongtv.dto.ProductRawDTO;
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
public class MayanhvnProductsCrawler extends BaseCrawler {

    private static String[] IGNORE_TEXTS = {"class=pagingSpace"};

    public MayanhvnProductsCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, ProductRawDTO> getProducts(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String beginTag = "<div class=\"product-list\">";
            String tag = "div";

            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LienaCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(LienaCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
        String expression = "//li[contains(@class, 'item')]";
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

            expression = ".//img/@src";
            String image = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//a[@class='p-name']/@href";
            String link = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//a[@class='p-name']";
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//span[@class='p-price']";
            String price = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            Integer priceI = -1;
            try {
                price = price.substring(0, price.length() - 1).replace(".", "");
                priceI = Integer.parseInt(price);
            } catch (Exception e) {
            }
            ProductRawDTO productRawDTO = new ProductRawDTO(name.trim(), ConstantsCrawler.MAYANHVN_ROOT + image.trim(), ConstantsCrawler.MAYANHVN_ROOT + link.trim(), priceI);
            products.put(link, productRawDTO);
        }
        return products;
    }

    public Map<String, String> getPages(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String beginTag = "<div class=\"paging\">";
            String tag = "div"; 

            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            return DOMHandlerPages(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LienaCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(LienaCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception e) {
            return null;
        }finally {
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

    public Map<String, String> DOMHandlerPages(String documentString) throws Exception {
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
            if (href == null || href.length() == 0) {
                continue;
            }
            categories.put(ConstantsCrawler.MAYANHVN_ROOT + href, name);
        }
        return categories;
    }
}
