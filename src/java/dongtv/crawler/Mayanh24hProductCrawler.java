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
public class Mayanh24hProductCrawler extends BaseCrawler {

//    private static final String[] IGNORE_TEXTS = {"<hr>", "<img.*jpg\">"};
//    private static final String beginTag = "<div class=\"col-sm-7\">";
//    private static final String tag = "div";
    private final XProduct xProduct;
    public Mayanh24hProductCrawler(ServletContext context,XProduct xProduct) {
        super(context);
        this.xProduct = xProduct;
    }

    public Map<String, String> getProduct(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            
            
//            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);

            String document = getDocument(reader, xProduct.getBeginTag(), xProduct.getTag(), xProduct.getReplace());
            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Mayanh24hProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(Mayanh24hProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Mayanh24hProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                Logger.getLogger(Mayanh24hProductCrawler.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }
    
//    private static final String Xdes = ".//table[@class='table-description']";
//    private static final String Xname =  ".//h3[@class='product-name']";
//    private static final String xParams = ".//tr[last()]/td[last()]";
    public Map<String, String> DOMHandler(String documentString) throws XPathExpressionException, Exception {
        Map<String, String> product = new HashMap<String, String>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return product;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = xProduct.getXdescription();
        String description = xpath.evaluate(expression, document, XPathConstants.STRING).toString();
        product.put("DES", description.trim());

        expression = xProduct.getXname();
        String name = xpath.evaluate(expression, document, XPathConstants.STRING).toString();
        product.put("NAME", name.trim());

        expression = xProduct.getXparam();
        String params = xpath.evaluate(expression, document, XPathConstants.STRING).toString();
        product.put("PARAMS", params.trim());
        return product;
    }
}
