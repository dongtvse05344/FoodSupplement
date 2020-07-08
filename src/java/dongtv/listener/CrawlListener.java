 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.listener;

import dongtv.util.DBUtilities;
import dongtv.thread.Mayanh24hThread;
import dongtv.thread.MayanhjpThread;
import dongtv.thread.MayanhvnThread;
import dongtv.thread.ZshopThread;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
    private static boolean isCrawl = false; 
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("--- Listener start");
        final ServletContext context = sce.getServletContext();
        if (isCrawl) { 
//            mayanh24hThread = new Mayanh24hThread(context);
//            mayanh24hThread.start();
            mayanhvnThread = new MayanhvnThread(context);
            mayanhvnThread.start(); 
            mayanhjpThread = new MayanhjpThread(context);
            mayanhjpThread.start();
            zshopThread = new ZshopThread(context);
            zshopThread.start(); 
        }
        System.out.println("--- Listener done");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        EntityManager em = DBUtilities.getEntityManager();
        if (em != null) {
            em.close();
        }
    }
}
