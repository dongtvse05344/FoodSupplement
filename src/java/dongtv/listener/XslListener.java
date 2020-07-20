/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Web application lifecycle listener.
 *
 * @author Tran Dong
 */
public class XslListener implements ServletContextListener {

    private final String XSL_PRODUCT_ITEM_DETAIL = "xsl/product_item_detail.xsl";
    private final String XSL_PRODUCT_ITEM = "xsl/product_item.xsl";
    private final String XSL_SUB_PRODUCT_ITEM = "xsl/sub_product_item.xsl";
    private final String XSL_PAGING = "xsl/paging.xsl";
    private final String XSL_CATE_ITEM = "xsl/category_item.xsl";
    private final String XSL_BRAND = "xsl/brand_item.xsl";
    private final String XSL_COMPARISON = "xsl/comparison.xsl";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        
        
        Source xslInput = new StreamSource(realPath + XSL_PRODUCT_ITEM_DETAIL);
        context.setAttribute("XSL_PRODUCT_ITEM_DETAIL", xslInput);

        xslInput = new StreamSource(realPath + XSL_PRODUCT_ITEM);
        context.setAttribute("XSL_PRODUCT_ITEM", xslInput);

        xslInput = new StreamSource(realPath + XSL_SUB_PRODUCT_ITEM);
        context.setAttribute("XSL_SUB_PRODUCT_ITEM", xslInput);

        xslInput = new StreamSource(realPath + XSL_PAGING);
        context.setAttribute("XSL_PAGING", xslInput);

        xslInput = new StreamSource(realPath + XSL_CATE_ITEM);
        context.setAttribute("XSL_CATE_ITEM", xslInput);
        
        xslInput = new StreamSource(realPath + XSL_BRAND);
        context.setAttribute("XSL_BRAND", xslInput); 
        
        xslInput = new StreamSource(realPath + XSL_COMPARISON);
        context.setAttribute("XSL_COMPARISON", xslInput); 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
