/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.util;

import javax.servlet.http.HttpSession;

/**
 *
 * @author Tran Dong
 */
public class ServletUtils {
    public static boolean isLogin(HttpSession httpsession) {
        if(httpsession ==null) return false;
        
        if( httpsession.getAttribute("ISLOGIN") != null  && (Boolean) httpsession.getAttribute("ISLOGIN") == true) {
            return true;
        }
        return false;
    }
}
