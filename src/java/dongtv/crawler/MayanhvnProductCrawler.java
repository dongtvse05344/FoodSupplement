/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.pageconfig.XProduct;
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
public class MayanhvnProductCrawler extends BaseCrawler {

//    private static final String[] IGNORE_TEXTS = {};
    private final XProduct xProduct;

    public MayanhvnProductCrawler(ServletContext context, XProduct xProduct) {
        super(context);
        this.xProduct = xProduct;
    }

    public Map<String, String> getProduct(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
//            String beginTag = "<div class=\"detail-summary\">";
//            String tag = "div";
//
//            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            String document = getDocument(reader, xProduct.getBeginTag(), xProduct.getTag(), xProduct.getReplace());
            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MayanhvnProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(MayanhvnProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MayanhvnProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
//        System.out.println(documentString);
        Map<String, String> product = new HashMap<String, String>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return product;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = xProduct.getXdescription();
        String description = xpath.evaluate(expression, document, XPathConstants.STRING).toString();
        product.put("DES", description);
        return product;
    }
}
