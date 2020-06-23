/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.contanst.ConstantsCrawler;
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
public class MayanhvnCategoryCrawler extends BaseCrawler {

    private static String[] IGNORE_TEXTS = {};

    public MayanhvnCategoryCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getCategories(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String beginTag = "<ul class=\"ul list-filter\">";
            String tag = "ul";

            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            document = document.replaceAll("></a>", "></img></a>");
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
        Map<String, String> categories = new HashMap<String, String>();

        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return categories;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = "//li";
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

            expression = "a/@href";
            String href =  xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = "a";
            String name =  xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            categories.put(ConstantsCrawler.MAYANHVN_ROOT + href, name);
        }
        return categories;
    }

}
