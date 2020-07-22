/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.contanst.ConstantsCrawler;
import dongtv.dto.raw.ProductRawDTO;
import dongtv.pageconfig.XPage;
import dongtv.pageconfig.XProducts;
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

//    private static final String[] IGNORE_TEXTS = {"class=pagingSpace"};
    private final XProducts xProducts;
    private final XPage xPage;

    public MayanhvnProductsCrawler(ServletContext context, XProducts xProducts, XPage xPage) {
        super(context);
        this.xProducts = xProducts;
        this.xPage = xPage;
    }

    public Map<String, ProductRawDTO> getProducts(String url) {
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
//            String beginTag = "<div class=\"product-list\">";
//            String tag = "div";
//            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);

            String document = getDocument(reader, xProducts.getBeginTag(), xProducts.getTag(), xProducts.getReplace());
            return DOMHandler(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MayanhvnProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(MayanhvnProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MayanhvnProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
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
        String expression = xProducts.getXproducts();
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

            expression = xProducts.getXimage();
            String image = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = xProducts.getXlink();
            String link = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = xProducts.getXname();
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = xProducts.getXprice();
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
//            String beginTag = "<div class=\"paging\">";
//            String tag = "div";
//            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);

            String document = getDocument(reader, xPage.getBeginTag(), xPage.getTag(), xPage.getReplace());
            return DOMHandlerPages(document);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MayanhvnProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | XPathExpressionException ex) {
            Logger.getLogger(MayanhvnProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MayanhvnProductsCrawler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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

    public Map<String, String> DOMHandlerPages(String documentString) throws Exception {
        Map<String, String> categories = new HashMap<String, String>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return categories;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = xPage.getXpage();
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);
            expression = xPage.getXlink();
            String href = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            if (href == null || href.length() == 0) {
                continue;
            }
            categories.put(ConstantsCrawler.MAYANHVN_ROOT + href, "x");
        }
        return categories;
    }
}
