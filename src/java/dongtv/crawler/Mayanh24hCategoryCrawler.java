/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.pageconfig.XCategories;
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
public class Mayanh24hCategoryCrawler extends BaseCrawler {

    private static final String[] IGNORE_TEXTS = {};
//    private static final String beginTag = "<ul class=\"list-category\">";
//    private static final String tag = "ul";
    private final XCategories xcategories;

    public Mayanh24hCategoryCrawler(ServletContext context, XCategories xcategories) {
        super(context);
        this.xcategories = xcategories;
    }

    public Map<String, String> getCategories(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
//            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            String document = getDocument(reader, xcategories.getBeginTag().trim(), xcategories.getTag().trim(), xcategories.getReplace());
            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Mayanh24hCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(Mayanh24hCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Mayanh24hCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Logger.getLogger(Mayanh24hCategoryCrawler.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

//    private static final String Xcategories = "//li";
//    private static final String Xhref = "a/@href";
//    private static final String Xname = "a";
    public Map<String, String> DOMHandler(String documentString) throws XPathExpressionException, Exception {
        Map<String, String> categories = new HashMap<String, String>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return categories;
        }
        XPath xpath = XMLUtils.createXPath();
//        String expression = Xcategories;
        String expression = xcategories.getXcategories().trim();
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

//            expression = Xhref;
            expression = xcategories.getXlink().trim();
            String href = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
//            expression = Xname;
            expression = xcategories.getXname().trim();
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            categories.put(href, name);
        }
        return categories;
    }

}
