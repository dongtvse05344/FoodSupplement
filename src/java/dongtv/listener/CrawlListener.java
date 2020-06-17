/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.listener;

import dongtv.thread.DemxanhThread;
import dongtv.util.DBUtilities;
import dongtv.thread.LienaThread;
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

    private static LienaThread lienaThread;
    private static DemxanhThread demxanhThread;
    private static boolean isCrawl = false;  
   
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("--- Listener start");
        final ServletContext context = sce.getServletContext();
        if (isCrawl) {
//            lienaThread = new LienaThread(context);
//            lienaThread.start();
            demxanhThread = new DemxanhThread(context);
            demxanhThread.start(); 
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
