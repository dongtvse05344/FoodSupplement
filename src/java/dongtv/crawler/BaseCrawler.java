/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.crawler;

import dongtv.pageconfig.Replace;
import dongtv.util.HTMLUtilities;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import javax.servlet.ServletContext;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Tran Dong
 */
public class BaseCrawler {

    private final ServletContext context;
    protected boolean isDebug = true;

    public BaseCrawler(ServletContext context) {
        this.context = context;
    }

    public ServletContext getContext() {
        return context;
    }

    public String getDocument(BufferedReader reader, String beginSignal, String tag, List<Replace> Replaces) throws IOException {
        String devide = " ";
        if (isDebug) {
            devide = "\n";
        }
        String line = "";
        String document = "";
        int tagCount = 0;
        boolean isFound = false;
        //make sure we don't have case like this <div><div></div></div>
        while ((line = reader.readLine()) != null) {
            //check the begin tag
            if (!isFound && document.length() == 0 && line.contains(beginSignal)) {
                isFound = true;
            }
            if (isFound && line.contains("<" + tag)) {
                tagCount = tagCount + HTMLUtilities.getAllMatches(line, "<" + tag).size();
            }
            if (tagCount > 0 && line.contains("</" + tag + ">")) {
                tagCount = tagCount - HTMLUtilities.getAllMatches(line, "</" + tag + ">").size();
                if (tagCount == 0) {
                    for (Replace ignore_text : Replaces) {
                        line = line.replaceAll(ignore_text.getFrom(), ignore_text.getTo());
                    }
                    document += line.trim() + devide;
                    isFound = false;
                }
            }
            if (isFound) {
                for (Replace ignore_text : Replaces) {
                    line = line.replaceAll(ignore_text.getFrom(), ignore_text.getTo());
                }
                document += line.trim() + devide;
            }
        }
        document = document.replaceAll("&", "và");
        document = document.replaceAll("<input.*?>", "");
        document = document.replaceAll("<br.*?>", "");
        document = document.replaceAll("<!--.*?-->", "");
        document = document.replaceAll("<style.*?</style>", "");
        document = document.replaceAll("<script.*?</script>", "");

        return document;
    }

    protected BufferedReader getBufferedReaderForURL(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            return reader;
        } catch (Exception e) {
            return null;
        }

    }

    protected XMLEventReader parseStringToXMLEventReader(String xmlSection) throws UnsupportedEncodingException, XMLStreamException {
        byte[] byteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        XMLInputFactory inputFactory = XMLInputFactory.newFactory();
        inputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream, "UTF-8");
        return reader;
    }
}
