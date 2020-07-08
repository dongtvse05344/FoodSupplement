/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.thread;

import dongtv.dto.raw.ProductRawDTO;
import dongtv.util.ImageUtils;
import java.util.Date;
import javax.servlet.ServletContext;

/**
 *
 * @author Tran Dong
 */
public class BaseThread extends Thread{
    protected BaseThread() {
    }
    private static BaseThread instance;
    private final static Object LOCK = new Object();
    public static BaseThread getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new BaseThread();
            }
        }
        return instance;
    }
    
    private static boolean suspended = false;
    public static boolean isSuspended() {
        return suspended;
    }
    public static void setSuspended(boolean aSuspended) {
        suspended = aSuspended;
    }
    
     public void suspendThread() {
        setSuspended(true);
        System.out.println("suspended");
    }
    public synchronized void resumeThread() {
        setSuspended(false);
        notify();
        System.out.println("resume");
    }
    
    protected void imageHande(ProductRawDTO product, String image_path, ServletContext context) {
        try {
            String filename = image_path + new Date().getTime() + ".jpg";
            String realPath = context.getRealPath("/");
            ImageUtils.saveImage(product.getImage(), realPath + filename);
            product.setImage(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }
}
