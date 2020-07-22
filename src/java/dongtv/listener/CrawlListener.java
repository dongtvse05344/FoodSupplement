/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.listener;

import dongtv.pageconfig.PageConfigs;
import dongtv.util.DBUtilities;
import dongtv.thread.Mayanh24hThread;
import dongtv.thread.MayanhjpThread;
import dongtv.thread.MayanhvnThread;
import dongtv.thread.ZshopThread;
import dongtv.util.XMLUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.JAXBException;

/**
 * Web application lifecycle listener.
 *
 * @author Tran Dong
 */
public class CrawlListener implements ServletContextListener {

    private static Mayanh24hThread mayanh24hThread;
    private static MayanhvnThread mayanhvnThread;
    private static MayanhjpThread mayanhjpThread;
    private static ZshopThread zshopThread;
    private static final boolean isCrawl = false;

    private final String XML_CRAWCONFIG = "WEB-INF/crawlConfig.xml";    

    @Override 
    public void contextInitialized(ServletContextEvent sce) {
        try {
            System.out.println("--- Listener start");
            final ServletContext context = sce.getServletContext();
            
            String realPath = context.getRealPath("/"); 
            
            PageConfigs pageConfigs = (PageConfigs) XMLUtils.unmarshalFromFile(PageConfigs.class, realPath + XML_CRAWCONFIG);
            
            if (isCrawl) {
                mayanh24hThread = new Mayanh24hThread(context, pageConfigs.getPageConfig().get(0));
                mayanh24hThread.start();
                mayanhvnThread = new MayanhvnThread(context, pageConfigs.getPageConfig().get(1));
                mayanhvnThread.start();
                mayanhjpThread = new MayanhjpThread(context, pageConfigs.getPageConfig().get(2));
                mayanhjpThread.start();
                zshopThread = new ZshopThread(context, pageConfigs.getPageConfig().get(3));
                zshopThread.start();
            }
            System.out.println("--- Listener done");
        } catch (JAXBException ex) {
            Logger.getLogger(CrawlListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManager em = DBUtilities.getEntityManager();
        if (em != null) {
            em.close();
        }
    }
}
