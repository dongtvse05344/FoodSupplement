/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.contanst.ConstantsCrawler;
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
 * @author shuu1
 */
public class MayanhjpCategoryCrawler extends BaseCrawler {

//    private static final String[] IGNORE_TEXTS = {};
    private final XCategories xcategories;

    public MayanhjpCategoryCrawler(ServletContext context, XCategories xcategories) {
        super(context);
        this.xcategories = xcategories;

    }

    public Map<String, String> getCategories(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
//            String beginTag = "<ul id=\"menu\" class=\"slide_mn\">";
//            String tag = "ul";
//
//            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            String document = getDocument(reader, xcategories.getBeginTag(), xcategories.getTag(), xcategories.getReplace());

            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MayanhjpCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(MayanhjpCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MayanhjpCategoryCrawler.class.getName()).log(Level.SEVERE, null, ex);
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

    public Map<String, String> DOMHandler(String documentString) throws XPathExpressionException, Exception {
        Map<String, String> categories = new HashMap<String, String>();

        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return categories;
        }
        XPath xpath = XMLUtils.createXPath();
//        String expression = "//li[contains(a/span,'MÁY ẢNH')]//li[not(@class)]";
        String expression = xcategories.getXcategories();

        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

//            expression = "a/@href";
            expression = xcategories.getXlink();
            String href = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
//            expression = "a";
            expression = xcategories.getXname();
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            categories.put(ConstantsCrawler.MAYANHJP + "/" + href, name);
        }
        return categories;
    }
}
