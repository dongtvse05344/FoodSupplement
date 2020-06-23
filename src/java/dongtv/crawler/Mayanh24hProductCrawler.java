/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

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

/**
 *
 * @author Tran Dong
 */
public class Mayanh24hProductCrawler extends BaseCrawler {

    private static String[] IGNORE_TEXTS = {"<hr>","<img.*jpg\">"};

    public Mayanh24hProductCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getProduct(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String beginTag = "<div class=\"col-sm-7\">";
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

    public Map<String, String> DOMHandler(String documentString) throws XPathExpressionException {
        Map<String, String> product = new HashMap<String, String>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return product;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = ".//table[@class='table-description']";
        String description = xpath.evaluate(expression, document, XPathConstants.STRING).toString();
        product.put("DES", description.trim());
        
        expression = ".//h3[@class='product-name']";
        String name = xpath.evaluate(expression, document, XPathConstants.STRING).toString();
        product.put("NAME", name.trim());
        
        expression = ".//tr[last()]/td[last()]";
        String params = xpath.evaluate(expression, document, XPathConstants.STRING).toString();
        product.put("PARAMS", params.trim());
        return product;
    }
}