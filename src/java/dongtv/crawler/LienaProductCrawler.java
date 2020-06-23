/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.thread.LienaThread;
import dongtv.util.HTMLUtilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Tran Dong
 */
public class LienaProductCrawler extends BaseCrawler {

    private static String[] IGNORE_TEXTS = {"alt=\".*\"", "<!--.*?-->"};

    public LienaProductCrawler(ServletContext context) {
        super(context);
    }

    public Map<String, String> getProduct(String url) {
        System.out.println(url);
        BufferedReader reader = null;
        try {
            String beginTag = "<div class=\"product attribute description\">";
            String tag = "div";
            reader = getBufferedReaderForURL(url);
            String document = getDocument(reader, beginTag, tag, IGNORE_TEXTS);
            if (document.length() > 0) {
                return stAXParserForProduct(document);
            }
            return null;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LienaProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LienaProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(LienaProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
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

    public Map<String, String> stAXParserForProduct(String document) throws UnsupportedEncodingException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        Map<String, String> products = new HashMap<String, String>();
        String des = "";
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                String tagName = startElement.getName().getLocalPart();
                if ("p".equals(tagName)) {
                    event = (XMLEvent) eventReader.next();
                    if(event.isCharacters()) {
                        des = des + event.asCharacters().getData().toString().trim();
                    }
                }
            }
        }
        products.put(LienaThread.DESCRIPTION_TAG, des);
        return products;
    }

//    public List<VolumeDTO> getProductVolume(String url) {
//        BufferedReader reader = null;
//        try {
//            reader = getBufferedReaderForURL(url);
//            String line = "";
//            String volumes = "";
//            //make sure we don't have case like this <div><div></div></div>
//            while ((line = reader.readLine()) != null) {
//                //check the begin tag
//                if (volumes.length() == 0 && line.contains("options\":[")) {
//                    volumes = line.trim();
//                }
//            }
//            List<String> types = HTMLUtilities.getAllMatches(volumes, "\"[0-9]{2,3}x[0-9]{2,3}x[0-9]{1,2}");
//            List<String> resultTypes = new ArrayList<>();
//            List<String> string_prices = HTMLUtilities.getAllMatches(volumes, ".finalPrice\"..\"amount\":[0-9]*}.");
//            List<VolumeDTO> volumeDTOs = new ArrayList<>();
//            List<Double> prices = new ArrayList<>();
//            for (String price : string_prices) {
//                price = price.substring(23, price.length() - 2);
//                prices.add(Double.parseDouble(price));
//            }
//            if (types.isEmpty()) {
//                types = HTMLUtilities.getAllMatches(volumes, "\"[0-9]{2,3}x[0-9]{2,3}");
//            }
//
//            Map<String, String> reduceTypes = new HashMap<String, String>();
//            for (String type : types) {
//                reduceTypes.put(type.replace("\"", ""), "w");
//            }
//            for (Map.Entry<String, String> tEntry : reduceTypes.entrySet()) {
//                resultTypes.add(tEntry.getKey());
//            }
//
//            for (String type : resultTypes) {
//                VolumeDTO volume = new VolumeDTO();
//                volume.pushData(type);
//                volumeDTOs.add(volume);
//            }
//            Collections.sort(prices);
//            Collections.sort(volumeDTOs);
//            for (int i = 0; i < volumeDTOs.size(); i++) {
//                volumeDTOs.get(i).setPrice(prices.get(i));
//            }
//            return volumeDTOs;
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(LienaProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(LienaProductCrawler.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//            } catch (IOException e) {
//                Logger.getLogger(BaseCrawler.class.getName()).log(Level.SEVERE, null, e);
//            }
//        }
//
//        return null;
//    }
}
