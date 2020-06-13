/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tran Dong
 */
public class HTMLUtilities {
    private static String[] IGNORE_TAGS= new String[]{"head","noscript","style","iframe","script"};
    private static String removeNeedlessTags(String src){
        String result = src;
        String expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");
        expression = "&npsp;?";
        result = result.replaceAll(expression, "");
        for(String exp: IGNORE_TAGS){
            expression = String.format("<%s.*?</%s>", exp, exp);
            result = result.replaceAll(expression, "");
        }
        return result;
    }
    
    public static List<String> getAllMatches(String text, String regex) {
        List<String> matches = new ArrayList<String>();
        Matcher m = Pattern.compile("(?=(" + regex + "))").matcher(text);
        while(m.find()) {
            matches.add(m.group(1));
        }
        return matches;
    }
}
