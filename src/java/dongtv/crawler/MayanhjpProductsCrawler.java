/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.contanst.ConstantsCrawler;
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
 * @author shuu1
 */
public class MayanhjpProductsCrawler extends BaseCrawler {

    private static String[] IGNORE_TEXTS = {"</br>", "color=[a-z]{1,6}", "<font >Th"};

    public MayanhjpProductsCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, ProductRawDTO> getProducts(String url) {
        System.out.println(url);
        BufferedReader reader = null;
        try {
            reader = getBufferedReaderForURL(url);
            String beginTag = "<table id=\"ctl00_ContentPlaceHolder1_CT_SanPham1_dtlProducts\" class=\"CT_SanPham_dtlProducts\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;\">";
            String tag = "table";
            isDebug = false;

            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            if(url.equals("http://www.mayanhjp.com/canon-ixus---ixy---sd_7z11.aspx")) {
                document = document.replace("<font color=\"red\">        ", "");
            }
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
        Map<String, ProductRawDTO> products = new HashMap<String, ProductRawDTO>();
        Document document = XMLUtils.parseStringtoDom(documentString);

        if (document == null) {
            System.out.println("Cannot create document");
            return products;
        }
        XPath xpath = XMLUtils.createXPath();
        String expression = "table/tr";
        NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i).cloneNode(true);

            expression = ".//img/@src";
            String image = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            image = image.replace(" ", "%20");
            expression = ".//a[@class='LinkSanpham']/@href";
            String link = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//a[@class='LinkSanpham']";
            String name = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//span[contains(.,'₫')]";
            String price = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            expression = ".//td[@class='CT_SanPham_table4_1td2']";
            String description = xpath.evaluate(expression, node, XPathConstants.STRING).toString();
            System.out.println(image);

            Integer priceI = -1;
            try {
                String priceregex = HTMLUtilities.getAllMatches(price, "[0-9]{1,3}[.,]{0,1}[0-9]{1,3}[.,]{0,1}[0-9]{1,3}").get(0)
                        .replace(".", "").replace(",", "");
                priceI = Integer.parseInt(priceregex);
            } catch (Exception e) {
            } 
            ProductRawDTO productRawDTO = new ProductRawDTO(name.trim(), ConstantsCrawler.MAYANHJP + image.trim(), link.trim(), priceI, description);
            products.put(link, productRawDTO);
        }
        return products;
    }

}
